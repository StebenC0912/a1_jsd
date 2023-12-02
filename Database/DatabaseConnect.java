package Database;

import a1_2001040121.Patron;

import java.sql.*;

public class DatabaseConnect {
    private static Connection connection = null;
    private static Statement statement = null;
    public static Connection connect() {
        try {
            connection = java.sql.DriverManager.getConnection("jdbc:sqlite:library.db");
            statement = connection.createStatement();
            System.out.println("Connection to SQLite has been established.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void addPatron(Patron patron) throws SQLException {
        String query = "INSERT INTO PATRON (PATRON_ID, NAME, DOB, EMAIL, PHONE_NUM, PATRON_TYPE) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS))
    }
}
