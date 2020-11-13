package jate.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NrwBlock extends Box {
    private final JPanel panel;

    public NrwBlock() {
        super(BoxLayout.Y_AXIS);
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 250));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 10, 0);

        JButton addRoomTypeButton = new JButton("Add room type");
        addRoomTypeButton.addActionListener(handleRoomTypeAddition(addRoomTypeButton));

        panel.add(addRoomTypeButton);
        add(panel);
    }

    private ActionListener handleRoomTypeAddition(JButton addRoomTypeButton) {
        return e -> {
            panel.remove(addRoomTypeButton);

            JPanel currentPanel = new JPanel();

            JComboBox<String> roomTypeComboBox = new JComboBox<>(new String[]{"1-bed room type", "2-bed room type"});
            roomTypeComboBox.setPreferredSize(new Dimension(200, 30));
            currentPanel.add(roomTypeComboBox);

            JSpinner roomCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
            roomCountSpinner.setPreferredSize(new Dimension(60, 30));
            currentPanel.add(roomCountSpinner);

            JButton removeButton = new JButton("X");
            removeButton.addActionListener(e1 -> {

            });
            currentPanel.add(removeButton);

            panel.add(currentPanel);
            panel.add(addRoomTypeButton);
            panel.revalidate();
        };
    }
}
