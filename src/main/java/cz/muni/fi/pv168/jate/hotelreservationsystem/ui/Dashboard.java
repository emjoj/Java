package cz.muni.fi.pv168.jate.hotelreservationsystem.ui;

import cz.muni.fi.pv168.jate.hotelreservationsystem.data.*;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public final class Dashboard {

    private final JFrame frame;
    private final PersonDao personDao;
    private final ReservationDao reservationDao;

    public Dashboard(PersonDao personDao, ReservationDao reservationDao) {
        this.personDao = personDao;
        this.reservationDao = reservationDao;
        frame = createFrame();
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        var frame = new JFrame("Hotel Reservation System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new TabPaneCreator(this).getTabbedPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        return frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public PersonDao getPersonDao() {
        return personDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }
}
