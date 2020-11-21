package edu.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;

final class NewReservationPanel {

    private final JPanel panel;

    public NewReservationPanel() {
        panel = new JPanel();
        panel.setName("New reservation");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridy = 0;
        panel.add(new JLabel("New reservation"), gbc);

        gbc.gridy++;
        panel.add(createDatePickers(), gbc);

        gbc.gridy++;
        panel.add(new ValidReservationBlock().getPanel(), gbc);

        JButton createReservationButton = new JButton("Create reservation");
        createReservationButton.addActionListener(e -> new PersonalDataDialog());

        gbc.gridy++;
        panel.add(createReservationButton, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createDatePickers() {
        JPanel datePickerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 0, 15);

        datePickerPanel.add(createDatePickerField("Check-in date"), gbc);
        datePickerPanel.add(createDatePickerField("Check-out date"), gbc);

        return datePickerPanel;
    }

    private JPanel createDatePickerField(String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(new Label(label + ":"), BorderLayout.NORTH);

        DatePicker datePicker = new DatePicker();
        panel.add(datePicker, BorderLayout.SOUTH);

        return panel;
    }
}
