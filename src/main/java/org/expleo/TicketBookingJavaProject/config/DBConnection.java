/*
 * FILE: DBConnection.java
 * PURPOSE: Manages database connection.
 * 
 * OOPS CONCEPTS USED:
 * - Static methods: Single connection point
 * - Encapsulation: Hides connection details
 * 
 * DATABASE: MySQL
 * 
 * Note: Connection details are stored as constants.
 */
package org.expleo.TicketBookingJavaProject.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * DBConnection class handles database connection to MySQL.
 */
public class DBConnection {

    // Database URL (from Aiven cloud)
    private static final String URL = "jdbc:mysql://mysql-19f33634-dharshujayapal-dc10.e.aivencloud.com:24437/defaultdb?sslMode=REQUIRED";
    
    // Database username
    private static final String USER = "avnadmin";
    
    // Database password (from environment variable)
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    /*
     * getConnection - Gets database connection
     * 
     * Returns: Connection object
     * 
     * Note: Call close() on the connection when done.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL JDBC Driver not found!");
            e.printStackTrace();
        }
        
        // Return connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
