package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

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

    @AfterEach
    void cleanUp() {
        reservationDao.dropTable();
        personDao.dropTable();
    }

    @Test
    void createReservation() {
        var person = PersonGenerator.getRandomPerson();
        var room = new Room(1L, RoomType.SMALL);
        var reservation = new Reservation(person, room,
                LocalDate.of(1996, 1, 20),
                LocalDate.of(1998, 1, 20));

        personDao.create(person);
        reservationDao.create(reservation);

        assertThat(reservation.getId())
                .isNotNull();
        assertThat(reservationDao.findAll())
                .usingFieldByFieldElementComparator()
                .containsExactly(reservation);
    }

    @Test
    void findByID() {
        var person = PersonGenerator.getRandomPerson();
        var room = new Room(1L, RoomType.SMALL);
        var reservation = new Reservation(person, room,
                LocalDate.of(1996, 1, 20),
                LocalDate.of(1998, 1, 20));
        personDao.create(person);
        reservationDao.create(reservation);
        assertThat(person.getId())
                .isNotNull();
        assertThat(reservationDao.findByID(person.getId()))
                .isEqualTo(reservation);
    }

    @Test
    void updateReservation() {
        var person = PersonGenerator.getRandomPerson();
        var room = new Room(1L, RoomType.SMALL);
        var reservation = new Reservation(person, room,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CREATED);
        personDao.create(person);
        reservationDao.create(reservation);
        reservation.setState(ReservationState.CHECKED_IN);
        reservationDao.updateReservation(reservation);
        assertThat(reservationDao.findByID(reservation.getId()))
                .isEqualTo(reservation);
    }

    @Test
    void findByState() {
        var person1 = PersonGenerator.getRandomPerson();
        var person2 = PersonGenerator.getRandomPerson();
        var person3 = PersonGenerator.getRandomPerson();
        var person4 = PersonGenerator.getRandomPerson();

        var room1 = new Room(1L, RoomType.SMALL);
        var room2 = new Room(2L, RoomType.SMALL);
        var room3 = new Room(3L, RoomType.SMALL);
        var room4 = new Room(4L, RoomType.SMALL);
        var badreservation1 = new Reservation(person1, room1,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CREATED);
        var goodreservation1 = new Reservation(person1, room2,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CHECKED_IN);
        var goodreservation2 = new Reservation(person1, room3,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CHECKED_IN);
        var badreservation2 = new Reservation(person1, room4,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CHECKED_OUT);
        personDao.create(person1);
        personDao.create(person2);
        personDao.create(person3);
        personDao.create(person4);
        reservationDao.create(badreservation1);
        reservationDao.create(badreservation2);
        reservationDao.create(goodreservation1);
        reservationDao.create(goodreservation2);
        assertThat(reservationDao.findByState(ReservationState.CHECKED_IN))
                .containsExactlyInAnyOrder(goodreservation1, goodreservation2);
    }
}