package cz.muni.fi.pv168.jate.hotelreservationsystem;

import cz.muni.fi.pv168.jate.hotelreservationsystem.data.*;
import cz.muni.fi.pv168.jate.hotelreservationsystem.ui.Dashboard;
import org.apache.derby.jdbc.*;

import javax.sql.DataSource;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = initDataSource();

        PersonDao personDao = new PersonDao(dataSource);

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