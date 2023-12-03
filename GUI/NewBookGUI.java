package GUI;

import Database.DatabaseConnect;
import a2_2001040121.Book;
import common.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewBookGUI {
    public NewBookGUI() {
        System.out.println("New Book GUI");
        JFrame newBookFrame = new JFrame("New Book");
        newBookFrame.setSize(300, 300);
        newBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newBookFrame.setLayout(new GridLayout(7, 2));

        // Add components
        newBookFrame.add(new JLabel("Title:"));
        JTextField titleTextField = new JTextField();
        newBookFrame.add(titleTextField);
        newBookFrame.add(new JLabel("Author:"));
        JTextField authorTextField = new JTextField();
        newBookFrame.add(authorTextField);
        newBookFrame.add(new JLabel("Genre:"));
        String[] genres = {"SCIENCE_FICTION", "FANTASY", "THRILLER", "BIOGRAPHY", "HISTORY", "SELF_HELP", "HORROR"};
        JComboBox<String> genreComboBox = new JComboBox<>(genres);
        newBookFrame.add(genreComboBox);
        newBookFrame.add(new JLabel("Year:"));
        JTextField yearTextField = new JTextField();
        newBookFrame.add(yearTextField);
        newBookFrame.add(new JLabel("Number of copies available:"));
        JTextField numCopiesTextField = new JTextField();
        newBookFrame.add(numCopiesTextField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from the text fields
                String title = titleTextField.getText();
                String author = authorTextField.getText();
                String genreString = genreComboBox.getSelectedItem().toString();
                String yearString = yearTextField.getText();
                int year = Integer.parseInt(yearString);
                String numCopiesString = numCopiesTextField.getText();
                int numCopies = Integer.parseInt(numCopiesString);

                // Create a new book
                Book book = new Book(title, author, Genre.valueOf(genreString), yearString, numCopies);
                // save to database
                DatabaseConnect databaseConnect = new DatabaseConnect();
                databaseConnect.insertBook(book.getISBN(),title, author, genreString, yearString, numCopies);
                System.out.println(book.toString());

                // Close the window
                newBookFrame.dispose();
            }
        });
        newBookFrame.add(saveButton);
        // Display the window
        newBookFrame.setVisible(true);
    }
}

