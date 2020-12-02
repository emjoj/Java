package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;
import java.time.LocalDate;

final class NewReservationPanel {

    private final JPanel panel;

    public NewReservationPanel(Window owner) {
        panel = new JPanel();
        panel.setName("New reservation");
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridy = 0;
        panel.add(createDatePickers(), gbc);

        gbc.gridy++;
        panel.add(new ValidReservationBlock().getPanel(), gbc);

        JButton createReservationButton = new JButton("Create reservation");
        createReservationButton.addActionListener(e -> new NewReservationDialog(owner));

        gbc.gridy++;
        panel.add(createReservationButton, gbc);
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createDatePickers() {
        JPanel datePickerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 3, 0, 3);

        datePickerPanel.add(createDatePickerField("Check-in date"), gbc);
        datePickerPanel.add(createDatePickerField("Check-out date"), gbc);

        return datePickerPanel;
    }

    private JPanel createDatePickerField(String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        panel.add(new JLabel(label + ":"), gbc);

        DatePicker datePicker = new DatePicker(LocalDate.now(), null);
        gbc.gridy++;
        gbc.ipady = 5;
        panel.add(datePicker, gbc);

        return panel;
    }
}
