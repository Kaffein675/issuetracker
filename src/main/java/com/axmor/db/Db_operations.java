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
            Issue_model issue = new Issue_model();
            Status_model status = new Status_model();
            while (rs.next()) {
                issue.setId(rs.getInt("ISSUE_ID"));
                issue.setTitle(rs.getString("TITLE"));
                issue.setDescription(rs.getString("DESCRIPTION"));
                issue.setDate(rs.getDate("PUBLISHING_DATE"));
                status = db_getStatus(rs.getInt("ISSUE_id"));
                issue.setStatus("ghjhg");
                issues.add(issue);
            }
            return issues;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }catch(NullPointerException e)
        {
            System.out.print("NullPointerException Caught");
            return null;
        }

    }

    private static Status_model db_getStatus(int issue_id) {
        try {
            String query = "SELECT * FROM ISSUE_STATUS where ISSUE_ID =?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issue_id);
            ResultSet rs = stmt.executeQuery();
            Status_model status = new Status_model();
            status.setStatus_id(rs.getInt("STATUS_ID"));
            status.setStatus(rs.getString("STATUS"));
            status.setIssue_id(issue_id);
            return status;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
        catch(NullPointerException e)
        {
            System.out.print("NullPointerException Caught");
            return null;
        }
    }

    public static List<Comment_model> db_getAllComments(int id) {
        try {
            String query = "SELECT * FROM comments where ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery();
            List<Comment_model> comments = new List<Comment_model>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<Comment_model> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] ts) {
                    return null;
                }

                @Override
                public boolean add(Comment_model comment_model) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends Comment_model> collection) {
                    return false;
                }

                @Override
                public boolean addAll(int i, Collection<? extends Comment_model> collection) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public Comment_model get(int i) {
                    return null;
                }

                @Override
                public Comment_model set(int i, Comment_model comment_model) {
                    return null;
                }

                @Override
                public void add(int i, Comment_model comment_model) {

                }

                @Override
                public Comment_model remove(int i) {
                    return null;
                }

                @Override
                public int indexOf(Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(Object o) {
                    return 0;
                }

                @Override
                public ListIterator<Comment_model> listIterator() {
                    return null;
                }

                @Override
                public ListIterator<Comment_model> listIterator(int i) {
                    return null;
                }

                @Override
                public List<Comment_model> subList(int i, int i1) {
                    return null;
                }
            };
            Comment_model comment = new Comment_model();
            while (rs.next()) {
                comment.setId(rs.getInt("ISSUE_ID"));
                comment.setIssue_id(rs.getInt("ISSUE_ID"));
                comment.setAuthor(rs.getString("AUTHOR"));
                comment.setDate(rs.getDate("PUBLISHING_DATE"));
                comment.setContent(rs.getString("CONTENT"));
                comments.add(comment);
            }
            return comments;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
        catch(NullPointerException e)
        {
            System.out.print("NullPointerException Caught");
            return null;
        }
    }

    public static Issue_model db_getIssue(int issue_id) {
        try {
            String query = "select * FROM issues WHERE ISSUE_ID=?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, issue_id);
            ResultSet rs = stmt.executeQuery();
            Issue_model issue = null;
            issue.setId(rs.getInt("ISSUE_ID"));
            issue.setTitle(rs.getString("TITLE"));
            issue.setDescription(rs.getString("DESCRIPTION"));
            issue.setDate(rs.getDate("PUBLISHING_DATE"));
            issue.setStatus(db_getStatus(rs.getInt("ISSUE_id")).getStatus());
            return issue;
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            return null;
        }
        catch(NullPointerException e)
        {
            System.out.print("NullPointerException Caught");
            return null;
        }
    }

    public static void db_createIssue(String title, String disc) {
        try {
            String query = "INSERT INTO issues (TITLE,DESCRIPTION,PUBLISHING_DATE) VALUES (?,?,?)";
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
        catch(NullPointerException e)
        {
            System.out.print("NullPointerException Caught");
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