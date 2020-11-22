package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.HORIZONTAL;

final class CheckinPanel {

    private final JPanel panel;

    CheckinPanel() {
        panel = createCheckinPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    private JPanel createCheckinPanel() {
        JPanel panel = new JPanel();
        panel.setName("Check-in");

        GridBagLayout layoutManager = new GridBagLayout();
        panel.setLayout(layoutManager);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.ipady = 10;
        constraints.insets = new Insets(4, 0, 4, 0);
        constraints.fill = HORIZONTAL;
        constraints.gridy = 1;

        fillCheckinPanel(panel, constraints);

        return panel;
    }

    private void fillCheckinPanel(JPanel panel, GridBagConstraints constraints) {
        addDateLabel(panel, constraints, "Check - in date: ");
        constraints.gridy++;
        addDateLabel(panel, constraints, "Check - out date: ");
        constraints.gridy++;
        addNumeralLabel(panel, constraints, "Room number: ");
        constraints.gridy++;
        addNumeralLabel(panel, constraints, "Number of people: ");
        constraints.gridy++;

        constraints.fill = CENTER;
        addFillInPersonalInfoButton(panel, constraints);
    }

    private void addDateLabel(JPanel panel, GridBagConstraints constraints, String name) {
        JLabel checkin = new JLabel(name);
        panel.add(checkin, constraints);

        DatePicker datePicker = new DatePicker();
        panel.add(datePicker, constraints);
    }

    private void addNumeralLabel(JPanel panel, GridBagConstraints constraints, String name) {
        JLabel checkout = new JLabel(name);
        panel.add(checkout, constraints);

        JTextField textField = new JTextField(5);
        textField.setEditable(true);
        panel.add(textField, constraints);
    }

    private void addFillInPersonalInfoButton(JPanel panel, GridBagConstraints constraints) {
        JButton button = new JButton("Fill in personal information");
        button.addActionListener(e -> new CheckinDialog(3));
        panel.add(button, constraints);
    }
}
