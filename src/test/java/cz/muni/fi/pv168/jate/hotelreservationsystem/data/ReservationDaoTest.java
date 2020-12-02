package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

final class ReservationDaoTest {
    private static EmbeddedDataSource dataSource;
    private PersonDao personDao;
    private ReservationDao reservationDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotel-reservation-system-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createReservationDao() {

    }

    @AfterEach
    void cleanUp() {
        personDao.dropTable();
        reservationDao.dropTable();
    }
}