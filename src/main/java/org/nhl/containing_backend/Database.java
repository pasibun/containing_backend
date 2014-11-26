package org.nhl.containing_backend;

import java.sql.*;

/**
 * This class handles the creating of a fresh database each time
 * the program is ran.
 * @author Skrylax
 */
public class Database {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/containing";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    /**
     * Creates a fresh database with products
     */
    public void createDatabase() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //Execute a query
            System.out.println("Creating table in given database...");
            stmt = conn.createStatement();

            String delete = "DROP TABLE products";

            stmt.executeUpdate(delete);

            String sql = "CREATE TABLE products "
                    + "(id INTEGER NULL AUTO_INCREMENT, "
                    + " product_name VARCHAR(255), "
                    + " product_price DOUBLE, "
                    + " product_amount INT, "
                    + " PRIMARY KEY ( id ))";

            stmt.executeUpdate(sql);
            System.out.println("Created table in given database...");
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
}
