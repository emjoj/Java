package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import javax.swing.JTabbedPane;
import java.awt.Window;

final class TabPaneCreator {

    private final JTabbedPane tabbedPane;
    private Window owner;

    TabPaneCreator(Window owner) {
        this.owner = owner;
        tabbedPane = new JTabbedPane();
        createTabs();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    private void createTabs() {
        tabbedPane.add(new NewReservationPanel(owner).getPanel());
        tabbedPane.add(new CheckinPanel(owner).getPanel());
        tabbedPane.add(new CheckoutPanel().getPanel());
    }
}

