package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

final class ValidReservationBlock {

    private final JPanel panel;
    private JCheckBox smallCheckBox = new JCheckBox(RoomType.SMALL.name());
    private JCheckBox mediumCheckBox = new JCheckBox(RoomType.MEDIUM.name());
    private JCheckBox bigCheckBox = new JCheckBox(RoomType.BIG.name());
    private JLabel smallLabel = new JLabel("max 0");
    private JLabel mediumLabel = new JLabel("max 0");
    private JLabel bigLabel = new JLabel("max 0");
    private JSpinner smallSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
    private JSpinner mediumSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
    private JSpinner bigSpinner = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));

    ValidReservationBlock() {
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
    }

    private void initRoomTypeLine(JCheckBox checkBox, JLabel label, JSpinner spinner, GridBagConstraints gbc) {
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
                smallCheckBox.setEnabled(true);
            } else if (number < 15) {
                medium++;
                mediumCheckBox.setEnabled(true);
            } else if (number <= 20) {
                big++;
                bigCheckBox.setEnabled(true);
            }
        }

        String[] splitSmall = smallLabel.getText().split(" ");
        String smallText = splitSmall[0] + " " + small;
        smallLabel.setText(smallText);
        smallSpinner.setModel(new SpinnerNumberModel(0, 0, small, 1));

        String[] splitMedium = mediumLabel.getText().split(" ");
        String mediumText = splitMedium[0] + " " + medium;
        mediumLabel.setText(mediumText);
        mediumSpinner.setModel(new SpinnerNumberModel(0, 0, medium, 1));

        String[] splitBig = bigLabel.getText().split(" ");
        String bigText = splitBig[0] + " " + big;
        bigLabel.setText(bigText);
        bigSpinner.setModel(new SpinnerNumberModel(0, 0, big, 1));
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

    public JLabel getSmallLabel() {
        return smallLabel;
    }

    public JLabel getMediumLabel() {
        return mediumLabel;
    }

    public JLabel getBigLabel() {
        return bigLabel;
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
