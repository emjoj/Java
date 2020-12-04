package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

final class RoomTypesPanel {

    private final JPanel panel;
    private final List<JCheckBox> checkBoxes = new ArrayList<>();
    private final List<JLabel> labels = new ArrayList<>();
    private final List<JSpinner> spinners = new ArrayList<>();
    private final JButton createReservationButton;

    RoomTypesPanel(JButton createReservationButton) {
        this.createReservationButton = createReservationButton;
        panel = new JPanel(new GridBagLayout());
        initRoomTypesPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void initRoomTypesPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;

        for (RoomType roomType : RoomType.values()) {
            JCheckBox checkBox = new JCheckBox(roomType.name());
            checkBox.setName(roomType.name());
            checkBox.setEnabled(false);
            checkBoxes.add(checkBox);

            JLabel label = new JLabel("(max 0)");
            label.setName(roomType.name());
            labels.add(label);

            JSpinner spinner = new JSpinner();
            spinner.setName(roomType.name());
            spinner.setEnabled(false);
            spinner.addChangeListener(e -> handleCheckBoxAndSpinnerChange());
            spinners.add(spinner);

            checkBox.addActionListener(e -> spinner.setEnabled(checkBox.isSelected()));
            checkBox.addActionListener(e -> handleCheckBoxAndSpinnerChange());
            panel.add(checkBox, gbc);

            gbc.insets = new Insets(0, 100, 10, 5);
            panel.add(label, gbc);

            gbc.insets = new Insets(0, 5, 10, 5);
            spinner.setPreferredSize(new Dimension(60, 30));
            panel.add(spinner, gbc);

            gbc.gridy++;
        }
    }

    private void handleCheckBoxAndSpinnerChange() {
        createReservationButton.setEnabled(validateSelection());
    }

    private boolean validateSelection() {
        for (RoomType roomType : RoomType.values()) {
            int index = roomType.ordinal();
            if ((int) spinners.get(index).getValue() > 0 && checkBoxes.get(index).isSelected()) {
                return true;
            }
        }
        return false;
    }

    void updateRoomTypeLines(List<Long> roomNumbers) {
        for (RoomType roomType : RoomType.values()) {
            int index = roomType.ordinal();
            int count = (int) roomNumbers.stream()
                    .filter(roomNumber -> RoomType.getType(roomNumber) == roomType)
                    .count();

            updateRoomTypeLine(count, checkBoxes.get(index), labels.get(index), spinners.get(index));
        }
    }

    private void updateRoomTypeLine(int value, JCheckBox checkBox, JLabel label, JSpinner spinner) {
        checkBox.setSelected(false);
        checkBox.setEnabled(value > 0);

        label.setText("(max " + value + ")");

        spinner.setModel(new SpinnerNumberModel(0, 0, value, 1));
        spinner.setEnabled(false);
    }

    public List<JCheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public List<JSpinner> getSpinners() {
        return spinners;
    }
}
