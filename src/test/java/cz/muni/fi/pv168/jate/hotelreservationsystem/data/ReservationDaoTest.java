package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Room;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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
        personDao = new PersonDao(dataSource);
        reservationDao = new ReservationDao(dataSource, personDao);
    }

    @Test
    void createReservation() {
        var tomik = new Person("Tomík", "Drobík",
                LocalDate.of(1998,1, 20), "123456789kek");
        var room = new Room(1L, RoomType.SMALL);
        var reservation = new Reservation(tomik, room,
                LocalDate.of(1996,1, 20),
                LocalDate.of(1998,1, 20));

        personDao.create(tomik);
        reservationDao.create(reservation);

        assertThat(reservation.getId())
                .isNotNull();
        assertThat(reservationDao.findAll())
                .usingFieldByFieldElementComparator()
                .containsExactly(reservation);
    }

    @AfterEach
    void cleanUp() {
        reservationDao.dropTable();
        personDao.dropTable();
    }
}