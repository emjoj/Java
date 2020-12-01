package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.HORIZONTAL;

final class CheckinPanel {

    private final JPanel panel;
    private final Window owner;

    private JTextField evidenceIdTextField;
    private DatePicker checkinDatePicker;
    private DatePicker checkoutDatePicker;
    private JTextField roomNumberTextField;
    private JTextField numberOfPeopleTextField;

    private String evidenceID;
    private String checkinDate;
    private String checkoutDate;
    private String roomNumber;
    private String numberOfPeople;

    CheckinPanel(Window owner) {
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
        JLabel withReservartion = new JLabel("With prior reservation:");
        panel.add(withReservartion, constraints);
        JCheckBox withReservationCheckBox = new JCheckBox();
        panel.add(withReservationCheckBox, constraints);
        constraints.gridy++;

        JLabel evidenceID = new JLabel("evidence ID: ");
        panel.add(evidenceID, constraints);
        evidenceIdTextField = new JTextField(5);
        evidenceIdTextField.setEditable(false);
        panel.add(evidenceIdTextField, constraints);
        withReservationCheckBox.addActionListener(e -> evidenceIdTextField.setEditable(withReservationCheckBox.isSelected()));
        constraints.gridy++;

        JLabel checkin = new JLabel("Check - in date:");
        panel.add(checkin, constraints);
        checkinDatePicker = new DatePicker();
        checkinDatePicker.setDateToToday();
        panel.add(checkinDatePicker, constraints);
        constraints.gridy++;

        JLabel checkout = new JLabel("Check - out date:");
        panel.add(checkout, constraints);
        checkoutDatePicker = new DatePicker();
        panel.add(checkoutDatePicker, constraints);
        constraints.gridy++;

        JLabel roomType = new JLabel("Room type: ");
        panel.add(roomType, constraints);
        JComboBox roomTypes = new JComboBox(RoomType.values());
        panel.add(roomTypes, constraints);
        constraints.gridy++;

        JLabel roomNumber = new JLabel("Room number: ");
        panel.add(roomNumber, constraints);
        roomNumberTextField = new JTextField(5);
        roomNumberTextField.setText("1");
        roomNumberTextField.setEditable(false);
        panel.add(roomNumberTextField, constraints);
        constraints.gridy++;

        JLabel numberOfPeople = new JLabel("Number of people: ");
        panel.add(numberOfPeople, constraints);
        numberOfPeopleTextField = new JTextField(5);
        panel.add(numberOfPeopleTextField, constraints);
        constraints.gridy++;

        constraints.fill = CENTER;

        JButton fillPersonalInfoButton = new JButton("Fill in personal information");
        fillPersonalInfoButton.setEnabled(false);
        fillPersonalInfoButton.addActionListener(e ->
                new CheckinDialog(owner, Integer.parseInt(numberOfPeopleTextField.getText())));

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setEnabled(true);
        confirmButton.addActionListener(e -> {
            if (checkThatTextBoxesAreFilled(fillPersonalInfoButton)) {
                checkinDatePicker.setEnabled(false);
                checkoutDatePicker.setEnabled(false);
                roomTypes.setEnabled(false);
                numberOfPeopleTextField.setEditable(false);
            }
        });

        panel.add(confirmButton, constraints);
        panel.add(fillPersonalInfoButton, constraints);
    }

    private boolean checkThatTextBoxesAreFilled(JButton fillInPersonalInfButton) {
        List<String> textBoxes = new ArrayList<>();
        checkinDate = checkinDatePicker.getText();
        checkoutDate = checkoutDatePicker.getText();
        roomNumber = roomNumberTextField.getText();
        numberOfPeople = numberOfPeopleTextField.getText();
        textBoxes.add(checkinDate);
        textBoxes.add(checkoutDate);
        textBoxes.add(roomNumber);
        textBoxes.add(numberOfPeople);

        boolean isFilled = isFilled(textBoxes);
        fillInPersonalInfButton.setEnabled(isFilled);
        return isFilled;
    }

    private boolean isFilled(List<String> textBoxes) {
        for (String textBox : textBoxes) {
            if (textBox.isEmpty())
                return false;
        }
        return true;
    }
}
