package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JTabbedPane;
import java.awt.event.*;

final class TabPaneCreator {

    private final JTabbedPane tabbedPane;
    private final Dashboard owner;

    TabPaneCreator(Dashboard owner) {
        this.owner = owner;
        tabbedPane = new JTabbedPane();
        tabbedPane.addKeyListener(exitOnEscape());
        createTabs();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    private void createTabs() {
        tabbedPane.add(new NewReservationPanel(owner).getPanel());
        tabbedPane.add(new CheckinPanel(owner).getPanel());
        tabbedPane.add(new CheckoutPanel(owner).getPanel());
    }

    private KeyListener exitOnEscape() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    owner.getFrame().dispose();
                }
            }
        };
    }
}

