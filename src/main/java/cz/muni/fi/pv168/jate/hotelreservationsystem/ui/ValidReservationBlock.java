package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    TODO remove room type after selection...
    TODO add room type after different selection...
    TODO add room type after removing...
    TODO initial value of JComboBox ? NULL
 */

final class ValidReservationBlock {

    private final JPanel panel;

    private List<String> data = new ArrayList<>(Arrays.asList("1-bed room", "2-bed room", "3-bed room"));

    ValidReservationBlock() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 0);

        JButton addRoomTypeButton = new JButton("Add room type");
        addRoomTypeButton.addActionListener(handleRoomTypeAddition(addRoomTypeButton));

        if (!data.isEmpty()) {
            panel.add(addRoomTypeButton);
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    private ActionListener handleRoomTypeAddition(JButton addRoomTypeButton) {
        return e -> {
            panel.remove(addRoomTypeButton);

            JPanel currentPanel = new JPanel();

            JComboBox<String> roomTypeComboBox = new JComboBox<>();
            if (data.size() == 1) {
                roomTypeComboBox.addItem(data.get(0));
                roomTypeComboBox.setSelectedItem(data.get(0));
                roomTypeComboBox.setEnabled(false); // TODO ...
            } else {
                for (String str : data) {
                    roomTypeComboBox.addItem(str);
                }
                roomTypeComboBox.setSelectedItem(null);
            }
            roomTypeComboBox.setPreferredSize(new Dimension(150, 30));
            roomTypeComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JComboBox s = (JComboBox) e.getSource();
                    String selected = (String) s.getSelectedItem();
                    data.remove(selected);
                    roomTypeComboBox.setEnabled(false); // TODO ...
                }
            });
            currentPanel.add(roomTypeComboBox);

            JSpinner roomCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
            roomCountSpinner.setPreferredSize(new Dimension(60, 30));
            currentPanel.add(roomCountSpinner);

            JButton removeButton = new JButton("X");
            removeButton.setPreferredSize(new Dimension(45, 30));
            removeButton.addActionListener(removeRoomType(currentPanel));
            currentPanel.add(removeButton);

            panel.add(currentPanel);
            if (data.size() > 1) { // TODO max size... FIX
                panel.add(addRoomTypeButton);
            }
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
