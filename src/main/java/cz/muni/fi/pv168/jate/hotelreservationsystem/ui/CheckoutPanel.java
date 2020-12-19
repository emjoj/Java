package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.ReservationState;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.math.BigDecimal.*;


import static java.time.temporal.ChronoUnit.DAYS;

final class CheckoutPanel {

    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField roomText;
    private JTextField priceText;
    private JTextField nightsText;
    private Reservation checkOutReservation;

    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel panel = new JPanel();
    private final JButton checkOutButton = new JButton("Check-out");
    private final JLabel totalCost = new JLabel("0");
    private final Dashboard owner;
    private JScrollPane listScroller = null;

    CheckoutPanel(Dashboard owner) {

        this.owner = owner;
        createCheckOutForm();
    }

    private void createCheckOutForm() {
        panel.setLayout(new GridBagLayout());
        panel.setName("Check-out");
        addRoomNumberTitle();

        getCheckedInReservations();

        addTitle("Guest Information");
        addGuestInformation();
        addTitle("Room Information");
        addRoomInformation();
        addTitle("Payment Information");
        addFinalSum();
        addCheckoutButton();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void addRoomNumberTitle() {
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        addLabel("Room number:");
        gbc.gridx = 1;

    }

    private void createListOfRooms(List<Long> data) {
        JList list = new JList<>(data.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);

        list.setVisibleRowCount((data.size() / 5) + 1);
        list.setFixedCellHeight(20);
        list.setFixedCellWidth(30);

        if (listScroller == null) {
            listScroller = new JScrollPane();
            panel.add(listScroller, gbc);
        }
        listScroller.setViewportView(list);
        loadData(list);
    }

    private void loadData(JList list) {
        list.addListSelectionListener(
                e -> {
                    checkOutReservation = loadRoomNumberInformation(list.getSelectedValue());

                    long nights = DAYS.between(checkOutReservation.getCheckinDate(), checkOutReservation.getCheckoutDate());
                    long totalPrice = nights * (checkOutReservation.getRoomV2().getRoomTypeV2().getPricePerNight()).longValue();

                    firstNameText.setText(checkOutReservation.getOwner().getFirstName());
                    lastNameText.setText(checkOutReservation.getOwner().getLastName());
                    roomText.setText(checkOutReservation.getRoomV2().getRoomTypeV2().getBedType().toString());
                    priceText.setText(checkOutReservation.getRoomV2().getRoomTypeV2().getPricePerNight().toString() + " $");
                    nightsText.setText(String.valueOf(nights));
                    totalCost.setText(totalPrice + " $");
                }
        );
    }

    public void getCheckedInReservations() {
        List<Long> data = new ArrayList<>();

        for (Reservation reservation : owner.getReservationDao().findByState(ReservationState.CHECKED_IN)) {
            data.add(reservation.getRoom().getId());
        }
        if (data.isEmpty()) {
            checkOutButton.setEnabled(false);
        } else {
            checkOutButton.setEnabled(true);
            data.sort(Comparator.naturalOrder());
        }
        createListOfRooms(data);
    }

    private Reservation loadRoomNumberInformation(Object selectedRoomNumber) {
        for (Reservation reservation : owner.getReservationDao().findAll()) {
            if (reservation.getRoom().getId().equals((selectedRoomNumber))) {
                return reservation;
            }
        }
        return null;
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

    private void setTextFieldFormat(JTextField textField) {
        textField.setEnabled(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setDisabledTextColor(Color.BLACK);
        panel.add(textField, gbc);
    }

    private void addGuestInformation() {
        addLabel("First Name:");
        addLabel("Last Name:");

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipady = 1;

        firstNameText = new JTextField(15);
        setTextFieldFormat(firstNameText);

        gbc.gridy++;
        lastNameText = new JTextField(15);
        setTextFieldFormat(lastNameText);
    }

    private void addRoomInformation() {
        addLabel("Room type:");
        addLabel("Price per night:");
        addLabel("Nights:");

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.ipady = 1;

        roomText = new JTextField(15);
        setTextFieldFormat(roomText);
        gbc.gridy++;
        priceText = new JTextField(15);
        setTextFieldFormat(priceText);
        gbc.gridy++;
        nightsText = new JTextField(15);
        setTextFieldFormat(nightsText);
    }

    private void addFinalSum() {
        addLabel("Total Cost:");

        gbc.gridx = 1;
        gbc.gridy = 9;

        panel.add(totalCost, gbc);
    }

    private void addCheckoutButton() {
        gbc.gridx--;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        checkOutButton.addActionListener(e -> {
            for (Reservation reservation : owner.getReservationDao().findByState(ReservationState.CHECKED_IN)) {

                if (reservation.getRoom().getId().equals((checkOutReservation.getRoom().getId()))) {
                    reservation.setState(ReservationState.CHECKED_OUT);
                    owner.getReservationDao().update(reservation);
                }
            }
            resetAllCells();
            getCheckedInReservations();
            panel.revalidate();
            panel.repaint();
        });

        panel.add(checkOutButton, gbc);
    }

    private void resetAllCells() {
        firstNameText.setText("");
        lastNameText.setText("");
        roomText.setText("");
        priceText.setText("");
        nightsText.setText("");
        checkOutReservation = null;
        totalCost.setText("0");
    }

    private void addLabel(String s) {
        panel.add(new JLabel(s), gbc);
        gbc.gridy++;
    }

}
