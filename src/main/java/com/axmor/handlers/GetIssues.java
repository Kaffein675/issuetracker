package com.axmor.handlers;

import com.axmor.db.Db_operations;
import com.axmor.db.model.Comment_model;
import com.axmor.db.model.Issue_model;
import com.axmor.utils.*;
import spark.*;
import java.util.*;
import static com.axmor.utils.Json.*;
import static com.axmor.utils.Requests.*;


public class GetIssues {

    public static Route fetchAllBooks = (Request request, Response response) -> {
        //Login.ensureUserIsLoggedIn(request, response);
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

    public static Route fetchOneBook = (Request request, Response response) -> {
        Login.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            Issue_model issue = Db_operations.db_getIssue(Integer.parseInt(getParamId(request)));
            List<Comment_model> comments = Db_operations.db_getAllComments(Integer.parseInt(getParamId(request)));
            model.put("issue", issue);
            model.put("coments", comments);
            return View.render(request, model, Path.Template.ISSUES_ONE);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(Db_operations.db_getIssue(Integer.parseInt(getParamId(request))));
        }
        return View.notAcceptable.handle(request, response);
    };
}