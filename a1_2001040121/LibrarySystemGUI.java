package a1_2001040121;

import Database.DatabaseConnect;
import common.DateUtils;
import common.Genre;
import common.PatronType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        // handle new patron
        // The ‘New patron’ window should allow user to enter patron details through text fields (except
        //for the patron’s id, which should be auto-generated). The “New patron” window displays these
        //components:
        //a. Name
        //b. Dob (format: DD/MM/YYYY)
        //c. Email
        //d. Phone
        //e. Patron Type: combo-box for selecting REGULAR and PREMIUM respectively.
        newPatronMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new window
                JFrame newPatronFrame = new JFrame("New Patron");
                newPatronFrame.setSize(300, 300);
                newPatronFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newPatronFrame.setLayout(new GridLayout(6, 2));

                // Add components
                newPatronFrame.add(new JLabel("Name:"));
                JTextField nameTextField = new JTextField();
                newPatronFrame.add(nameTextField);

                newPatronFrame.add(new JLabel("DOB (DD/MM/YYYY):"));
                JTextField dobTextField = new JTextField();
                newPatronFrame.add(dobTextField);

                newPatronFrame.add(new JLabel("Email:"));
                JTextField emailTextField = new JTextField();
                newPatronFrame.add(emailTextField);

                newPatronFrame.add(new JLabel("Phone:"));
                JTextField phoneTextField = new JTextField();
                newPatronFrame.add(phoneTextField);

                newPatronFrame.add(new JLabel("Patron Type:"));
                String[] patronTypes = {"REGULAR", "PREMIUM"};
                JComboBox<String> patronTypeComboBox = new JComboBox<>(patronTypes);
                newPatronFrame.add(patronTypeComboBox);

                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        // Get the values from the text fields
                        String name = nameTextField.getText();
                        String dobString = dobTextField.getText().toString();
                        Date dob = null;
                        try {
                            dob = formatter.parse(dobString);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        String email = emailTextField.getText();
                        String phone = phoneTextField.getText();
                        PatronType patronType = PatronType.valueOf(patronTypeComboBox.getSelectedItem().toString());
                        // Create a new patron
                        Patron patron = new Patron(name, dob, email, phone, patronType);
                        // save to database
                        DatabaseConnect databaseConnect = new DatabaseConnect();
                        databaseConnect.insertPatron(name, dobString, email, phone, patronType.toString());
                        System.out.println(patron.toString());


                        // Close the window
                        newPatronFrame.dispose();
                    }
                });
                newPatronFrame.add(saveButton);

                // Display the window
                newPatronFrame.setVisible(true);


            }
        });
        // handle list patrons
        // The ‘List patrons’ window should display a table of all patrons in the system. The table should
        //have the following columns:
        //a. Patron ID
        //b. Name
        //c. Dob
        //d. Email
        //e. Phone
        //f. Patron Type
        listPatronsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new window
                JFrame listPatronsFrame = new JFrame("List Patrons");
                listPatronsFrame.setSize(500, 500);
                listPatronsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listPatronsFrame.setLayout(new BorderLayout());

                // Create a table
                String[] columnNames = {"Patron ID", "Name", "DOB", "Email", "Phone", "Patron Type"};
                String[][] data = {
                        {"1", "John Doe", "01/01/2000", "a@a.com", "123456", "PREMIUM"},
                        {"2", "Jane Doe", "02/01/2000", "b@b.com", "123456", "REGULAR"},
                        {"3", "John Smith", "03/01/2000", "c@c.com", "123456", "PREMIUM"},
                        {"4", "Jane Smith", "04/01/2000", "d@d.com", "123456", "REGULAR"},
                };
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                listPatronsFrame.add(scrollPane, BorderLayout.CENTER);
                // Display the window
                listPatronsFrame.setVisible(true);
            }});
        // handle new book
        // The ‘New book’ window should allow user to enter book details through text fields (except for
        //the book’s id, which should be auto-generated). The “New book” window displays these
        //components:
        //a. Title
        //b. Author
        //c. Genre: combo-box for selecting one of the genres in the Genre enum.
        //d. Year
        //e. Number of copies available
        newBookMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                        System.out.println(book.toString());

                        // Close the window
                        newBookFrame.dispose();
                    }
                });
                newBookFrame.add(saveButton);
                // Display the window
                newBookFrame.setVisible(true);

            }
        });
        // handle list books
        // The ‘List books’ window should display a table of all books in the system.
        listBooksMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new window
                JFrame listBooksFrame = new JFrame("List Books");
                listBooksFrame.setSize(500, 500);
                listBooksFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listBooksFrame.setLayout(new BorderLayout());

                // Create a table
                String[] columnNames = {"Book ID", "Title", "Author", "Genre", "Year", "Number of copies available"};
                String[][] data = {
                        {"1", "The Great Gatsby", "F. Scott Fitzgerald", "FICTION", "1925", "5"},
                        {"2", "The Catcher in the Rye", "J.D. Salinger", "NON_FICTION", "1951", "5"},
                        {"3", "The Fault in Our Stars", "John Green", "ROMANCE", "2012", "5"},
                        {"4", "Pride and Prejudice", "Jane Austen", "SCIENCE_FICTION", "1813", "5"},
                        {"5", "To Kill a Mockingbird", "Harper Lee", "FANTASY", "1960", "5"},
                        {"6", "The Book Thief", "Markus Zusak", "THRILLER", "2005", "5"},
                        {"7", "The Chronicles of Narnia", "C.S. Lewis", "BIOGRAPHY", "1956", "5"},
                        {"8", "Animal Farm", "George Orwell", "HISTORY", "1945", "5"},
                        {"9", "Gone with the Wind", "Margaret Mitchell", "SELF_HELP", "1936", "5"},
                        {"10", "The Fault in Our Stars", "John Green", "HORROR", "2012", "5"},
                };
                JTable table = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(table);
                listBooksFrame.add(scrollPane, BorderLayout.CENTER);
                // Display the window
                listBooksFrame.setVisible(true);
            }
        });
        // handle checkout book
        // The ‘Checkout book’ window should allow user to checkout book. This window displays these
        //components:
        //a. Patron: a combo-box to select a patron
        //b. Book: a combo-box to select a book
        //c. Checkout date: a label to display the system’s current date (display format:
        //DD/MM/YYYY)
        //d. Due date: text field (format: DD/MM/YYYY)
        //e. Checkout button
        checkoutBookMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame checkoutBookFrame = new JFrame("Checkout Book");
                checkoutBookFrame.setSize(300, 300);
                checkoutBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                checkoutBookFrame.setLayout(new GridLayout(5, 2));

                // Add components
                checkoutBookFrame.add(new JLabel("Patron:"));
                String[] patrons = {"John Doe", "Jane Doe", "John Smith", "Jane Smith"};
                JComboBox<String> patronComboBox = new JComboBox<>(patrons);
                checkoutBookFrame.add(patronComboBox);
                checkoutBookFrame.add(new JLabel("Book:"));
                String[] books = {"The Great Gatsby", "The Catcher in the Rye", "The Fault in Our Stars", "Pride and Prejudice", "To Kill a Mockingbird", "The Book Thief", "The Chronicles of Narnia", "Animal Farm", "Gone with the Wind", "The Fault in Our Stars"};
                JComboBox<String> bookComboBox = new JComboBox<>(books);
                checkoutBookFrame.add(bookComboBox);
                checkoutBookFrame.add(new JLabel("Checkout date:"));
                JLabel checkoutDateLabel = new JLabel(new SimpleDateFormat("dd/MM/yyyy").format(new DateUtils().getCurrentDate()));
                checkoutBookFrame.add(checkoutDateLabel);
                checkoutBookFrame.add(new JLabel("Due date:"));
                JTextField dueDateTextField = new JTextField();
                checkoutBookFrame.add(dueDateTextField);
                JButton checkoutButton = new JButton("Checkout");
                checkoutButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get the values from the text fields
                        String patron = patronComboBox.getSelectedItem().toString();
                        String book = bookComboBox.getSelectedItem().toString();
                        String dueDateString = dueDateTextField.getText().toString();
                        Date dueDate = null;
                        try {
                            dueDate = new SimpleDateFormat("dd/MM/yyyy").parse(dueDateString);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        // A dialog window should appear when user clicks the Checkout button. This dialog window
                        //should show brief details about the transaction and let the user confirm the transaction.
                        Dialog dialog = new Dialog(checkoutBookFrame, "Confirm checkout");
                        dialog.setLayout(new GridLayout(5, 2));
                        dialog.add(new JLabel("Patron:"));
                        dialog.add(new JLabel(patron));
                        dialog.add(new JLabel("Book:"));
                        dialog.add(new JLabel(book));
                        dialog.add(new JLabel("Checkout date:"));
                        dialog.add(new JLabel(new SimpleDateFormat("dd/MM/yyyy").format(new DateUtils().getCurrentDate())));
                        dialog.add(new JLabel("Due date:"));
                        dialog.add(new JLabel(dueDateString));
                        JButton confirmButton = new JButton("Confirm");
                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Close the window
                                dialog.dispose();
                                checkoutBookFrame.dispose();
                            }
                        });
                        JButton cancelButton = new JButton("Cancel");
                        cancelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Close the window
                                dialog.dispose();
                            }
                        });
                        dialog.add(confirmButton);
                        dialog.add(cancelButton);
                        dialog.setSize(300, 300);
                        dialog.setVisible(true);
                    }
                });
                checkoutBookFrame.add(checkoutButton);
                // Display the window
                checkoutBookFrame.setVisible(true);

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
                JFrame transactionReportFrame = new JFrame("Transaction Report");
                transactionReportFrame.setSize(500, 500);
                transactionReportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                transactionReportFrame.setLayout(new BorderLayout());

                // Add components
                transactionReportFrame.add(new JLabel("Report type:"));
                String[] reportTypes = {"All transactions", "All checked-out books", "Overdue books"};
                JComboBox<String> reportTypeComboBox = new JComboBox<>(reportTypes);
                transactionReportFrame.add(reportTypeComboBox, BorderLayout.NORTH);
                JButton getReportButton = new JButton("Get report");
                getReportButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Create a table
                        String[] columnNames = {"Patron", "Book", "Checkout date", "Due date", "Return date", "Fine amount"};
                        String[][] data = {
                                {"John Doe", "The Great Gatsby", "01/01/2000", "01/01/2000", "01/01/2000", "0"},
                                {"Jane Doe", "The Catcher in the Rye", "02/01/2000", "02/01/2000", "02/01/2000", "0"},
                                {"John Smith", "The Fault in Our Stars", "03/01/2000", "03/01/2000", "03/01/2000", "0"},
                                {"Jane Smith", "Pride and Prejudice", "04/01/2000", "04/01/2000", "04/01/2000", "0"},
                        };
                        JTable table = new JTable(data, columnNames);
                        JScrollPane scrollPane = new JScrollPane(table);
                        transactionReportFrame.add(scrollPane, BorderLayout.CENTER);
                        // Display the window
                        transactionReportFrame.setVisible(true);
                    }
                });
                transactionReportFrame.add(getReportButton, BorderLayout.SOUTH);
                // Display the window
                transactionReportFrame.setVisible(true);
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
                JFrame returnBookFrame = new JFrame("Return Book");
                returnBookFrame.setSize(300, 300);
                returnBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                returnBookFrame.setLayout(new GridLayout(4, 2));

                // Add components
                returnBookFrame.add(new JLabel("Select patron:"));
                String[] patrons = {"John Doe", "Jane Doe", "John Smith", "Jane Smith"};
                JComboBox<String> patronComboBox = new JComboBox<>(patrons);
                returnBookFrame.add(patronComboBox);
                returnBookFrame.add(new JLabel("Select book:"));
                String[] books = {"The Great Gatsby", "The Catcher in the Rye", "The Fault in Our Stars", "Pride and Prejudice", "To Kill a Mockingbird", "The Book Thief", "The Chronicles of Narnia", "Animal Farm", "Gone with the Wind", "The Fault in Our Stars"};
                JComboBox<String> bookComboBox = new JComboBox<>(books);
                returnBookFrame.add(bookComboBox);
                returnBookFrame.add(new JLabel("Return date:"));
                JTextField returnDateTextField = new JTextField();
                returnBookFrame.add(returnDateTextField);
                JButton returnButton = new JButton("Return");
                returnButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get the values from the text fields
                        String patron = patronComboBox.getSelectedItem().toString();
                        String book = bookComboBox.getSelectedItem().toString();
                        String returnDateString = returnDateTextField.getText().toString();
                        Date returnDate = null;
                        try {
                            returnDate = new SimpleDateFormat("dd/MM/yyyy").parse(returnDateString);
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }
                        // A dialog window should appear when user clicks the Return button. This dialog window
                        //should show brief details about the transaction and let the user confirm the transaction.
                        Dialog dialog = new Dialog(returnBookFrame, "Confirm return");
                        dialog.setLayout(new GridLayout(5, 2));
                        dialog.add(new JLabel("Patron:"));
                        dialog.add(new JLabel(patron));
                        dialog.add(new JLabel("Book:"));
                        dialog.add(new JLabel(book));
                        dialog.add(new JLabel("Return date:"));
                        dialog.add(new JLabel(returnDateString));
                        JButton confirmButton = new JButton("Confirm");
                        confirmButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Close the window
                                dialog.dispose();
                                returnBookFrame.dispose();
                            }
                        });
                        JButton cancelButton = new JButton("Cancel");
                        cancelButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // Close the window
                                dialog.dispose();
                            }
                        });
                        dialog.add(confirmButton);
                        dialog.add(cancelButton);
                        dialog.setSize(300, 300);
                        dialog.setVisible(true);
                    }

                });
                returnBookFrame.add(returnButton);
                // Display the window
                returnBookFrame.setVisible(true);
            }
        });


        setJMenuBar(menuBar);

        // Display the frame
        setVisible(true);
    }
}
