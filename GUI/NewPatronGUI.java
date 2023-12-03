package GUI;

import Database.DatabaseConnect;
import a2_2001040121.Patron;
import common.PatronType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPatronGUI {
    public NewPatronGUI() {
        System.out.println("New Patron GUI");
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
}
