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

        get(Path.Web.ISSUES,         Issues.fetchAllIssues);
        get(Path.Web.ONE_ISSUE,      Issues.fetchOneIssue);
        get(Path.Web.ISSUE_EDIT,     Issues.serveEditPage);
        get(Path.Web.ISSUE_CREATE,   Issues.serveCreatePage);
        get(Path.Web.LOGIN,          Login.serveLoginPage);
        get(Path.Web.SUBMIT,         Login.serveSubmitPage);
        get(Path.Web.ADD_COMMENT,    Comments.serveAddPage);
        get(Path.Web.EDIT_COMMENT,   Comments.serveEditPage);

        delete(Path.Web.REMOVE_ISSUE,   Issues.removeIssue);
        delete(Path.Web.REMOVE_COMMENT, Comments.removeComment);

        post(Path.Web.LOGIN,         Login.handleLoginPost);
        post(Path.Web.LOGOUT,        Login.handleLogoutPost);
        post(Path.Web.SUBMIT,        Login.handleUserCreate);
        post(Path.Web.ISSUE_EDIT,    Issues.handleEditPost);
        post(Path.Web.ISSUE_CREATE,  Issues.handleCreatePost);
        post(Path.Web.ADD_COMMENT,   Comments.handleAddPost);
        post(Path.Web.EDIT_COMMENT,  Comments.handleEditPost);

        get("*",                     View.notFound);

        after("*",             Filters.addGzipHeader);
    }
}