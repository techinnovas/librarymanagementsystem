package com.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * To create Person class
 * @author JayasuriyaPJ(Expleo)
 * @since 20 FEB 2024
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Corrected URL format
    private static final String USERNAME = "SYSTEM";
    private static final String PASSWORD = "oracle";

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Registering the Oracle JDBC driver
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading Oracle JDBC driver: ");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
