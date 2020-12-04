package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.ReservationState;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Room;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.HORIZONTAL;

final class CheckinPanel {

    private final JPanel panel;
    private final Dashboard owner;

    private final JCheckBox withReservationCheckBox = new JCheckBox();
    private final JTextField reservationIDTextField = new JTextField(5);
    private final DatePicker checkinDatePicker = new DatePicker();
    private final DatePicker checkoutDatePicker = new DatePicker();
    private JComboBox roomTypes = new JComboBox(RoomType.values());
    private final JTextField roomNumberTextField = new JTextField(5);
    private final JSpinner numberOfPeopleSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
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

        panel.add(new JLabel("reservation ID: "), constraints);
        reservationIDTextField.setEditable(false);
        panel.add(reservationIDTextField, constraints);
        reservationIDTextField.addCaretListener(e -> {
            if (!reservationIDTextField.getText().isEmpty() && (owner.getReservationDao().findByID(getReservationID()) != null))
                validateFieldsWhenReservationCreated();
        });
        withReservationCheckBox.addActionListener(e -> reservationIDTextField.setEditable(withReservationCheckBox.isSelected()));

        constraints.gridy++;

        panel.add(new JLabel("Check - in date:"), constraints);
        checkoutDatePicker.addDateChangeListener(e -> {
            fillPersonalInfoButton.setEnabled(checkThatTextBoxesAreFilled());
            updateRoomTypes(getFreeRoomNumbers(checkinDatePicker.getDate(), checkoutDatePicker.getDate()));
        });
        checkinDatePicker.setDateToToday();
        panel.add(checkinDatePicker, constraints);
        constraints.gridy++;

        panel.add(new JLabel("Check - out date:"), constraints);
        checkoutDatePicker.disableUntil(checkinDatePicker.getDate().plusDays(1));
        checkoutDatePicker.addDateChangeListener(e -> {
            fillPersonalInfoButton.setEnabled(checkThatTextBoxesAreFilled());
        });
        panel.add(checkoutDatePicker, constraints);
        constraints.gridy++;

        panel.add(new JLabel("Room type:"), constraints);
        panel.add(roomTypes, constraints);
        roomTypes.addActionListener(e -> {
            if (!withReservationCheckBox.isSelected()) {
                roomNumberTextField.setText(Long.toString(findRoom()));
            } else {
                if (getRoomType() == RoomType.SMALL) {
                    numberOfPeopleSpinner.setModel(new SpinnerNumberModel(1, 1, 2, 1));
                }
                if (getRoomType() == RoomType.MEDIUM) {
                    numberOfPeopleSpinner.setModel(new SpinnerNumberModel(1, 1, 3, 1));
                }
                if (getRoomType() == RoomType.BIG) {
                    numberOfPeopleSpinner.setModel(new SpinnerNumberModel(1, 1, 4, 1));
                }
            }
        });
        constraints.gridy++;

        panel.add(new JLabel("Room number:"), constraints);
        roomNumberTextField.addCaretListener(e -> fillPersonalInfoButton.setEnabled(checkThatTextBoxesAreFilled()));
        roomNumberTextField.setEditable(false);
        panel.add(roomNumberTextField, constraints);
        constraints.gridy++;

        panel.add(new JLabel("Number of people:"), constraints);
        numberOfPeopleSpinner.addChangeListener(e -> fillPersonalInfoButton.setEnabled(checkThatTextBoxesAreFilled()));
        panel.add(numberOfPeopleSpinner, constraints);
        constraints.gridy++;

