package com.axmor;

import spark.Request;
import spark.Response;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

/**
 * Application entry point
 */
public class Main {
    public static void main(String[] args) {
        port(80);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        get("/", (Request req, Response res) ->
                "<html><body><h1>Hello, world!</h1></body></html>");
    }
}
