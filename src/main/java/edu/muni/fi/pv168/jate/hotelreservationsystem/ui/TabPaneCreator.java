package edu.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JTabbedPane;

public class TabPaneCreator {
    private JTabbedPane tabbedPane;

    public TabPaneCreator() {
        tabbedPane = new JTabbedPane();
        createTabs();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    private void createTabs() {
        tabbedPane.add(new NewReservationPanel().getPanel());
        tabbedPane.add(new CheckinPanel().getPanel());
        tabbedPane.add(new CheckoutPanel().getPanel());
    }
}