        constraints.fill = CENTER;
        fillPersonalInfoButton.setEnabled(false);
        fillPersonalInfoButton.addActionListener(e -> {
            CheckinDialog checkinDialog = new CheckinDialog(owner.getFrame(), Integer.parseInt(numberOfPeopleSpinner.getValue().toString()));
            if (!withReservationCheckBox.isSelected()) {
                createReservation(checkinDialog.getPeopleAtOneRoom().get(0));
                checkinDialog.getPeopleAtOneRoom().remove(0);
            }
            for (Person p : checkinDialog.getPeopleAtOneRoom()) {
                owner.getPersonDao().create(p);
            }
        });
        panel.add(fillPersonalInfoButton, constraints);
    }

    private boolean checkThatTextBoxesAreFilled() {
        List<String> textBoxes = new ArrayList<>();
        if (withReservationCheckBox.isSelected()) {
            textBoxes.add(reservationIDTextField.getText());
        }
        textBoxes.add(checkinDatePicker.getText());
        textBoxes.add(checkoutDatePicker.getText());
        textBoxes.add(roomNumberTextField.getText());
        textBoxes.add(numberOfPeopleSpinner.getValue().toString());

        for (String textBox : textBoxes) {
            if (textBox.isEmpty())
                return false;
        }
        return true;
    }

    void updateRoomTypes(List<Long> roomNumbers) {
        int small = 0;
        int medium = 0;
        int big = 0;

        for (Long number : roomNumbers) {
            if (number < 8) {
                small++;
            } else if (number < 15) {
                medium++;
            } else if (number <= 20) {
                big++;
            }
        }

        if (small == 0) {
            roomTypes.removeItem(RoomType.SMALL);
        }
        if (medium == 0) {
            roomTypes.removeItem(RoomType.MEDIUM);
        }
        if (big == 0) {
            roomTypes.removeItem(RoomType.BIG);
        }
    }

    private List<Long> getFreeRoomNumbers(LocalDate checkinDate, LocalDate checkoutDate) {
        List<Reservation> currentReservations = owner.getReservationDao().findAll();

        List<Long> resultNumbers = new ArrayList<>();
        for (long roomNumber = 1; roomNumber <= 20; roomNumber++) {
            long currentRoomNumber = roomNumber;

            List<Reservation> reservationsForCurrentRoom = currentReservations.stream()
                    .filter(reservation -> reservation.getRoom().getId().equals(currentRoomNumber))
                    .collect(Collectors.toList());

            List<Reservation> NonCollidingReservationsForCurrentRoom = reservationsForCurrentRoom.stream()
                    .filter(reservation -> (reservation.getCheckinDate().compareTo(checkinDate) < 0
                            && reservation.getCheckoutDate().compareTo(checkinDate) <= 0)
                            || (reservation.getCheckinDate().compareTo(checkoutDate) >= 0
                            && reservation.getCheckoutDate().compareTo(checkinDate) > 0))
                    .collect(Collectors.toList());

            if (NonCollidingReservationsForCurrentRoom.size() == reservationsForCurrentRoom.size()) {
                resultNumbers.add(roomNumber);
            }
        }
        return resultNumbers;
    }

    private long findRoom() {
        List<Long> freeRoomNumbers = getFreeRoomNumbers(checkinDatePicker.getDate(), checkoutDatePicker.getDate());
        long freeRoomID = 0;

        if (getRoomType() == RoomType.SMALL) {
            var smallFreeRoomNumbers = freeRoomNumbers.stream()
                    .filter(number -> number < 8)
                    .collect(Collectors.toList());

            freeRoomID = smallFreeRoomNumbers.get(0);
        }

        if (getRoomType() == RoomType.MEDIUM) {
            var mediumFreeRoomNumbers = freeRoomNumbers.stream()
                    .filter(number -> number >= 8 && number < 15)
                    .collect(Collectors.toList());

            freeRoomID = mediumFreeRoomNumbers.get(0);
        }

        if (getRoomType() == RoomType.BIG) {
            var bigFreeRoomNumbers = freeRoomNumbers.stream()
                    .filter(number -> number >= 15 && number <= 20)
                    .collect(Collectors.toList());

            freeRoomID = bigFreeRoomNumbers.get(0);
        }
        return freeRoomID;
    }

    private void createReservation(Person person) {
        owner.getPersonDao().create(person);
        Reservation reservation = new Reservation(person, new Room(getRoomNumber(), (RoomType) roomTypes.getSelectedItem()), getCheckinDate(), getCheckoutDate(), ReservationState.CHECKEDIN);
        owner.getReservationDao().create(reservation);
        updateRoomTypes(getFreeRoomNumbers(checkinDatePicker.getDate(), checkoutDatePicker.getDate()));
        owner.getReservationDao().updateReservation(reservation);
    }

    private void validateFieldsWhenReservationCreated() {
        Long reservationID = getReservationID();
        Reservation reservation = owner.getReservationDao().findByID(reservationID);
        reservation.setState(ReservationState.CHECKEDIN);
        owner.getReservationDao().updateReservation(reservation);
        checkinDatePicker.setDate(reservation.getCheckinDate());
        checkoutDatePicker.setDate(reservation.getCheckoutDate());
        roomTypes.setSelectedItem(reservation.getRoom().getRoomType());
        roomNumberTextField.setText(reservation.getRoom().getId().toString());

        checkinDatePicker.setEnabled(false);
        checkoutDatePicker.setEnabled(false);
        roomTypes.setEnabled(false);
        roomNumberTextField.setEditable(false);

    }

    private Long getReservationID() {
        return Long.parseLong(reservationIDTextField.getText());
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
