package Database;

import a1_2001040121.Patron;

import java.sql.*;
public class DatabaseConnect {

    // Connect to the sqlite database with the file library.db
    Connection connection = null;
    private Statement stmt = null;

    public Connection connect() {
        try {
            connection = java.sql.DriverManager.getConnection("jdbc:sqlite:library.db");
            stmt = connection.createStatement();
            System.out.println("Connected to database!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertPatron(String name, String dob, String email, String phone, String patronType) {
        try {
            connection = connect();
            stmt.execute("INSERT INTO patron (name, dob, email, phone, patronType) VALUES ('" + name + "', '" + dob + "', '" + email + "', '" + phone + "', '" + patronType + "')");
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void executeQuery(String s) {
        try {
            connection = connect();
            stmt.execute(s);
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
    }

    public void insertBook(String isbn, String title, String author, String genre, String publicationYear, int numberOfCopiesAvailable) {
        try {
            connection = connect();
            stmt.execute("INSERT INTO book (isbn, title, author, genre, pubYear, numCopiesAvailable) VALUES ('" + isbn + "', '" + title + "', '" + author + "', '" + genre + "', '" + publicationYear + "', '" + numberOfCopiesAvailable + "')");
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkoutBook(int bookId, int patronId, String checkoutDate, String dueDateText) {
        try {
            connection = connect();
            stmt.execute("INSERT INTO 'transaction' (book_id, patron_id, checkoutDate, dueDate) VALUES ('" + bookId + "', '" + patronId + "', '" + checkoutDate + "', '" + dueDateText + "')");
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getNumberOfCopiesAvailable(int bookId) {
        int numberOfCopiesAvailable = 0;
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT numCopiesAvailable FROM book WHERE id = '" + bookId + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                numberOfCopiesAvailable = rs.getInt("numCopiesAvailable");
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numberOfCopiesAvailable;
    }

    public void decrementNumberOfCopiesAvailable(int bookId) {
        try {
            connection = connect();
            stmt.executeUpdate("UPDATE book SET numCopiesAvailable = numCopiesAvailable - 1 WHERE id = '" + bookId + "'");
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void returnBook(int patronId, int bookId, String returnDateText) {
        try {
            connection = connect();
            deleteTransaction(patronId, bookId);
            System.out.println(patronId + " " + bookId);
            setNumberOfCopiesAvailable(bookId, getNumberOfCopiesAvailable(bookId) + 1);
            stmt.close();
//            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTransaction(int patronId, int bookId) {
        try {
            connection = connect();
            stmt.execute("DELETE FROM 'transaction' WHERE patron_id = '" + patronId + "' AND book_id = '" + bookId + "'");
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setNumberOfCopiesAvailable(int bookId, int numberOfCopiesAvailable) {
        try {
            connection = connect();
            stmt.execute("UPDATE book SET numCopiesAvailable = " + numberOfCopiesAvailable + " WHERE id = '" + bookId + "'");
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPatronId(String patronName) {
        int patronId = 0;
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT id FROM patron WHERE name = '" + patronName + "'";
            ResultSet resultSet = stmt.executeQuery(query);

            if (resultSet.next()) {
                patronId = resultSet.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return patronId;
    }

    public int getBookId(String bookName) {
        int bookId = 0;
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT id FROM book WHERE title = '" + bookName + "'";
            ResultSet resultSet = stmt.executeQuery(query);

            if (resultSet.next()) {
                bookId = resultSet.getInt("id");
            }

            System.out.println(bookId);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookId;
    }
}