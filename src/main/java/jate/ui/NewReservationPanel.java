package jate.ui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;

public class NewReservationPanel extends JPanel {
    public NewReservationPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridy = 0;
        add(new JLabel("New reservation"), gbc);

        gbc.gridy++;
        add(createDatePickerPanel(), gbc);

        gbc.gridy++;
        add(new NrwBlock(), gbc);

        JButton createReservationButton = new JButton("Create reservation");
        createReservationButton.addActionListener(e -> {
            PersonalDataDialog personalDataDialog = new PersonalDataDialog(); // TODO
        });

        gbc.gridy++;
        add(createReservationButton, gbc);
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
}
