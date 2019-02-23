package com.axmor.handlers;

import com.axmor.db.Db_operations;
import com.axmor.utils.Path;
import com.axmor.utils.View;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static com.axmor.utils.Requests.*;


public class Issues {

    public static Route fetchAllIssues = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        model.put("issues", Db_operations.db_getAllIssues());
        return View.render(request, model, Path.Template.ISSUES_ALL);
    };

    public static Route fetchOneIssue = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        HashMap<String, Object> model = new HashMap<>();
        model.put("issue", Db_operations.db_getIssue(Integer.parseInt(getParamId(request).trim())));
        model.put("comments", Db_operations.db_getAllComments(Integer.parseInt(getParamId(request).trim())));
        return View.render(request, model, Path.Template.ISSUES_ONE);
    };

    public static Route serveEditPage = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        model.put("issue", Db_operations.db_getIssue(Integer.parseInt(getParamId(request).trim())));
        return View.render(request, model, Path.Template.ISSUE_EDIT);
    };

    public static Route handleEditPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        Db_operations.db_updateIssue(Integer.parseInt(getParamId(request).trim()), request.queryParams("title"),
                request.queryParams("description"));
        model.put("issue", Db_operations.db_getIssue(Integer.parseInt(getParamId(request).trim())));
        model.put("comments", Db_operations.db_getAllComments(Integer.parseInt(getParamId(request).trim())));
        return View.render(request, model, Path.Template.ISSUES_ONE);
    };

    public static Route serveCreatePage = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        model.put("statuses", Db_operations.db_getStatuses());
        return View.render(request, model, Path.Template.ISSUE_CREATE);
    };

    public static Route handleCreatePost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        Db_operations.db_createIssue(request.queryParams("title"), request.queryParams("description"),
                request.session().attribute("currentUser"), request.queryParams("status"));
        model.put("issues", Db_operations.db_getAllIssues());
        return View.render(request, model, Path.Template.ISSUES_ALL);
    };

    public static Route removeIssue = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        Db_operations.db_removeIssue(Integer.parseInt(getParamId(request).trim()));
        model.put("issues", Db_operations.db_getAllIssues());
        return View.render(request, model, Path.Template.ISSUES_ALL);
    };
}