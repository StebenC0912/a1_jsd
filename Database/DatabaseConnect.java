package Database;

import a2_2001040121.Book;
import a2_2001040121.LibraryTransaction;
import a2_2001040121.Patron;
import common.Genre;
import common.PatronType;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            stmt.close();
            connection.close();
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
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookId;
    }

    public ArrayList<Patron> getPatonList() {
        ArrayList<Patron> patronList = new ArrayList<>();
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM patron";
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String dobString = resultSet.getString("dob");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String patronTypeString = resultSet.getString("patronType");
                Patron patron = new Patron(Integer.parseInt(id),name,new SimpleDateFormat("DD/MM/YYYY").parse(dobString), email, phone, PatronType.valueOf(patronTypeString));
                patronList.add(patron);
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patronList;
    }

    public ArrayList<Book> getBookList() {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM book";
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                String publicationYear = resultSet.getString("pubYear");
                int numberOfCopiesAvailable = resultSet.getInt("numCopiesAvailable");
                Book book = new Book(isbn, title, author, Genre.valueOf(genre), publicationYear, numberOfCopiesAvailable);
                bookList.add(book);
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public void insertTransaction(String patron, String book, String format, String dueDateString) {
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT id FROM patron WHERE name = '" + patron + "'";
            ResultSet resultSet = stmt.executeQuery(query);
            int patronId = 0;
            if (resultSet.next()) {
                patronId = resultSet.getInt("id");
            }
            query = "SELECT id FROM book WHERE title = '" + book + "'";
            resultSet = stmt.executeQuery(query);
            int bookId = 0;
            if (resultSet.next()) {
                bookId = resultSet.getInt("id");
            }
            stmt.execute("INSERT INTO 'transaction' (book_id, patron_id, checkoutDate, dueDate) VALUES ('" + bookId + "', '" + patronId + "', '" + format + "', '" + dueDateString + "')");
            query = "UPDATE book SET numCopiesAvailable = '" + (getBookList().get(bookId - 1).getNumCopiesAvailable() - 1) + "' WHERE title = '" + book + "'";
            stmt.execute(query);
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LibraryTransaction> getTransactionList() {
        ArrayList<LibraryTransaction> transactionList = new ArrayList<>();
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM 'transaction'";
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                int patronId = resultSet.getInt("patron_id");
                String checkoutDateString = resultSet.getString("checkoutDate");
                String dueDateString = resultSet.getString("dueDate");
                LibraryTransaction transaction = new LibraryTransaction(getPatronName(patronId), getBookName(bookId), new SimpleDateFormat("dd/MM/yyyy").parse(checkoutDateString), new SimpleDateFormat("dd/MM/yyyy").parse(dueDateString));
                transactionList.add(transaction);
            }
            stmt.close();
            connection.close();
            return transactionList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactionList;
    }

    private Book getBookName(int bookId) {
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM book WHERE id = '" + bookId + "'";
            ResultSet resultSet = stmt.executeQuery(query);
            Book book = null;
            if (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                Genre genre = Genre.valueOf(resultSet.getString("genre"));
                String publicationYear = resultSet.getString("pubYear");
                int numberOfCopiesAvailable = resultSet.getInt("numCopiesAvailable");
                book = new Book(title, author, genre, publicationYear, numberOfCopiesAvailable);
            }
            stmt.close();
            connection.close();
            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Patron getPatronName(int patronId) {
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "SELECT * FROM patron WHERE id = '" + patronId + "'";
            ResultSet resultSet = stmt.executeQuery(query);
            Patron patron = null;
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(resultSet.getString("dob"));
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                PatronType patronType = PatronType.valueOf(resultSet.getString("patronType"));
                patron = new Patron(name, dob, email, phone, patronType);
            }
            stmt.close();
            connection.close();
            return patron;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTransaction(LibraryTransaction transaction) {
        try {
            connection = connect();
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM 'transaction' WHERE book_id = '" + getBookId(transaction.getBook().getTitle()) + "' AND patron_id = '" + getPatronId(transaction.getPatron().getName()) + "'";
            stmt.execute(query);
            // update the number of copies available
            query = "UPDATE book SET numCopiesAvailable = '" + (transaction.getBook().getNumCopiesAvailable() + 1) + "' WHERE title = '" + getBookId(transaction.getBook().getTitle()) + "'";
            stmt.execute(query);
            stmt.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}