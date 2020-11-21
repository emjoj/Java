package edu.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

final class PersonalDataDialog {

    private final JDialog dialog;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private final JTextField nameTextField = new JTextField(15);
    private final JTextField surnameTextField = new JTextField(15);
    private final JTextField phoneNumberTextField = new JTextField(15);
    private final JTextField emailTextField = new JTextField(15);

    PersonalDataDialog() {
        dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("Personal data");
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        panel.add(new JLabel("Name"), gbc);
        panel.add(new JLabel("Surname"), gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 3, 0, 3);
        panel.add(nameTextField, gbc);
        panel.add(surnameTextField, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;

        gbc.insets = new Insets(15, 15, 15, 15);
        panel.add(new JLabel("Contact"), gbc);
        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.gridy++;
        panel.add(new JLabel("Phone"), gbc);

        gbc.gridy++;
        panel.add(phoneNumberTextField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("E-mail"), gbc);

        gbc.gridy++;
        panel.add(emailTextField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(40, 0, 0, 0);
        JButton confirmButton = new JButton("Confirm");

        confirmButton.addActionListener(handleTextChange());
        panel.add(confirmButton, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private ActionListener handleTextChange() {
        return e -> {
            name = nameTextField.getText();
            surname = surnameTextField.getText();
            phoneNumber = phoneNumberTextField.getText();
            email = emailTextField.getText();
            dialog.dispose();
        };
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
