package GUI;

import Database.DatabaseConnect;
import a2_2001040121.LibraryTransaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReturnBookGUI {
    public ReturnBookGUI() {
        System.out.println("Return Book GUI");
        JFrame returnBookFrame = new JFrame("Return Book");
        returnBookFrame.setSize(300, 300);
        returnBookFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        returnBookFrame.setLayout(new GridLayout(4, 2));
        // query data
        DatabaseConnect databaseConnect = new DatabaseConnect();
        ArrayList< LibraryTransaction> transactionList = databaseConnect.getTransactionList();

        // Add components
        returnBookFrame.add(new JLabel("Select patron:"));
        String[] patrons = new String[transactionList.size()];
        for (int i = 0; i < transactionList.size(); i++) {
            patrons[i] = transactionList.get(i).getPatron().getName();
        }
        JComboBox<String> patronComboBox = new JComboBox<>(patrons);
        returnBookFrame.add(patronComboBox);

        returnBookFrame.add(new JLabel("Select book:"));
        String[] books = new String[transactionList.size()];
        JComboBox<String> bookComboBox = new JComboBox<>(books);
        returnBookFrame.add(bookComboBox);
        returnBookFrame.add(new JLabel("Return date:"));
        JTextField returnDateTextField = new JTextField();
        returnBookFrame.add(returnDateTextField);
        JButton returnButton = new JButton("Return");
        patronComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected patron
                String selectedPatron = patronComboBox.getSelectedItem().toString();

                // Clear the existing items in bookComboBox
                bookComboBox.removeAllItems();

                // Populate bookComboBox with books borrowed by the selected patron
                for (LibraryTransaction transaction : transactionList) {
                    if (transaction.getPatron().getName().equals(selectedPatron)) {
                        // Add books borrowed by the selected patron
                        bookComboBox.addItem(transaction.getBook().getTitle());
                    }
                }
            }
        });
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
                LibraryTransaction transaction = null;
                for (LibraryTransaction t : transactionList) {
                    if (t.getPatron().getName().equals(patron) && t.getBook().getTitle().equals(book)) {
                        transaction = t;
                        transaction.setReturnDate(returnDate);
                        break;
                    }
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
                dialog.add(new JLabel("Fine amount:"));
                dialog.add(new JLabel(String.valueOf(transaction.getFineAmount())));
                JButton confirmButton = new JButton("Confirm");
                LibraryTransaction finalTransaction = transaction;
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Update the transaction
                        databaseConnect.deleteTransaction(finalTransaction);
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
}
