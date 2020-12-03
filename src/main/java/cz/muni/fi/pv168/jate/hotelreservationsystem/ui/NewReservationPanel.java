package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import com.github.lgooddatepicker.components.DatePicker;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.*;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

final class NewReservationPanel {

    private final JPanel panel;
    private final Dashboard owner;
    private ValidReservationBlock validReservationBlock;
    private DatePicker checkinDatePicker = new DatePicker(LocalDate.now(), null);
    private DatePicker checkoutDatePicker = new DatePicker(LocalDate.now().plusDays(1), null);

    public NewReservationPanel(Dashboard owner) {
        this.owner = owner;
        panel = new JPanel(new GridBagLayout());
        panel.setName("New reservation");
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(15, 15, 15, 15);

        gbc.gridy = 0;
        panel.add(createDatePickers(), gbc);

        gbc.gridy++;
        validReservationBlock = new ValidReservationBlock();
        panel.add(validReservationBlock.getPanel(), gbc);

        gbc.gridy++;
        JButton createReservationButton = new JButton("Create reservation");
        createReservationButton.addActionListener(e -> createReservations());
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
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 0;
        panel.add(new JLabel(label + ":"), gbc);

        gbc.gridy++;
        gbc.ipady = 5;
        if (label.contains("in")) {
            checkinDatePicker.addDateChangeListener(e -> {
                if (checkinDatePicker.getDate() != null) {
                    checkoutDatePicker.disableUntil(checkinDatePicker.getDate().plusDays(1));
                }
                validateDatePickers();
            });
            panel.add(checkinDatePicker, gbc);
        } else if (label.contains("out")) {
            checkoutDatePicker.addDateChangeListener(e -> {
                if (checkoutDatePicker.getDate() != null) {
                    checkinDatePicker.disableAfter(checkoutDatePicker.getDate().minusDays(1));
                }
                validateDatePickers();
            });
            panel.add(checkoutDatePicker, gbc);
        }

        return panel;
    }

    private void validateDatePickers() {
        if (checkinDatePicker.getDate() != null && checkoutDatePicker.getDate() != null) {
            validReservationBlock.updateRoomTypeLines(getFreeRoomNumbers(
                    checkinDatePicker.getDate(),
                    checkoutDatePicker.getDate())
            );
        } else {
            validReservationBlock.getSmallCheckBox().setSelected(false);
            validReservationBlock.getSmallCheckBox().setEnabled(false);
            validReservationBlock.getMediumCheckBox().setSelected(false);
            validReservationBlock.getMediumCheckBox().setEnabled(false);
            validReservationBlock.getBigCheckBox().setSelected(false);
            validReservationBlock.getBigCheckBox().setEnabled(false);

            validReservationBlock.getSmallSpinner().setModel(new SpinnerNumberModel(0, 0, null, 1));
            validReservationBlock.getMediumSpinner().setModel(new SpinnerNumberModel(0, 0, null, 1));
            validReservationBlock.getBigSpinner().setModel(new SpinnerNumberModel(0, 0, null, 1));

            validReservationBlock.getSmallLabel().setText("max 0");
            validReservationBlock.getMediumLabel().setText("max 0");
            validReservationBlock.getBigLabel().setText("max 0");
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

    private void createReservations() {

        List<Long> freeRoomNumbers = getFreeRoomNumbers(checkinDatePicker.getDate(), checkoutDatePicker.getDate());

        NewReservationDialog personInformation = new NewReservationDialog(owner);
        if (personInformation.getName().isEmpty()) {
            return;
        }

        Person person = new Person(
                personInformation.getName(),
                personInformation.getSurname(),
                personInformation.getBirthDate(),
                personInformation.getEvidenceID()
        );
        person.setPhoneNumber(personInformation.getPhoneNumber());

        owner.getPersonDao().create(person);

        if (validReservationBlock.getSmallCheckBox().isSelected()) {
            var smallFreeRoomNumbers = freeRoomNumbers.stream()
                    .filter(number -> number < 8)
                    .collect(Collectors.toList());

            for (int i = 0; i < (int) validReservationBlock.getSmallSpinner().getValue(); i++) {
                Room freeRoom = new Room(smallFreeRoomNumbers.get(i), RoomType.SMALL);
                createReservation(freeRoom, person);
            }
        }

        if (validReservationBlock.getMediumCheckBox().isSelected()) {
            var mediumFreeRoomNumbers = freeRoomNumbers.stream()
                    .filter(number -> number >= 8 && number < 15)
                    .collect(Collectors.toList());

            for (int i = 0; i < (int) validReservationBlock.getMediumSpinner().getValue(); i++) {
                Room freeRoom = new Room(mediumFreeRoomNumbers.get(i), RoomType.MEDIUM);
                createReservation(freeRoom, person);
            }
        }

        if (validReservationBlock.getBigCheckBox().isSelected()) {
            var bigFreeRoomNumbers = freeRoomNumbers.stream()
                    .filter(number -> number >= 15 && number <= 20)
                    .collect(Collectors.toList());

            for (int i = 0; i < (int) validReservationBlock.getBigSpinner().getValue(); i++) {
                Room freeRoom = new Room(bigFreeRoomNumbers.get(i), RoomType.BIG);
                createReservation(freeRoom, person);
            }
        }
        validateDatePickers();
    }

    private void createReservation(Room room, Person person) {
        Reservation reservation = new Reservation(
                person,
                room,
                checkinDatePicker.getDate(),
                checkoutDatePicker.getDate());

        owner.getReservationDao().create(reservation);
    }
}
