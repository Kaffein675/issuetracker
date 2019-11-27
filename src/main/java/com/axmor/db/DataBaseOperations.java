package com.axmor.db;

import com.axmor.dto.Comment;
import com.axmor.dto.Issue;
import com.axmor.dto.Status;
import com.axmor.dto.User;

import java.sql.*;
import java.util.*;

public class DataBaseOperations {

    static private final String JDBC_DRIVER = "org.h2.Driver";
    static private final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    static private final String USER = "sa";
    static private final String PASS = "";
    static private Connection conn = null;
    static private PreparedStatement stmt = null;

    public static void Connect() {
        try {

            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Success");
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void Disconnect() {
        try {
            System.out.println("Disconnect from database...");
            stmt.close();
            conn.close();
            System.out.println("Successful");
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static Iterable<Issue> getAllIssues() {
        try {
            String query = "SELECT * FROM ISSUES";
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Issue> issues = new ArrayList<>();
            while (rs.next()) {
                Issue issue = new Issue();
                issue.setIssueId(rs.getInt("ISSUE_ID"));
                issue.setTitle(rs.getString("TITLE"));
                issue.setDescription(rs.getString("DESCRIPTION"));
                issue.setPublishingDate(rs.getDate("PUBLISHING_DATE"));
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

    public static Iterable<Status> getStatuses() {
        try {
            String query = "SELECT * FROM ISSUE_STATUS";
            stmt = conn.prepareStatement(query);
            ArrayList<Status> statuses = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Status status = new Status();
                status.setStatusId(rs.getInt("STATUS_ID"));
                status.setStatus(rs.getString("STATUS"));
                statuses.add(status);
            }
            return statuses;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Iterable<Comment> getAllComments(int id) {
        try {
            String query = "SELECT * FROM comments where ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            ArrayList<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("COMMENT_ID"));
                comment.setPostId(rs.getInt("ISSUE_ID"));
                comment.setAuthor(rs.getString("AUTHOR"));
                comment.setSubmissionDate(rs.getDate("SUBMISSION_DATE"));
                comment.setContent(rs.getString("CONTENT"));
                comments.add(comment);
            }
            return comments;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Comment getComment(int id) {
        try {
            String query = "SELECT * FROM comments where COMMENT_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            Comment comment = new Comment();
            comment.setCommentId(rs.getInt("ISSUE_ID"));
            comment.setPostId(rs.getInt("ISSUE_ID"));
            comment.setAuthor(rs.getString("AUTHOR"));
            comment.setSubmissionDate(rs.getDate("SUBMISSION_DATE"));
            comment.setContent(rs.getString("CONTENT"));
            return comment;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Issue getIssue(int issueId) {
        try {
            String query = "select * FROM issues WHERE ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issueId);
            ResultSet rs = stmt.executeQuery();
            Issue issue = new Issue();
            rs.next();
            issue.setIssueId(issueId);
            issue.setTitle(rs.getString("TITLE"));
            issue.setDescription(rs.getString("DESCRIPTION"));
            issue.setPublishingDate(rs.getDate("PUBLISHING_DATE"));
            issue.setStatus(rs.getString("STATUS"));
            issue.setAuthor(rs.getString("AUTHOR"));
            return issue;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static void createIssue(String title, String desc, String author, String status) {
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

    public static void updateIssue(int issueId, String title, String desc, String status) {
        try {
            String query = "update issues set TITLE=?, DESCRIPTION=?, STATUS=? where ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, desc);
            stmt.setString(3, status);
            stmt.setInt(4, issueId);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void removeIssue(int issueId) {
        try {
            String query = "DELETE FROM issues WHERE ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issueId);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void createComment(int issueId, String author, String content) {
        try {
            String query = "INSERT INTO comments (ISSUE_ID, AUTHOR, CONTENT, SUBMISSION_DATE) VALUES (?,?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issueId);
            stmt.setString(2, author);
            stmt.setString(3, content);
            stmt.setDate(4, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void updateComment(int commentId, String content) {
        try {
            String query = "update COMMENTS set CONTENT=? where Comment_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, content);
            stmt.setInt(2, commentId);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void removeComment(int commentId) {
        try {
            String query = "DELETE FROM comments WHERE comment_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, commentId);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static User getUser(String userName) {
        try {
            String query = "select * FROM users WHERE USER_NAME=?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            User user = new User();
            user.setUserId(rs.getInt("USER_ID"));
            user.setUserName(rs.getString("USER_NAME"));
            user.setSalt(rs.getString("SALT"));
            user.setHashPass(rs.getString("HASH_PASS"));
            return user;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static void createUser(String userName, String salt, String hashPass) {
        try {
            String query = "INSERT INTO users (USER_NAME, SALT, HASH_PASS) VALUES (?,?,?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, userName);
            stmt.setString(2, salt);
            stmt.setString(3, hashPass);
            stmt.executeUpdate();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();

        }
    }
}