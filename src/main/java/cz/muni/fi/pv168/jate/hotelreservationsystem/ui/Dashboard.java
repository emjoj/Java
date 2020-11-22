package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public final class Dashboard {

    private final JFrame frame;

    public Dashboard() {
        frame = createFrame();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Hotel Reservation System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new TabPaneCreator(frame).getTabbedPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        return frame;
    }

}
