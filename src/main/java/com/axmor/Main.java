package com.axmor;

import com.axmor.db.Db_operations;
import com.axmor.utils.*;
import com.axmor.handlers.*;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Main {



    public static void main(String[] args){
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        port(80);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        Db_operations.db_connect();
        //Db_operations.db_getAllIssues();

        before("*",            Filters.addTrailingSlashes);
        before("*",            Filters.handleLocaleChange);

        get(Path.Web.ISSUES,         GetIssues.fetchAllIssues);
        get(Path.Web.ONE_ISSUE,      GetIssues.fetchOneIssue);
        get(Path.Web.LOGIN,          Login.serveLoginPage);
        get(Path.Web.SUBMIT,          Login.serveSubmitPage);
        post(Path.Web.LOGIN,         Login.handleLoginPost);
        post(Path.Web.LOGOUT,        Login.handleLogoutPost);
        post(Path.Web.SUBMIT,        Login.handleUserCreate);
        get("*",                     View.notFound);

        after("*",             Filters.addGzipHeader);
    }

}