package jate.ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckOut implements ActionListener, ChangeListener {

    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel panel = new JPanel();
    private static JFrame frame = new JFrame();
    private JComboBox<String> roomTypes;
    private JLabel totalCost = new JLabel("0");
    private int sumPerNight;
    private JSpinner nights;

    public static void main(String[] args) {

        CheckOut checkOut = new CheckOut();
    }

    public CheckOut() {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        panel.setLayout(new GridBagLayout());

        addHeading();
        addTitle("Guest Information");
        addGuestInformation();
        addTitle("Room Information");
        addRoomInformation();
        addTitle("Payment Information");
        addFinalSum();
        addButton();
        frame.add(panel);
        frame.show();

    }

        private void addHeading(){
            gbc.gridx = 1;
            gbc.insets = new Insets(15,15,15,15);
            addLabel("Check-out Form");

        }

    private void addTitle(String title) {
        gbc.gridx --;
        gbc.gridy ++ ;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15,15,15,15);
        gbc.anchor = GridBagConstraints.CENTER;
        addLabel(title);
        gbc.ipady = 10;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    }

    public void addGuestInformation() {

        addLabel("First Name:");
        addLabel("Last Name:");
        addLabel("Contact");

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipady = 1;


        JTextField firstNameText = new JTextField(15);
        panel.add(firstNameText, gbc);

        gbc.gridy++;
        JTextField lastNameText = new JTextField(15);
        panel.add(lastNameText, gbc);

        gbc.gridy++;
        JTextField contact = new JTextField(15);
        panel.add(contact, gbc);

    }

    public void addRoomInformation(){

        addLabel("Room number:");
        addLabel("Room type:");
        addLabel("Nights:");

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.ipady = 1;

        JTextField roomNumber = new JTextField(15);
        panel.add(roomNumber, gbc);

        addRoomType();
        addNights();

    }

    public void addFinalSum(){

        addLabel("Total Cost:");
        addLabel("Payment method:");

        gbc.gridx = 1;
        gbc.gridy = 10;

        panel.add(totalCost, gbc);
        addPaymentMethod();
    }

    public void addButton(){

        gbc.gridx --;
        gbc.gridy ++;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton checkOut = new JButton("Check-out");
        checkOut.addActionListener(this);
        panel.add(checkOut,gbc);

    }

    private void addRoomType() {
        gbc.gridy++;
        String[] roomTypeStrings = {"Select","1-bed room", "2-bed room", "3-bed room"};
        roomTypes = new JComboBox<>(roomTypeStrings);
        roomTypes.addActionListener(this);
        panel.add(roomTypes, gbc);
    }

    private void addNights() {

        gbc.gridy++;
        nights = new JSpinner(new SpinnerNumberModel(0,0,100,1));
        nights.addChangeListener(this);
        panel.add(nights, gbc);
    }

    private void addLabel(String s) {
        panel.add(new JLabel(s), gbc);
        gbc.gridy++;
    }

    private void addPaymentMethod() {
        gbc.gridy++;
        JRadioButton cashMethod = new JRadioButton("Cash");
        JRadioButton cardMethod = new JRadioButton("Card");

        ButtonGroup paymentMethod = new ButtonGroup();
        paymentMethod.add(cashMethod);
        paymentMethod.add(cardMethod);

        panel.add(cashMethod, gbc);
        gbc.gridx++;
        panel.add(cardMethod, gbc);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == roomTypes){
            JComboBox<String> copyRoomTypes = (JComboBox)e.getSource();
            String types = (String)copyRoomTypes.getSelectedItem();
            switch(types){
                case "1-bed room": sumPerNight = 50;
                    break;
                case "2-bed room": sumPerNight = 100;
                    break;
                case "3-bed room": sumPerNight = 150;
                    break;
                default: sumPerNight = 0;
            }
            totalCost.setText(Integer.toString((Integer)nights.getValue()* sumPerNight));
        }

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int cost = (Integer)nights.getValue()* sumPerNight;
        totalCost.setText(Integer.toString(cost));
    }
}
