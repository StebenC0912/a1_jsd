package GUI;

import Database.DatabaseConnect;
import a2_2001040121.Book;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ListBookGUI {
    public ListBookGUI() {
        System.out.println("List Book GUI");
        // Create a new window
        JFrame listBooksFrame = new JFrame("List Books");
        listBooksFrame.setSize(500, 500);
        listBooksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listBooksFrame.setLayout(new BorderLayout());

        // Create a table
        String[] columnNames = {"ISBN", "Title", "Author", "Genre", "Year", "Number of copies available"};
        ArrayList<Book> rawData = new DatabaseConnect().getBookList();
        String[][] data = new String[rawData.size()][6];
        for (int i = 0; i < rawData.size(); i++) {
            Book book = rawData.get(i);
            data[i][0] = book.getISBN();
            data[i][1] = book.getTitle();
            data[i][2] = book.getAuthor();
            data[i][3] = book.getGenre().toString();
            data[i][4] = book.getPublicationYear();
            data[i][5] = String.valueOf(book.getNumCopiesAvailable());
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        listBooksFrame.add(scrollPane, BorderLayout.CENTER);
        // Display the window
        listBooksFrame.setVisible(true);
    }
}

