package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

final class RoomTypesPanel {

    private final JPanel panel;
    private final JCheckBox smallCheckBox = new JCheckBox(RoomType.SMALL.name());
    private final JCheckBox mediumCheckBox = new JCheckBox(RoomType.MEDIUM.name());
    private final JCheckBox bigCheckBox = new JCheckBox(RoomType.BIG.name());
    private final JLabel smallLabel = new JLabel("(max 0)");
    private final JLabel mediumLabel = new JLabel("(max 0)");
    private final JLabel bigLabel = new JLabel("(max 0)");
    private final JSpinner smallSpinner = new JSpinner();
    private final JSpinner mediumSpinner = new JSpinner();
    private final JSpinner bigSpinner = new JSpinner();
    private List<JSpinner> spinners = new ArrayList<>();
    private JButton createReservationButton;

    RoomTypesPanel(JButton createReservationButton) {
        this.createReservationButton = createReservationButton;
        panel = new JPanel(new GridBagLayout());
        initValidReservationBlock();
    }

    private void initValidReservationBlock() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = -1;

        initRoomTypeLine(smallCheckBox, smallLabel, smallSpinner, gbc);
        initRoomTypeLine(mediumCheckBox, mediumLabel, mediumSpinner, gbc);
        initRoomTypeLine(bigCheckBox, bigLabel, bigSpinner, gbc);

        addChangeListenersToSpinners();
        addActionListenersToCheckBoxes();
    }

    private void initRoomTypeLine(JCheckBox checkBox, JLabel label, JSpinner spinner, GridBagConstraints gbc) {
        for (RoomType roomType : RoomType.values()) {
            JSpinner roomCountSpinner = new JSpinner();
            roomCountSpinner.setName(roomType.name());
            roomCountSpinner.setEnabled(false);

            JCheckBox roomTypeCheckBox = new JCheckBox(roomType.name());
            // TODO ...
        }

        gbc.gridy++;

        spinner.setEnabled(false);

        checkBox.setEnabled(false);
        checkBox.addActionListener(e -> spinner.setEnabled(checkBox.isSelected()));
        panel.add(checkBox, gbc);

        gbc.insets = new Insets(0, 100, 10, 5);
        panel.add(label, gbc);

        gbc.insets = new Insets(0, 5, 10, 5);
        spinner.setPreferredSize(new Dimension(60, 30));
        panel.add(spinner, gbc);
    }

    private void addChangeListenersToSpinners() {
        smallSpinner.addChangeListener(e -> handleSpinnerChange());
        mediumSpinner.addChangeListener(e -> handleSpinnerChange());
        bigSpinner.addChangeListener(e -> handleSpinnerChange());
    }

    private void handleSpinnerChange() {
        createReservationButton.setEnabled(
                (int) smallSpinner.getValue() +
                (int) mediumSpinner.getValue() +
                (int) bigSpinner.getValue() > 0);
    }

    private void addActionListenersToCheckBoxes() {
            createReservationButton.setEnabled(
                    smallCheckBox.isSelected()
                    || mediumCheckBox.isSelected()
                    || bigCheckBox.isSelected());
    }

    public JPanel getPanel() {
        return panel;
    }

    void updateRoomTypeLines(List<Long> roomNumbers) {
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

        updateRoomTypeLine(small, smallCheckBox, smallLabel, smallSpinner);
        updateRoomTypeLine(medium, mediumCheckBox, mediumLabel, mediumSpinner);
        updateRoomTypeLine(big, bigCheckBox, bigLabel, bigSpinner);
    }

    private void updateRoomTypeLine(int value, JCheckBox checkBox, JLabel label, JSpinner spinner) {
        checkBox.setSelected(false);
        checkBox.setEnabled(value > 0);
        spinner.setEnabled(value > 0);
        label.setText("(max " + value + ")");
        spinner.setModel(new SpinnerNumberModel(0, 0, value, 1));
    }

    public JCheckBox getSmallCheckBox() {
        return smallCheckBox;
    }

    public JCheckBox getMediumCheckBox() {
        return mediumCheckBox;
    }

    public JCheckBox getBigCheckBox() {
        return bigCheckBox;
    }

    public JSpinner getSmallSpinner() {
        return smallSpinner;
    }

    public JSpinner getMediumSpinner() {
        return mediumSpinner;
    }

    public JSpinner getBigSpinner() {
        return bigSpinner;
    }
}
