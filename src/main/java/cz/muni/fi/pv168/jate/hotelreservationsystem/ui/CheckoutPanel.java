package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;
import cz.muni.fi.pv168.jate.hotelreservationsystem.data.DataAccessException;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Room;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.ReservationState;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import static java.time.temporal.ChronoUnit.DAYS;

final class CheckoutPanel {

    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField roomText;
    private JTextField priceText;
    private JTextField nightsText;
    private Reservation checkoutRoom;

    private GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel panel = new JPanel();
    private final JButton checkOutButton = new JButton("Check-out");
    private final JLabel totalCost = new JLabel("0");
    private final Dashboard owner;

    CheckoutPanel(Dashboard owner) {

        this.owner = owner;
        //owner.getPersonDao().dropTable();
        //owner.getReservationDao().dropTable();
        //getReservations();
        createCheckOutForm();
    }

    private void createCheckOutForm() {
        panel.setLayout(new GridBagLayout());
        panel.setName("Check-out");
        addRoomNumberTitle();
        if(!(getCheckedInReservations().isEmpty())){
            createListOfRooms(getCheckedInReservations());
        }else checkOutButton.setEnabled(false);

        addTitle("Guest Information");
        addGuestInformation();
        addTitle("Room Information");
        addRoomInformation();
        addTitle("Payment Information");
        addFinalSum();
        addCheckoutButton();
    }

    private void getReservations(){
        owner.getPersonDao().create(new Person("Alan","Holly",LocalDate.of(1997, 1, 13),"HU9876"));
        Person person = owner.getPersonDao().findByEvidence("HU9876");
        owner.getPersonDao().create(new Person("Erik","Cooper",LocalDate.of(1999, 1, 13),"876JOI"));
        Person person1 = owner.getPersonDao().findByEvidence("876JOI");

      owner.getReservationDao().create(new Reservation(person,
                new Room((long) 3, RoomType.SMALL), LocalDate.of(2020,12, 13),LocalDate.of(2020, 1, 25)));
     owner.getReservationDao().create(new Reservation(person1,
                new Room((long) 1, RoomType.SMALL), LocalDate.of(2020, 12, 13),LocalDate.of(2020, 1, 17)));

    }

    public JPanel getPanel() {
        return panel;
    }

    private void addRoomNumberTitle() {
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        addLabel("Room number:");
        gbc.gridx = 1;

    }

    private void createListOfRooms(List<Long> data){
        JList list = new JList(data.toArray());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);

        list.setVisibleRowCount(0);
        list.setFixedCellHeight(20);
        list.setFixedCellWidth(30);

        JScrollPane listScroller = new JScrollPane(list);
        //listScroller.setPreferredSize(new Dimension(250, 80));
        panel.add(listScroller,gbc);
        loadData(list);
    }
    private void loadData(JList list){
        list.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        checkoutRoom = loadRoomNumberInformation(list.getSelectedValue());

                        long nights = DAYS.between(checkoutRoom.getCheckinDate(), checkoutRoom.getCheckoutDate());
                        long pricePerNight = setPrice(checkoutRoom.getRoom().getRoomType());


                        firstNameText.setText(checkoutRoom.getOwner().getFirstName());
                        lastNameText.setText(checkoutRoom.getOwner().getLastName());
                        roomText.setText(checkoutRoom.getRoom().getRoomType().toString());
                        priceText.setText(String.valueOf(pricePerNight));
                        nightsText.setText(String.valueOf(nights)) ;
                        totalCost.setText(String.valueOf(pricePerNight*nights));
                    }
                }
        );
    }

    private long setPrice(RoomType roomType) {
        switch (roomType) {
            case SMALL:
                return 50;
            case MEDIUM:
                return 100;
            case BIG:
                return 150;
            default: return 0;
        }
    }


    public List<Long> getCheckedInReservations() {
        List<Long> data = new ArrayList<>();

        try
        {
            for (Reservation reservation : owner.getReservationDao().findAll()) {
                if(reservation.getState() == ReservationState.CHECKEDIN){
                    data.add(reservation.getRoom().getId());
                }
            }
            data.sort(Comparator.naturalOrder());
            checkOutButton.setEnabled(true);
            return data;
        }
        catch (DataAccessException e)
    {
        return data;
        }
    }

    private Reservation loadRoomNumberInformation(Object selectedRoomNumber){
        for (Reservation reservation : owner.getReservationDao().findAll()) {
            if (reservation.getRoom().getId().equals((selectedRoomNumber))) {

                return reservation;
            }
        }return null;
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

    private void setTextFieldFormat(JTextField textField){
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
            for (Reservation reservation : owner.getReservationDao().findAll()) {

                if (reservation.getRoom().getId().equals((checkoutRoom.getRoom().getId()))) {
                    reservation.setState(ReservationState.CHECKEDOUT);
                    owner.getReservationDao().updateReservation(reservation);
                }
            }
            panel.revalidate();
            panel.repaint();
            resetTextFields();

        });
        panel.add(checkOutButton, gbc);
    }
    private void resetTextFields(){
        firstNameText.setText("");
        lastNameText.setText("");
        roomText.setText("");
        priceText.setText("");
        nightsText.setText("");
        checkoutRoom = null;
        totalCost.setText("0");

    }

    private void addLabel(String s) {
        panel.add(new JLabel(s), gbc);
        gbc.gridy++;
    }

}
