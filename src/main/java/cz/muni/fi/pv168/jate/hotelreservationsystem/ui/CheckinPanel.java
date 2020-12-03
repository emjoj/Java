package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.HORIZONTAL;

final class CheckinPanel {

    private final JPanel panel;
    private final Dashboard owner;

    private final JCheckBox withReservationCheckBox = new JCheckBox();
    private final JTextField evidenceIdTextField = new JTextField(5);
    private final DatePicker checkinDatePicker = new DatePicker();
    private final DatePicker checkoutDatePicker = new DatePicker();
    private JComboBox roomTypes = new JComboBox(RoomType.values());
    private final JTextField roomNumberTextField = new JTextField(5);
    private final JTextField numberOfPeopleTextField = new JTextField(5);
    private final JButton fillPersonalInfoButton = new JButton("Fill in personal information");

    CheckinPanel(Dashboard owner) {
        this.owner = owner;
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
        panel.add(new JLabel("With prior reservation:"), constraints);
        panel.add(withReservationCheckBox, constraints);
        constraints.gridy++;

        panel.add(new JLabel("evidence ID: "), constraints);
        evidenceIdTextField.setEditable(false);
        panel.add(evidenceIdTextField, constraints);
        evidenceIdTextField.addCaretListener(e -> evidenceIdTextField.setEditable(withReservationCheckBox.isSelected()));
        withReservationCheckBox.addActionListener(e -> evidenceIdTextField.setEditable(withReservationCheckBox.isSelected()));
        constraints.gridy++;

        panel.add(new JLabel("Check - in date:"), constraints);
        checkoutDatePicker.addDateChangeListener(e -> fillPersonalInfoButton.setEnabled(checkThatTextBoxesAreFilled()));
        checkinDatePicker.setDateToToday();
        panel.add(checkinDatePicker, constraints);
        constraints.gridy++;

        panel.add(new JLabel("Check - out date:"), constraints);
        checkoutDatePicker.disableUntil(checkinDatePicker.getDate().plusDays(1));
        checkoutDatePicker.addDateChangeListener(e -> fillPersonalInfoButton.setEnabled(checkThatTextBoxesAreFilled()));
        panel.add(checkoutDatePicker, constraints);
        constraints.gridy++;

        panel.add(new JLabel("Room type:"), constraints);
        panel.add(roomTypes, constraints);
        constraints.gridy++;

        panel.add(new JLabel("Room number:"), constraints);
        roomNumberTextField.addCaretListener(e -> fillPersonalInfoButton.setEnabled(checkThatTextBoxesAreFilled()));
        roomNumberTextField.setText("1");
        roomNumberTextField.setEditable(false);
        panel.add(roomNumberTextField, constraints);
        constraints.gridy++;

        panel.add(new JLabel("Number of people:"), constraints);
        numberOfPeopleTextField.addCaretListener(e -> fillPersonalInfoButton.setEnabled(checkThatTextBoxesAreFilled()));
        panel.add(numberOfPeopleTextField, constraints);
        constraints.gridy++;

        constraints.fill = CENTER;
        fillPersonalInfoButton.setEnabled(false);
        fillPersonalInfoButton.addActionListener(e ->
                new CheckinDialog(owner.getFrame(), Integer.parseInt(numberOfPeopleTextField.getText())));
        panel.add(fillPersonalInfoButton, constraints);
    }

    private boolean checkThatTextBoxesAreFilled() {
        List<String> textBoxes = new ArrayList<>();
        if (withReservationCheckBox.isSelected()) {
            textBoxes.add(evidenceIdTextField.getText());
        }
        textBoxes.add(checkinDatePicker.getText());
        textBoxes.add(checkoutDatePicker.getText());
        textBoxes.add(roomNumberTextField.getText());
        textBoxes.add(numberOfPeopleTextField.getText());

        for (String textBox : textBoxes) {
            if (textBox.isEmpty())
                return false;
        }
        return true;
    }

    private String getEvidenceID() {
        return evidenceIdTextField.getText();
    }

    private LocalDate getCheckinDate() {
        return checkinDatePicker.getDate();
    }

    private LocalDate getCheckoutDate() {
        return checkoutDatePicker.getDate();
    }

    private RoomType getRoomType() {
        return (RoomType) roomTypes.getSelectedItem();
    }

    private Long getRoomNumber() {
        return Long.parseLong(roomNumberTextField.getText());
    }
}
