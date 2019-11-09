package com.example.wellinformed2;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class SQLConnection
{
    private static final String LOG = "DEBUG";
    private static String ip = "wellinformeddb.cjvv450ucvsa.us-east-2.rds.amazonaws.com";
    private static String port = "1433";
    private static String classs = "net.sourceforge.jtds.jdbc.Driver";
    private static String db = "wellinformeddb";
    private static String un = "admin";
    private static String password = "W3ll1nformed";
    private static Connection conn = null;
    private static String ConnURL = null;

    public static Connection connect() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip +":"+port+";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException e) {
            System.err.println("GOT AN EXCEPTION");
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {

            System.err.println("GOT AN EXCEPTION");
            System.err.println(e.getMessage());
        }

        return conn;
    }

}