package GUI;

import Database.DatabaseConnect;
import a2_2001040121.Patron;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListPatronGUI {
    public ListPatronGUI() {
        System.out.println("List Patron GUI");
        // Create a new window
        JFrame listPatronsFrame = new JFrame("List Patrons");
        listPatronsFrame.setSize(500, 500);
        listPatronsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        listPatronsFrame.setLayout(new BorderLayout());

        // Create a table
        String[] columnNames = {"Patron ID", "Name", "DOB", "Email", "Phone", "Patron Type"};
        DatabaseConnect databaseConnect = new DatabaseConnect();

        ArrayList<Patron> rawData = databaseConnect.getPatonList();
        String[][] data = new String[rawData.size()][6];
        for (int i = 0; i < rawData.size(); i++) {
            Patron patron = rawData.get(i);
            data[i][0] = patron.getPatronID();
            data[i][1] = patron.getName();
            data[i][2] = new SimpleDateFormat("dd/MM/yyyy").format(patron.getDOB());
            data[i][3] = patron.getEmail();
            data[i][4] = patron.getPhoneNum();
            data[i][5] = patron.getType().toString();
        }
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        listPatronsFrame.add(scrollPane, BorderLayout.CENTER);
        // Display the window
        listPatronsFrame.setVisible(true);
    }
}
