package jate.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Dashboard {
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
        frame.setSize(1000, 700);
        frame.add((new TabPaneCreator()).getTabbedPane());
        return frame;
    }

}
