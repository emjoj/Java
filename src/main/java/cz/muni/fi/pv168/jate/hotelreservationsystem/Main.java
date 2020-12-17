package cz.muni.fi.pv168.jate.hotelreservationsystem;


import cz.muni.fi.pv168.jate.hotelreservationsystem.data.PersonDao;
import cz.muni.fi.pv168.jate.hotelreservationsystem.data.ReservationDao;
import cz.muni.fi.pv168.jate.hotelreservationsystem.data.RoomDao;
import cz.muni.fi.pv168.jate.hotelreservationsystem.data.RoomTypeDao;
import cz.muni.fi.pv168.jate.hotelreservationsystem.ui.Dashboard;
import org.apache.derby.jdbc.EmbeddedDataSource;

import javax.sql.DataSource;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = initDataSource();

        PersonDao personDao = new PersonDao(dataSource);

        RoomTypeDao roomTypeDao = new RoomTypeDao(dataSource);

        RoomDao roomDao = new RoomDao(dataSource);

        ReservationDao reservationDao = new ReservationDao(dataSource);

        EventQueue.invokeLater(() -> new Dashboard(personDao, reservationDao).show());
    }

    private static DataSource initDataSource() {
        var dataSource = new EmbeddedDataSource();
        String dbPath = System.getProperty("user.home") + "/hotel-reservation-system";
        dataSource.setDatabaseName(dbPath);
        dataSource.setCreateDatabase("create");

        return dataSource;
    }
}