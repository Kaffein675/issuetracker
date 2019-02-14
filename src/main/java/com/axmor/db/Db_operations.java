package com.axmor.db;

import com.axmor.db.model.Issue_model;
import com.axmor.db.model.Status_model;

import java.sql.*;
import java.util.List;

public class Db_operations {

    static private final String JDBC_DRIVER = "org.h2.Driver";
    static private final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    static private final String USER = "sa";
    static private final String PASS = "";
    static private Connection conn = null;
    static private PreparedStatement stmt = null;

    public static void db_connect() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        System.out.println("Connecting to database...");
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        System.out.println("Success");
    }

    public static void db_disconnect() throws SQLException {
        System.out.println("Disconnect from database...");
        stmt.close();
        conn.close();
        System.out.println("Successful");
    }

    public static List<Issue_model> db_get_all_issues() throws SQLException {
        String query = "SELECT * FROM ISSUES";
        stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        List <Issue_model> issues = null;
        Issue_model issue = null;
        while(rs.next()){
            issue.setId(rs.getInt("ISSUE_ID"));
            issue.setTitle(rs.getString("TITLE"));
            issue.setDescription(rs.getString("DESCRIPTION"));
            issue.setDate(rs.getDate("PUBLISHING_DATE"));
            issue.setStatus(db_get_status(rs.getInt("ISSUE_id")).getStatus());
            issues.add(issue);
        }
        return issues;
    }

    private static Status_model db_get_status(int issue_id)throws SQLException{
        String query = "SELECT * FROM ISSUE_STATUS where ISSUE_ID =?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, issue_id);
        ResultSet rs = stmt.executeQuery();
        Status_model status = null;
        status.setStatus_id(rs.getInt("STATUS_ID "));
        status.setStatus(rs.getString("STATUS"));
        status.setIssue_id(issue_id);
        return status;
    }

    public static void db_get_all_comments() throws SQLException {
        String query = "SELECT * FROM comments";
        stmt = conn.prepareStatement(query);
        stmt.executeUpdate();
    }

    public static void db_get_issue(int issue_id)throws SQLException{
        String query = "select * FROM issues WHERE ISSUE_ID=?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, issue_id);
        stmt.executeUpdate();
    }

    public static void db_create_issue(String title, String disc) throws SQLException {
        String query = "INSERT INTO issues (TITLE,DISCRIPTION,PUBLISHING_DATE) VALUES (?,?,?)";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, title);
        stmt.setString(2,disc);
        stmt.setDate(3, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
        stmt.executeUpdate();
    }

    public static void db_update_issue(int issue_id,String title, String disc) throws SQLException {
        String query = "update issues set TITLE=?, DISCRIPTION=? where ISSUE_ID=?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, title);
        stmt.setString(2,disc);
        stmt.setInt(3,issue_id);
        stmt.executeUpdate();
    }

    public static void db_remove_issue(int issue_id)throws SQLException{
        String query = "DELETE FROM issues WHERE ISSUE_ID=?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, issue_id);
        stmt.executeUpdate();
    }

    public static void db_create_comment(int issue_id,String author, String content) throws SQLException {
        String query = "INSERT INTO comments (ISSUE_ID, AUTHOR, CONTENT, SUBMISSION_DATE) VALUES (?,?,?,?)";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, issue_id);
        stmt.setString(2,author);
        stmt.setString(3, content);
        stmt.setDate(4, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
        stmt.executeUpdate();
    }

    public static void db_update_comment(int comment_id,String content) throws SQLException {
        String query = "update COMMENTS set CONTENT=? where Comment_ID=?";
        stmt = conn.prepareStatement(query);
        stmt.setString(1, content);
        stmt.setInt(2,comment_id);
        stmt.executeUpdate();
    }

    public static void db_remove_comment(int comment_id)throws SQLException{
        String query = "DELETE FROM comments WHERE comment_ID=?";
        stmt = conn.prepareStatement(query);
        stmt.setInt(1, comment_id);
        stmt.executeUpdate();
    }

}
