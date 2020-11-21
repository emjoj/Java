package edu.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class ValidReservationBlock {
    public JPanel getPanel() {
        return panel;
    }

    private final JPanel panel;

    public ValidReservationBlock() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 0);

        JButton addRoomTypeButton = new JButton("Add room type");
        addRoomTypeButton.addActionListener(handleRoomTypeAddition(addRoomTypeButton));

        panel.add(addRoomTypeButton);
    }

    private ActionListener handleRoomTypeAddition(JButton addRoomTypeButton) {
        return e -> {
            panel.remove(addRoomTypeButton);

            JPanel currentPanel = new JPanel();

            JComboBox<String> roomTypeComboBox = new JComboBox<>(new String[]{"1-bed room type", "2-bed room type"});
            roomTypeComboBox.setPreferredSize(new Dimension(150, 30));
            currentPanel.add(roomTypeComboBox);

            JSpinner roomCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
            roomCountSpinner.setPreferredSize(new Dimension(60, 30));
            currentPanel.add(roomCountSpinner);

            JButton removeButton = new JButton("X");
            removeButton.addActionListener(removeRoomType(currentPanel));
            currentPanel.add(removeButton);

            panel.add(currentPanel);
            panel.add(addRoomTypeButton);
            panel.revalidate();
        };
    }

    private ActionListener removeRoomType(JPanel currentPanel) {
        return e -> {
            panel.remove(currentPanel);
            panel.revalidate();
            panel.repaint();
        };
    }
}
