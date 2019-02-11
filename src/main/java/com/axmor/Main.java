package com.axmor;


import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;


public class Main {


    public static void main(String[] args) {

        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        port(80);
        enableDebugScreen();

        get("/", (Request req, Response res) -> renderIndex());
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