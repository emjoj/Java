package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;

final class NewReservationDialog {

    private final JDialog dialog;
    private final JTextField nameTextField = new JTextField(15);
    private final JTextField surnameTextField = new JTextField(15);
    private final DatePicker birthDateDatePicker = new DatePicker();
    private final JTextField evidenceIDTextField = new JTextField(15);
    private final JTextField phoneNumberTextField = new JTextField(15);
    private final JTextField emailTextField = new JTextField(15);
    private final JButton confirmButton = new JButton("Confirm");
    private boolean confirmed = false;

    NewReservationDialog(Dashboard owner) {
        dialog = new JDialog(owner.getFrame());
        initNewReservationDialog();
    }

    private void initNewReservationDialog() {
        dialog.setModal(true);
        dialog.setTitle("Personal data");
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.ipady = 10;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        addRequiredLabel("Name", panel, gbc);
        addRequiredLabel("Surname", panel, gbc);

        nameTextField.addCaretListener(e -> validateRequiredFields());
        surnameTextField.addCaretListener(e -> validateRequiredFields());

        gbc.gridy++;
        panel.add(nameTextField, gbc);
        panel.add(surnameTextField, gbc);

        gbc.gridy++;
        addRequiredLabel("Date of birth", panel, gbc);
        addRequiredLabel("Evidence ID", panel, gbc);

        birthDateDatePicker.addDateChangeListener(e -> validateRequiredFields());
        gbc.gridy++;
        gbc.ipadx = 12;
        gbc.ipady = 5;
        panel.add(birthDateDatePicker, gbc);

        evidenceIDTextField.addCaretListener(e -> validateRequiredFields());
        gbc.ipadx = 0;
        gbc.ipady = 10;
        panel.add(evidenceIDTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 0, 10, 0);
        panel.add(new JLabel("Contact information"), gbc);

        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridy++;
        addRequiredLabel("Phone Number", panel, gbc);

        phoneNumberTextField.addCaretListener(e -> validateRequiredFields());
        gbc.gridy++;
        panel.add(phoneNumberTextField, gbc);

        gbc.gridy++;
        panel.add(new JLabel("E-mail"), gbc);

        gbc.gridy++;
        panel.add(emailTextField, gbc);

        confirmButton.setEnabled(false);
        confirmButton.addActionListener(e -> {
            confirmed = true;
            dialog.dispose();
        });
        gbc.gridy++;
        gbc.insets = new Insets(20, 0, 10, 0);
        gbc.ipady = 0;
        panel.add(confirmButton, gbc);

        dialog.add(panel);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }

    private void addRequiredLabel(String text, JPanel panel, GridBagConstraints gbc) {
        gbc.insets = new Insets(0, 3, 0, 3);

        JPanel requiredLabelPanel = new JPanel();
        requiredLabelPanel.setPreferredSize(new Dimension(100, 15));

        requiredLabelPanel.add(new JLabel(text));

        JLabel redStar = new JLabel("*");
        redStar.setForeground(Color.RED);
        requiredLabelPanel.add(redStar);

        panel.add(requiredLabelPanel, gbc);
    }

    private void validateRequiredFields() {
        confirmButton.setEnabled(!nameTextField.getText().isEmpty()
                        && !surnameTextField.getText().isEmpty()
                        && birthDateDatePicker.getDate() != null
                        && !evidenceIDTextField.getText().isEmpty()
                        && !phoneNumberTextField.getText().isEmpty());
    }

    public String getName() {
        return nameTextField.getText();
    }

    public String getSurname() {
        return surnameTextField.getText();
    }

    public LocalDate getBirthDate() {
        return birthDateDatePicker.getDate();
    }

    public String getEvidenceID() {
        return evidenceIDTextField.getText();
    }

    public String getPhoneNumber() {
        return phoneNumberTextField.getText();
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
