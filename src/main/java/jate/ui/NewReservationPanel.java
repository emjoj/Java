package jate.ui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;

public class NewReservationPanel {
    private JPanel panel;

    public NewReservationPanel() {
        panel = new JPanel();
        panel.setName("New reservation");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridy = 0;
        panel.add(new JLabel("New reservation"), gbc);

        gbc.gridy++;
        panel.add(createDatePickerPanel(), gbc);

        gbc.gridy++;
        panel.add(new ValidReservationBlock().getPanel(), gbc);

        JButton createReservationButton = new JButton("Create reservation");
        createReservationButton.addActionListener(e -> {
            PersonalDataDialog personalDataDialog = new PersonalDataDialog(); // TODO
        });

        gbc.gridy++;
        panel.add(createReservationButton, gbc);
    }

    private JPanel createDatePickerPanel() {
        JPanel datePickerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 15, 0, 15);

        datePickerPanel.add(createCheckPanel("in"), gbc);
        datePickerPanel.add(createCheckPanel("out"), gbc);

        return datePickerPanel;
    }

    private JPanel createCheckPanel(String inOrOut) {
        JPanel checkPanel = new JPanel();
        checkPanel.setLayout(new BorderLayout());

        checkPanel.add(new Label(String.format("Check-%s date:", inOrOut)), BorderLayout.NORTH);

        DatePicker datePicker = new DatePicker();
        checkPanel.add(datePicker, BorderLayout.SOUTH);

        return checkPanel;
    }

    public JPanel getPanel() {
        return panel;
    }
}
