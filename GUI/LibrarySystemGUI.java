package GUI;

import Database.DatabaseConnect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LibrarySystemGUI extends JFrame {
    // Connect to the database
    Connection connection =  new DatabaseConnect().connect();
    public LibrarySystemGUI() {
        setTitle("Library System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        // Patron menu
        JMenu patronMenu = new JMenu("Patron");
        JMenuItem newPatronMenuItem = new JMenuItem("New patron");
        JMenuItem listPatronsMenuItem = new JMenuItem("List patrons");
        patronMenu.add(newPatronMenuItem);
        patronMenu.add(listPatronsMenuItem);
        menuBar.add(patronMenu);

        // Book menu
        JMenu bookMenu = new JMenu("Book");
        JMenuItem newBookMenuItem = new JMenuItem("New book");
        JMenuItem listBooksMenuItem = new JMenuItem("List books");
        bookMenu.add(newBookMenuItem);
        bookMenu.add(listBooksMenuItem);
        menuBar.add(bookMenu);

        // Transaction menu
        JMenu transactionMenu = new JMenu("Transaction");
        JMenuItem checkoutBookMenuItem = new JMenuItem("Checkout book");
        JMenuItem transactionReportMenuItem = new JMenuItem("Transaction report");
        JMenuItem returnBookMenuItem = new JMenuItem("Return book");



        transactionMenu.add(checkoutBookMenuItem);
        transactionMenu.add(transactionReportMenuItem);
        transactionMenu.add(returnBookMenuItem);

        
        menuBar.add(transactionMenu);
        // handle new patron option
        newPatronMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewPatronGUI();
            }
        });
        // handle list patrons
        listPatronsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new ListPatronGUI();
            }});
        // handle new book
        newBookMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NewBookGUI();
            }
        });
        // handle list books
        listBooksMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListBookGUI();
            }
        });
        // handle checkout book
        checkoutBookMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CheckOutBookGUI();
            }
        });
        // handle transaction report
        // The ‘Transaction report’ window displays transactions with these options:
        //a. All transactions
        //b. All checked-out books
        //c. Overdue books
        //The user selects an option from a combo-box and clicks a “Get report” button to see the report
        //displayed in a table.
        transactionReportMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransactionReportGUI();
            }
        });
        // handle return book
        // The “Return book” window should allow user to return book. This window displays these
        //components:
        //a. Select patron: a combo-box to select a patron
        //b. Select book: a combo-box which is empty when the user has not selected a patron. This
        //combo-box will contain the books borrowed by the selected patron.
        //c. Return date: a text field to enter the return date (format: DD/MM/YYYY)
        //d. A “Return” button to confirm the user’s returning of the book

        returnBookMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReturnBookGUI();
            }
        });


        setJMenuBar(menuBar);

        // Display the frame
        setVisible(true);
    }
}
