package GUI;

import Database.DatabaseConnect;
import a2_2001040121.LibraryTransaction;
import common.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TransactionReportGUI {
    public TransactionReportGUI() {
        System.out.println("Transaction Report GUI");
        JFrame transactionReportFrame = new JFrame("Transaction Report");
        transactionReportFrame.setSize(900, 500);
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
                // Get the values from the text fields
                String reportType = reportTypeComboBox.getSelectedItem().toString();
                System.out.println(reportType);
                DatabaseConnect databaseConnect = new DatabaseConnect();
                ArrayList<LibraryTransaction> transactionList = databaseConnect.getTransactionList();
                switch (reportType) {
                    case "All transactions":
                        System.out.println("All transactions");
                        String[][] data = new String[transactionList.size()][4];
                        for (int i = 0; i < transactionList.size(); i++) {
                            LibraryTransaction transaction = transactionList.get(i);
                            data[i][0] = transaction.getPatron().getName();
                            data[i][1] = transaction.getBook().getTitle();
                            data[i][2] = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getCheckOutDate());
                            data[i][3] = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDueDate());
                        }
                        String[] columnNames = {"Patron", "Book", "Check-out date", "Due date"};
                        // create table
                        JTable table = new JTable(data, columnNames);
                        JScrollPane scrollPane = new JScrollPane(table);
                        // add table to frame
                        transactionReportFrame.add(scrollPane, BorderLayout.CENTER);
                        transactionReportFrame.setVisible(true);
                        break;
                    case "All checked-out books":
                        System.out.println("All checked-out books");
                        String[][] data2 = new String[transactionList.size()][7];
                        for (int i = 0; i < transactionList.size(); i++) {
                            LibraryTransaction transaction = transactionList.get(i);
                            data2[i][0] = transaction.getPatron().getName();
                            data2[i][1] = transaction.getBook().getTitle();
                            data2[i][2] = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getCheckOutDate());
                            data2[i][3] = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDueDate());
                            data2[i][4] = transaction.getBook().getISBN();
                            data2[i][5] = transaction.getBook().getAuthor();
                            data2[i][6] = transaction.getBook().getGenre().toString();
                        }
                        String[] columnNames2 = {"Patron", "Book", "Check-out date", "Due date", "ISBN", "Author", "Genre"};
                        // create table
                        JTable table2 = new JTable(data2, columnNames2);
                        JScrollPane scrollPane2 = new JScrollPane(table2);
                        // add table to frame
                        transactionReportFrame.add(scrollPane2, BorderLayout.CENTER);
                        transactionReportFrame.setVisible(true);
                        break;
                    case "Overdue books":
                        System.out.println("Overdue books");
                        ArrayList<LibraryTransaction> overdueTransactionList = new ArrayList<>();
                        for (int i = 0; i < transactionList.size(); i++) {
                            LibraryTransaction transaction = transactionList.get(i);
                            if (transaction.getDueDate().before(new DateUtils().getCurrentDate())) {
                                overdueTransactionList.add(transaction);
                            }
                        }
                        String[][] data3 = new String[overdueTransactionList.size()][7];
                        for (int i = 0; i < transactionList.size(); i++) {
                            LibraryTransaction transaction = overdueTransactionList.get(i);
                            data3[i][0] = transaction.getPatron().getName();
                            data3[i][1] = transaction.getBook().getTitle();
                            data3[i][2] = transaction.getBook().getISBN();
                            data3[i][3] = transaction.getBook().getAuthor();
                            data3[i][4] = transaction.getBook().getGenre().toString();
                            data3[i][5] = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getCheckOutDate());
                            data3[i][6] = new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDueDate());
                        }
                        String[] columnNames3 = {"Patron", "Book", "ISBN", "Author", "Genre", "Check-out date", "Due date"};
                        // create table
                        JTable table3 = new JTable(data3, columnNames3);
                        JScrollPane scrollPane3 = new JScrollPane(table3);
                        // add table to frame
                        transactionReportFrame.add(scrollPane3, BorderLayout.CENTER);
                        transactionReportFrame.setVisible(true);
                        break;
                }
            }
        });
        transactionReportFrame.add(getReportButton, BorderLayout.SOUTH);
        // Display the window
        transactionReportFrame.setVisible(true);
    }
}
