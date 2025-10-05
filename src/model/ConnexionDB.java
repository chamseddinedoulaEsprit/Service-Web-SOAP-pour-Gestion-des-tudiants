package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnexionDB {
    private static final Logger logger = LoggerFactory.getLogger(ConnexionDB.class);
    private static final String URL = "jdbc:mysql://localhost:3306/tptest";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    private ConnexionDB() {
        // Constructeur privé pour empêcher l'instanciation
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Database connection established");
            } catch (ClassNotFoundException | SQLException e) {
                logger.error("Error connecting to database: {}", e.getMessage());
                throw new RuntimeException("Failed to connect to database", e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.error("Error closing database connection: {}", e.getMessage());
            }
        }
    }
}
