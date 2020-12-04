package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.ReservationState;
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
        reservationDao = new ReservationDao(dataSource);
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

    @Test
    void findByID() {
        var tomik = new Person("Tomík", "Drobík",
                LocalDate.of(1998,1, 20), "123456789kek");
        var room = new Room(1L, RoomType.SMALL);
        var reservation = new Reservation(tomik, room,
                LocalDate.of(1996,1, 20),
                LocalDate.of(1998,1, 20));
        personDao.create(tomik);
        reservationDao.create(reservation);
        assertThat(tomik.getId())
                .isNotNull();
        assertThat(reservationDao.findByID(tomik.getId())).isEqualTo(reservation);
    }

    @Test
    void updateReservation() {
        PersonGenerator pg = new PersonGenerator();
        var boi = pg.getRandomPerson();
        var room = new Room(1L, RoomType.SMALL);
        var reservation = new Reservation(boi, room,
                LocalDate.of(2000,1, 20),
                LocalDate.of(2002,1, 20),
                ReservationState.CREATED);
        personDao.create(boi);
        reservationDao.create(reservation);
        reservation.setState(ReservationState.CHECKEDIN);
        reservationDao.updateReservation(reservation);
        assertThat(reservationDao.findByID(reservation.getId())).isEqualTo(reservation);
    }

    @Test
    void findByState() {
        PersonGenerator pg = new PersonGenerator();
        var boi = pg.getRandomPerson();
        var kek = pg.getRandomPerson();
        var kekito = pg.getRandomPerson();
        var ripito = pg.getRandomPerson();

        var room1 = new Room(1L, RoomType.SMALL);
        var room2 = new Room(2L, RoomType.SMALL);
        var room3 = new Room(3L, RoomType.SMALL);
        var room4 = new Room(4L, RoomType.SMALL);
        var badreservation1 = new Reservation(boi, room1,
                LocalDate.of(2000,1, 20),
                LocalDate.of(2002,1, 20),
                ReservationState.CREATED);
        var goodreservation1 = new Reservation(boi, room2,
                LocalDate.of(2000,1, 20),
                LocalDate.of(2002,1, 20),
                ReservationState.CHECKEDIN);
        var goodreservation2 = new Reservation(boi, room3,
                LocalDate.of(2000,1, 20),
                LocalDate.of(2002,1, 20),
                ReservationState.CHECKEDIN);
        var badreservation2 = new Reservation(boi, room4,
                LocalDate.of(2000,1, 20),
                LocalDate.of(2002,1, 20),
                ReservationState.CHECKEDOUT);
        personDao.create(boi);
        personDao.create(kek);
        personDao.create(kekito);
        personDao.create(ripito);
        reservationDao.create(badreservation1);
        reservationDao.create(badreservation2);
        reservationDao.create(goodreservation1);
        reservationDao.create(goodreservation2);
        assertThat(reservationDao.findbyState(ReservationState.CHECKEDIN))
                .containsExactlyInAnyOrder(goodreservation1, goodreservation2);
    }

    @AfterEach
    void cleanUp() {
        reservationDao.dropTable();
        personDao.dropTable();
    }
}