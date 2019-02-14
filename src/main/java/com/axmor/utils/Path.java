package com.axmor.utils;

import lombok.*;

public class Path {
    public static class Web {
        @Getter public static final String INDEX = "/index/";
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String ISSUES= "/issues/";
        @Getter public static final String ONE_ISSUE = "/issues/:isbn/";

    }
    public static class Template {
        public final static String INDEX = "/velocity/index/index.vm";
        public final static String LOGIN = "/velocity/login/login.vm";
        public final static String ISSUES_ALL = "/velocity/issue/all.vm";
        public static final String ISSUES_ONE = "/velocity/issue/one.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";

    }
}
