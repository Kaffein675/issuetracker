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

public class Comments {
    public static Route serveAddPage = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return View.render(request, model, Path.Template.ADD_COMMENT);
    };

    public static Route handleAddPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        Db_operations.db_createComment(Integer.parseInt(getParamId(request).trim()),
                request.session().attribute("currentUser"),request.queryParams("content"));
        model.put("issue", Db_operations.db_getIssue(Integer.parseInt(getParamId(request).trim())));
        return View.render(request, model, Path.Template.ISSUES_ONE);
    };

    public static Route serveEditPage = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        model.put("comment", Db_operations.db_getComment(Integer.parseInt(getParamId(request).trim())));
        return View.render(request, model, Path.Template.EDIT_COMMENT);
    };

    public static Route handleEditPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        Db_operations.db_updateComment(Integer.parseInt(getParamId(request).trim()),
                request.queryParams("content"));
        model.put("issue", Db_operations.db_getIssue(Integer.parseInt(getParamId(request).trim())));
        return View.render(request, model, Path.Template.ISSUES_ONE);
    };

    public static Route removeComment = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        Db_operations.db_removeComment(Integer.parseInt(getParamId(request).trim()));
        model.put("issue", Db_operations.db_getIssue(Integer.parseInt(getParamId(request).trim())));
        return View.render(request, model, Path.Template.ISSUES_ONE);
    };
}
