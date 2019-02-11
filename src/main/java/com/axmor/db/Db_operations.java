package com.axmor.db;

import java.sql.*;

public class Db_operations {

    static private final String JDBC_DRIVER = "org.h2.Driver";
    static private final String DB_URL = "jdbc:h2:tcp://localhost/~/test";
    static private final String USER = "sa";
    static private final String PASS = "";
    static private Connection conn = null;
    static private Statement stmt = null;

    public static void db_connect(){
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch(Exception se) {
            se.printStackTrace();
        }
        System.out.println("Successful connection");
    }

    public static void db_disconnect() throws SQLException {
        System.out.println("Disconnect from database...");
        stmt.close();
        conn.close();
        System.out.println("Successful");
    }

    public static void db_incert(){


    }

    public static void db_update(){}

    public static void db_remove(){}

}
