package com.axmor.db;

import com.axmor.db.model.Comment_model;
import com.axmor.db.model.Issue_model;
import com.axmor.db.model.Status_model;
import com.axmor.db.model.User;

import java.sql.*;
import java.util.*;

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
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
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

    public static Iterable<Issue_model> db_getAllIssues(){
        try {
            String query = "SELECT * FROM ISSUES";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Issue_model> issues = new ArrayList<>();
            while (rs.next()) {
                Issue_model issue = new Issue_model();
                issue.setId(rs.getInt("ISSUE_ID"));
                issue.setTitle(rs.getString("TITLE"));
                issue.setDescription(rs.getString("DESCRIPTION"));
                issue.setDate(rs.getDate("PUBLISHING_DATE"));
                issue.setStatus(rs.getString("STATUS"));
                issue.setAuthor(rs.getString("AUTHOR"));
                issues.add(issue);
            }
            return issues;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Iterable<Status_model> db_getStatuses() {
        try {
            String query = "SELECT * FROM ISSUE_STATUS";
            stmt = conn.prepareStatement(query);
            ArrayList<Status_model> statuses = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Status_model status = new Status_model();
                status.setStatus_id(rs.getInt("STATUS_ID"));
                status.setStatus(rs.getString("STATUS"));
                statuses.add(status);
            }
            return statuses;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Iterable<Comment_model> db_getAllComments(int id) {
        try {
            String query = "SELECT * FROM comments where ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Comment_model> comments = new ArrayList<>();
            while(rs.next())
                {
                    Comment_model comment = new Comment_model();
                    comment.setId(rs.getInt("COMMENT_ID"));
                    comment.setIssue_id(rs.getInt("ISSUE_ID"));
                    comment.setAuthor(rs.getString("AUTHOR"));
                    comment.setDate(rs.getDate("SUBMISSION_DATE"));
                    comment.setContent(rs.getString("CONTENT"));
                    comments.add(comment);
                }
            return comments;
            } catch(SQLException sqlEx){
                sqlEx.printStackTrace();
                return null;
            }
        }

    public static Comment_model db_getComment(int id) {
        try {
            String query = "SELECT * FROM comments where COMMENT_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Comment_model comment = new Comment_model();
            comment.setId(rs.getInt("ISSUE_ID"));
            comment.setIssue_id(rs.getInt("ISSUE_ID"));
            comment.setAuthor(rs.getString("AUTHOR"));
            comment.setDate(rs.getDate("SUBMISSION_DATE"));
            comment.setContent(rs.getString("CONTENT"));
            return comment;
        } catch(SQLException sqlEx){
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Issue_model db_getIssue(int issue_id) {
        try {
            String query = "select * FROM issues WHERE ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issue_id);
            ResultSet rs = stmt.executeQuery();
            Issue_model issue = new Issue_model();
            rs.next();
            issue.setId(issue_id);
            issue.setTitle(rs.getString("TITLE"));
            issue.setDescription(rs.getString("DESCRIPTION"));
            issue.setDate(rs.getDate("PUBLISHING_DATE"));
            issue.setStatus(rs.getString("STATUS"));
            issue.setAuthor(rs.getString("AUTHOR"));
            return issue;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static void db_createIssue(String title, String desc, String author, String status) {
        try {
            String query = "INSERT INTO issues (TITLE,DESCRIPTION,PUBLISHING_DATE, AUTHOR, STATUS) VALUES (?,?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, desc);
            stmt.setDate(3, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
            stmt.setString(4, author);
            stmt.setString(5, status);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void db_updateIssue(int issue_id, String title, String desc, String status) {
        try {
            String query = "update issues set TITLE=?, DESCRIPTION=?, STATUS=? where ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, desc);
            stmt.setString(3, status);
            stmt.setInt(4, issue_id);
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
            String query = "select * FROM users WHERE USER_NAME=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, user_name);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            User user = new User();
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