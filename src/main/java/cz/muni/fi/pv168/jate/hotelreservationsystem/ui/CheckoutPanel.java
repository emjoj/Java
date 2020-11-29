package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Objects;

final class CheckoutPanel {

    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel panel = new JPanel();
    private JComboBox<String> roomTypes;
    private final JLabel totalCost = new JLabel("0");
    private int sumPerNight;
    private JSpinner nights;

    CheckoutPanel() {
        panel.setLayout(new GridBagLayout());
        panel.setName("Check-out");
        addRoomNumber();

        addTitle("Guest Information");
        addGuestInformation();
        addTitle("Room Information");
        addRoomInformation();
        addTitle("Payment Information");
        addFinalSum();
        addButton();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void addRoomNumber() {
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        addLabel("Room number:");

        gbc.gridx = 1;
        JTextField roomNumber = new JTextField(15);
        panel.add(roomNumber, gbc);
    }



    private void addTitle(String title) {
        gbc.gridx--;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.anchor = GridBagConstraints.CENTER;
        addLabel(title);
        gbc.ipady = 10;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    }

    private void addGuestInformation() {
        addLabel("First Name:");
        addLabel("Last Name:");

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipady = 1;

        JTextField firstNameText = new JTextField(15);
        firstNameText.setEnabled(false);
        panel.add(firstNameText, gbc);

        gbc.gridy++;
        JTextField lastNameText = new JTextField(15);
        lastNameText.setEnabled(false);
        panel.add(lastNameText, gbc);
    }

    private void addRoomInformation() {
        addLabel("Room type:");
        addLabel("Price per night:");
        addLabel("Nights:");

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.ipady = 1;

        addRoomType();
        gbc.gridy++;
        JTextField priceText = new JTextField(15);
        priceText.setEnabled(false);
        panel.add(priceText, gbc);
        addNights();
    }

    private void addFinalSum() {
        addLabel("Total Cost:");

        gbc.gridx = 1;
        gbc.gridy = 9;

        panel.add(totalCost, gbc);
    }

    private void addButton() {
        gbc.gridx--;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton checkOut = new JButton("Check-out");
        checkOut.addActionListener(e -> {
            // TODO
        });
        panel.add(checkOut, gbc);
    }

    private void addRoomType() {
        String[] roomTypeStrings = {"1-bed room", "2-bed room", "3-bed room"};
        roomTypes = new JComboBox<>(roomTypeStrings);
        roomTypes.addActionListener(handleRoomTypeChange());
        panel.add(roomTypes, gbc);
    }

    private void addNights() {
        gbc.gridy++;
        nights = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        nights.addChangeListener(handleNightCountChange());
        panel.add(nights, gbc);
    }

    private void addLabel(String s) {
        panel.add(new JLabel(s), gbc);
        gbc.gridy++;
    }

    public ActionListener handleRoomTypeChange() {
        return e -> {
            String type = (String) roomTypes.getSelectedItem();
            switch (Objects.requireNonNull(type)) {
                case "1-bed room":
                    sumPerNight = 50;
                    break;
                case "2-bed room":
                    sumPerNight = 100;
                    break;
                case "3-bed room":
                    sumPerNight = 150;
                    break;
            }
            totalCost.setText(Integer.toString((Integer) nights.getValue() * sumPerNight));
        };
    }

    public ChangeListener handleNightCountChange() {
        return e -> {
            int cost = (Integer) nights.getValue() * sumPerNight;
            totalCost.setText(Integer.toString(cost));
        };
    }
}
