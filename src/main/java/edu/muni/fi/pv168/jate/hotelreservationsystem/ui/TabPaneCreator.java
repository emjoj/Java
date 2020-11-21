package edu.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JTabbedPane;

final class TabPaneCreator {

    private final JTabbedPane tabbedPane;

    TabPaneCreator() {
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

