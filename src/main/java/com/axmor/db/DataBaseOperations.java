package com.axmor.db;

import com.axmor.dto.Comment;
import com.axmor.dto.Issue;
import com.axmor.dto.Status;
import com.axmor.dto.User;

import java.sql.*;
import java.util.*;

public class DataBaseOperations {

    public static Iterable<Issue> getAllIssues() {
        String query = "SELECT * FROM ISSUES";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            ArrayList<Issue> issues = new ArrayList<>();
            while (rs.next()) {
                Issue issue = new Issue(
                        rs.getInt("ISSUE_ID"),
                        rs.getString("TITLE"),
                        rs.getString("DESCRIPTION"),
                        rs.getDate("PUBLISHING_DATE"),
                        rs.getString("STATUS"),
                        rs.getString("AUTHOR")
                );
                issues.add(issue);
            }
            return issues;
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Iterable<Status> getStatuses() {
        String query = "SELECT * FROM ISSUE_STATUS";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            ArrayList<Status> statuses = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Status status = new Status(
                        rs.getInt("STATUS_ID"),
                        rs.getString("STATUS")
                );
                statuses.add(status);
            }
            return statuses;
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Iterable<Comment> getAllComments(int id) {
        String query = "SELECT * FROM comments where ISSUE_ID=?";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            ArrayList<Comment> comments = new ArrayList<>();
            while (rs.next()) {
                Comment comment = new Comment(
                        rs.getInt("COMMENT_ID"),
                        rs.getInt("ISSUE_ID"),
                        rs.getString("AUTHOR"),
                        rs.getString("CONTENT"),
                        rs.getDate("SUBMISSION_DATE")
                );
                comments.add(comment);
            }
            return comments;
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Comment getComment(int id) {
        String query = "SELECT * FROM comments where COMMENT_ID=?";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return new Comment(
                    rs.getInt("ISSUE_ID"),
                    rs.getInt("ISSUE_ID"),
                    rs.getString("AUTHOR"),
                    rs.getString("CONTENT"),
                    rs.getDate("SUBMISSION_DATE")
            );
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static Issue getIssue(int issueId) {
        String query = "select * FROM issues WHERE ISSUE_ID=?";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, issueId);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return new Issue(
                    issueId,
                    rs.getString("TITLE"),
                    rs.getString("DESCRIPTION"),
                    rs.getDate("PUBLISHING_DATE"),
                    rs.getString("STATUS"),
                    rs.getString("AUTHOR")
            );
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static void createIssue(String title, String desc, String author, String status) {
        String query = "INSERT INTO issues (TITLE,DESCRIPTION,PUBLISHING_DATE, AUTHOR, STATUS) VALUES (?,?,?,?,?)";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, title);
            statement.setString(2, desc);
            statement.setDate(3, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
            statement.setString(4, author);
            statement.setString(5, status);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void updateIssue(int issueId, String title, String desc, String status) {
        String query = "update issues set TITLE=?, DESCRIPTION=?, STATUS=? where ISSUE_ID=?";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, title);
            statement.setString(2, desc);
            statement.setString(3, status);
            statement.setInt(4, issueId);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void removeIssue(int issueId) {
        String query = "DELETE FROM issues WHERE ISSUE_ID=?";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, issueId);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void createComment(int issueId, String author, String content) {
        String query = "INSERT INTO comments (ISSUE_ID, AUTHOR, CONTENT, SUBMISSION_DATE) VALUES (?,?,?,?)";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, issueId);
            statement.setString(2, author);
            statement.setString(3, content);
            statement.setDate(4, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void updateComment(int commentId, String content) {
        String query = "update COMMENTS set CONTENT=? where Comment_ID=?";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, content);
            statement.setInt(2, commentId);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static void removeComment(int commentId) {
        String query = "DELETE FROM comments WHERE comment_ID=?";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setInt(1, commentId);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static User getUser(String userName) {
        String query = "select * FROM users WHERE USER_NAME=?";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, userName);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return new User(
                    rs.getInt("USER_ID"),
                    rs.getString("USER_NAME"),
                    rs.getString("SALT"),
                    rs.getString("HASH_PASS")
            );
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
    }

    public static void createUser(String userName, String salt, String hashPass) {
        String query = "INSERT INTO users (USER_NAME, SALT, HASH_PASS) VALUES (?,?,?)";
        try (Connection connect = ConnectToDataBase.Connect();
             PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, userName);
            statement.setString(2, salt);
            statement.setString(3, hashPass);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException sqlEx) {
            sqlEx.printStackTrace();

        }
    }
}