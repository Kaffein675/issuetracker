package com.axmor.utils;

import lombok.*;

public class Path {
    public static class Web {
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String ISSUES= "/";
        @Getter public static final String ONE_ISSUE = "/:id/";

    }
    public static class Template {
        public static final String LOGIN = "/velocity/login/login.vm";
        public static final String ISSUES_ALL = "/velocity/issue/all.vm";
        public static final String ISSUES_ONE = "/velocity/issue/one.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";

    }
}
