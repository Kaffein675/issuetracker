package com.axmor;


import com.axmor.db.Db_operations;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.exception;
import static spark.Spark.port;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Main {


    public static void main(String[] args){
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        port(80);
        enableDebugScreen();
        Db_operations.db_connect();
        Db_operations.db_createIssue("title","description");
        Db_operations.db_disconnect();
    }


    private static String renderIndex() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", "nmae");
        return renderTemplate("velocity/index.vm", model);
    }

    private static String renderTemplate(String template, Map model) {
        return new VelocityTemplateEngine().render(new ModelAndView(model, template));
    }

}