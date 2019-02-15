package com.axmor.db;

import com.axmor.db.model.Issue_model;
import com.axmor.db.model.Status_model;
import com.axmor.db.model.User;

import java.sql.*;
import java.util.List;

public class Db_operations {

    static private final String JDBC_DRIVER = "org.h2.Driver";
    static private final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    static private final String USER = "sa";
    static private final String PASS = "";
    static private Connection conn = null;
    static private PreparedStatement stmt = null;

    public static void db_connect() {
        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Success");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } catch (ClassNotFoundException classEx) {
            classEx.printStackTrace();
        }
    }

    public static void db_disconnect() {
        try {
            System.out.println("Disconnect from database...");
            stmt.close();
            conn.close();
            System.out.println("Successful");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static List<Issue_model> db_getAllIssues() {
        try {
            String query = "SELECT * FROM ISSUES";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            List<Issue_model> issues = null;
            Issue_model issue = null;
            while (rs.next()) {
                issue.setId(rs.getInt("ISSUE_ID"));
                issue.setTitle(rs.getString("TITLE"));
                issue.setDescription(rs.getString("DESCRIPTION"));
                issue.setDate(rs.getDate("PUBLISHING_DATE"));
                issue.setStatus(db_getStatus(rs.getInt("ISSUE_id")).getStatus());
                issues.add(issue);
            }
            return issues;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    private static Status_model db_getStatus(int issue_id) {
        try {
            String query = "SELECT * FROM ISSUE_STATUS where ISSUE_ID =?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issue_id);
            ResultSet rs = stmt.executeQuery();
            Status_model status = null;
            status.setStatus_id(rs.getInt("STATUS_ID "));
            status.setStatus(rs.getString("STATUS"));
            status.setIssue_id(issue_id);
            return status;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static void db_getAllComments() {
        try {
            String query = "SELECT * FROM comments";
            stmt = conn.prepareStatement(query);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void db_getIssue(int issue_id) {
        try {
            String query = "select * FROM issues WHERE ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issue_id);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void db_createIssue(String title, String disc) {
        try {
            String query = "INSERT INTO issues (TITLE,DISCRIPTION,PUBLISHING_DATE) VALUES (?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, disc);
            stmt.setDate(3, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void db_updateIssue(int issue_id, String title, String disc) {
        try {
            String query = "update issues set TITLE=?, DISCRIPTION=? where ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, disc);
            stmt.setInt(3, issue_id);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void db_removeIssue(int issue_id) {
        try {
            String query = "DELETE FROM issues WHERE ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issue_id);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void db_createComment(int issue_id, String author, String content) {
        try {
            String query = "INSERT INTO comments (ISSUE_ID, AUTHOR, CONTENT, SUBMISSION_DATE) VALUES (?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issue_id);
            stmt.setString(2, author);
            stmt.setString(3, content);
            stmt.setDate(4, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void db_updateComment(int comment_id, String content) {
        try {
            String query = "update COMMENTS set CONTENT=? where Comment_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, content);
            stmt.setInt(2, comment_id);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void db_removeComment(int comment_id) {
        try {
            String query = "DELETE FROM comments WHERE comment_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, comment_id);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static User db_getUser(String user_name) {
        try {
            String query = "select * FROM issues WHERE USER_NAME=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user_name);
            ResultSet rs = stmt.executeQuery();
            User user = null;
            user.setUser_id(rs.getInt("USER_ID"));
            user.setUser_name(rs.getString("USER_NAME"));
            user.setSalt(rs.getString("SALT"));
            user.setHash_pass(rs.getString("HASH_PASS"));
            return user;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static void db_createUser(String user_name, String salt, String hash_pass) {
        try {
            String query = "INSERT INTO users (USER_NAME, SALT, HASH_PASS) VALUES (?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user_name);
            stmt.setString(2, salt);
            stmt.setString(3, hash_pass);
            stmt.executeUpdate();
        }catch (SQLException sqlEx) {
            sqlEx.printStackTrace();

        }
    }
}