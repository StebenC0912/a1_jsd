package GUI;

import Database.DatabaseConnect;
import a2_2001040121.Book;
import a2_2001040121.LibraryTransaction;
import a2_2001040121.Patron;
import common.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckOutBookGUI {
    public CheckOutBookGUI() {
        System.out.println("Check Out Book GUI");
        JFrame checkoutBookFrame = new JFrame("Checkout Book");
        checkoutBookFrame.setSize(300, 300);
        checkoutBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutBookFrame.setLayout(new GridLayout(5, 2));
        // query data
        DatabaseConnect databaseConnect = new DatabaseConnect();
        ArrayList<Patron> patronList = databaseConnect.getPatonList();
        String[][] data = new String[patronList.size()][6];
        for (int i = 0; i < patronList.size(); i++) {
            Patron patron = patronList.get(i);
            data[i][0] = patron.getPatronID();
            data[i][1] = patron.getName();
            data[i][2] = new SimpleDateFormat("dd/MM/yyyy").format(patron.getDOB());
            data[i][3] = patron.getEmail();
            data[i][4] = patron.getPhoneNum();
            data[i][5] = patron.getType().toString();
        }
        ArrayList<Book> bookList = databaseConnect.getBookList();
        String[][] data2 = new String[bookList.size()][6];
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            data2[i][0] = book.getISBN();
            data2[i][1] = book.getTitle();
            data2[i][2] = book.getAuthor();
            data2[i][3] = book.getGenre().toString();
            data2[i][4] = book.getPublicationYear();
            data2[i][5] = String.valueOf(book.getNumCopiesAvailable());
        }
        // Add components
        checkoutBookFrame.add(new JLabel("Patron:"));
        String[] patronsName = new String[data.length];
        for (int i = 0; i < data.length; i++) {
            patronsName[i] = data[i][1];
        }
        JComboBox<String> patronComboBox = new JComboBox<>(patronsName);
        checkoutBookFrame.add(patronComboBox);
        checkoutBookFrame.add(new JLabel("Book:"));
        String[] booksName = new String[data2.length];
        for (int i = 0; i < data2.length; i++) {
            booksName[i] = data2[i][1];
        }
        JComboBox<String> bookComboBox = new JComboBox<>(booksName);
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
                // Take patron from list
                Patron patronObj = null;
                for (int i = 0; i < patronList.size(); i++) {
                    if (patronList.get(i).getName().equals(patron)) {
                        patronObj = patronList.get(i);
                        break;
                    }
                }

                // Check if the patron has already checked out 5 books
                int count = 0;
                DatabaseConnect databaseConnect = new DatabaseConnect();
                ArrayList<LibraryTransaction> transactionList = databaseConnect.getTransactionList();
                for (LibraryTransaction transaction : transactionList) {
                    if (transaction.getPatron().getName().equals(patronObj.getName())) {
                        count++;
                    }
                }
                System.out.println(count);
                boolean isAvailable = true;
                String message = "";
                if (count >= 5 && patronObj.getType().toString().equals("PREMIUM")) {
                    isAvailable = false;
                    message = "This Premium Patron has already checked out 5 books";
                }
                if (count >= 3 && patronObj.getType().toString().equals("REGULAR")) {
                    isAvailable = false;
                    message = "This Regular Patron has already checked out 3 books";
                }
                if (dueDate.before(new DateUtils().getCurrentDate())) {
                    isAvailable = false;
                    message = "Due date must be after checkout date";
                }
                Book bookObj = null;
                for (int i = 0; i < bookList.size(); i++) {
                    if (bookList.get(i).getTitle().equals(book)) {
                        bookObj = bookList.get(i);
                        break;
                    }
                }
                if (bookObj.getNumCopiesAvailable() <= 0) {
                    isAvailable = false;
                    message = "This book is not available";
                }
                if (isAvailable) {
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
                            // save to database
                            DatabaseConnect databaseConnect = new DatabaseConnect();
                            databaseConnect.insertTransaction(patron, book, new SimpleDateFormat("dd/MM/yyyy").format(new DateUtils().getCurrentDate()), dueDateString);
//                                Close the window
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
                else {
                    Dialog dialog = new Dialog(checkoutBookFrame, "Error");
                    dialog.setLayout(new GridLayout(2, 1));
                    dialog.add(new JLabel(message));
                    JButton okButton = new JButton("OK");
                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Close the window
                            dialog.dispose();
                        }
                    });
                    dialog.add(okButton);
                    dialog.setSize(500, 300);
                    dialog.setVisible(true);
                }
            }
        });
        checkoutBookFrame.add(checkoutButton);
        // Display the window
        checkoutBookFrame.setVisible(true);

    }
}
