package com.axmor.utils;

import lombok.*;

public class Path {
    public static class Web {
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String ISSUES= "/";
        @Getter public static final String ONE_ISSUE = "/issue/:id/";
        @Getter public static final String SUBMIT = "/submit/";
        @Getter public static final String ISSUE_EDIT = "/edit/issue/:id/";
        @Getter public static final String ISSUE_CREATE = "/create/issue/";
        @Getter public static final String ADD_COMMENT = "/create/comment/:id/";
        @Getter public static final String EDIT_COMMENT = "/edit/comment/:id/";
        @Getter public static final String REMOVE_ISSUE = "/remove/issue/:id/";
        @Getter public static final String REMOVE_COMMENT = "/remove/comment/:id/";
    }
    public static class Template {
        public static final String LOGIN = "/velocity/login/login.vm";
        public static final String ISSUES_ALL = "/velocity/issue/all.vm";
        public static final String ISSUES_ONE = "/velocity/issue/one.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String SUBMIT = "/velocity/login/submit.vm";
        public static final String ISSUE_EDIT = "/velocity/issue/edit.vm";
        public static final String ISSUE_CREATE = "/velocity/issue/create.vm";
        public static final String ADD_COMMENT = "/velocity/comment/create.vm";
        public static final String EDIT_COMMENT = "/velocity/comment/edit.vm";
    }
}
