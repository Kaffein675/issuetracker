package com.axmor.handlers;

import com.axmor.db.Db_operations;
import com.axmor.utils.Path;
import com.axmor.utils.View;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

import static com.axmor.utils.Json.dataToJson;
import static com.axmor.utils.Requests.*;


public class GetIssues {

    public static Route fetchAllIssues = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("issues", Db_operations.db_getAllIssues());
            return View.render(request, model, Path.Template.ISSUES_ALL);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(Db_operations.db_getAllIssues());
        }
        return View.notAcceptable.handle(request, response);
    };

    public static Route fetchOneIssue = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("issue", Db_operations.db_getIssue(Integer.parseInt(getParamId(request).trim())));
            model.put("coments", Db_operations.db_getAllComments(Integer.parseInt(getParamId(request).trim())));
            return View.render(request, model, Path.Template.ISSUES_ONE);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(Db_operations.db_getIssue(Integer.parseInt(getParamId(request))));
        }
        return View.notAcceptable.handle(request, response);
    };
}