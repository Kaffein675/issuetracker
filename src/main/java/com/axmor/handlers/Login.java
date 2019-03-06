package com.axmor.handlers;


import com.axmor.db.Db_operations;
import com.axmor.db.model.User;
import com.axmor.utils.Path;
import com.axmor.utils.View;
import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static com.axmor.utils.Requests.*;


public class Login {
    public static Route serveLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return View.render(request, model, Path.Template.LOGIN);
    };

    public static Route serveSubmitPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
        return View.render(request, model, Path.Template.SUBMIT);
    };

    public static Route handleUserCreate = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(Db_operations.db_getUser(getQueryUsername(request)) == null) {
            createUser(getQueryUsername(request), getQueryPassword(request));
        }
        if (!authenticate(getQueryUsername(request), getQueryPassword(request))) {
            model.put("authenticationFailed", true);
            return View.render(request, model, Path.Template.LOGIN);
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", getQueryUsername(request));
        if (getQueryLoginRedirect(request) != null) {
            response.redirect(getQueryLoginRedirect(request));
        }
        return View.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLoginPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if (!authenticate(getQueryUsername(request), getQueryPassword(request))) {
            model.put("authenticationFailed", true);
            return View.render(request, model, Path.Template.LOGIN);
        }
        model.put("authenticationSucceeded", true);
        request.session().attribute("currentUser", getQueryUsername(request));
        if (getQueryLoginRedirect(request) != null) {
            response.redirect(getQueryLoginRedirect(request));
        }
        //model.put("issues", Db_operations.db_getAllIssues());
        return View.render(request, model, Path.Template.LOGIN);
    };

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Path.Web.LOGIN);
        return null;
    };


    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            response.redirect(Path.Web.LOGIN);
        }
    }

    private static boolean authenticate(String username, String password){
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = Db_operations.db_getUser(username);
        if (user == null) {
            return false;
        }
        String hashedPassword = BCrypt.hashpw(password, user.getSalt());
        return hashedPassword.equals(user.getHash_pass());
    }

    private static void createUser(String username, String Password) {
        String newSalt = BCrypt.gensalt();
        String newHashedPassword = BCrypt.hashpw(Password, newSalt);
        Db_operations.db_createUser(username, newSalt, newHashedPassword);
    }
}
