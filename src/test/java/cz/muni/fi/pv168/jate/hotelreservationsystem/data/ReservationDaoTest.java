package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.*;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

final class ReservationDaoTest {
    private static EmbeddedDataSource dataSource;
    private PersonDao personDao;
    private ReservationDao reservationDao;
    private RoomDao roomDao;
    private RoomTypeDao roomTypeDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotel-reservation-system-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createReservationDao() {
        personDao = new PersonDao(dataSource);
        roomTypeDao = new RoomTypeDao(dataSource);
        roomDao = new RoomDao(dataSource);
        reservationDao = new ReservationDao(dataSource, roomDao);
    }

    @AfterEach
    void cleanUp() {
        reservationDao.dropTable();
        personDao.dropTable();
        roomDao.dropTable();
        roomTypeDao.dropTable();
    }

    @Test
    void createReservation() {
        var person = PersonGenerator.getRandomPerson();
        var room = new Room(1L, RoomType.SMALL);
        var roomV2 = new RoomV2(1, new RoomTypeV2(
                RoomTypeName.SMALL, 1, BedType.SEPARATED_BEDS, new BigDecimal(100)
        ));
        var reservation = new Reservation(person, room,
                LocalDate.of(1996, 1, 20),
                LocalDate.of(1998, 1, 20));
        reservation.setRoomV2(roomV2);
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
        var roomV2 = new RoomV2(1, new RoomTypeV2(
             RoomTypeName.SMALL, 1, BedType.SEPARATED_BEDS, new BigDecimal(100)
        ));
        var reservation = new Reservation(person, room,
                LocalDate.of(1996, 1, 20),
                LocalDate.of(1998, 1, 20));
        reservation.setRoomV2(roomV2);
        personDao.create(person);
        reservationDao.create(reservation);
        assertThat(person.getId())
                .isNotNull();
        assertThat(reservationDao.findByID(reservation.getId()))
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
        reservation.setRoomV2(roomDao.findById(1L));
        personDao.create(person);
        reservationDao.create(reservation);
        reservation.setState(ReservationState.CHECKED_IN);
        reservationDao.update(reservation);
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
        var badReservation1 = new Reservation(person1, room1,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CREATED);
        badReservation1.setRoomV2(roomDao.findById(1L));
        var goodReservation1 = new Reservation(person1, room2,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CHECKED_IN);
        goodReservation1.setRoomV2(roomDao.findById(2L));
        var goodReservation2 = new Reservation(person1, room3,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CHECKED_IN);
        goodReservation2.setRoomV2(roomDao.findById(3L));
        var badReservation2 = new Reservation(person1, room4,
                LocalDate.of(2000, 1, 20),
                LocalDate.of(2002, 1, 20),
                ReservationState.CHECKED_OUT);
        badReservation2.setRoomV2(roomDao.findById(4L));
        personDao.create(person1);
        personDao.create(person2);
        personDao.create(person3);
        personDao.create(person4);
        reservationDao.create(badReservation1);
        reservationDao.create(badReservation2);
        reservationDao.create(goodReservation1);
        reservationDao.create(goodReservation2);
        assertThat(reservationDao.findByState(ReservationState.CHECKED_IN))
                .containsExactlyInAnyOrder(goodReservation1, goodReservation2);
    }

    @Test
    void getFreeRooms() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        assertThat(reservationDao.getFreeRooms(today, tomorrow)).isNotEmpty();

        Person person = PersonGenerator.getRandomPerson();
        personDao.create(person);

        var rooms = roomDao.findAll();
        for (int index = 0; index < rooms.size(); index++) {
            var room1 = new Room(rooms.get(index).getId(), RoomType.SMALL); // TODO will be removed

            Reservation reservation = new Reservation(person, room1, today, tomorrow);
            reservation.setRoomV2(rooms.get(index));
            reservationDao.create(reservation);

            if (index == rooms.size() - 1) {
                break;
            }
            assertThat(reservationDao.getFreeRooms(today, tomorrow)).isNotEmpty();
        }
        assertThat(reservationDao.getFreeRooms(today, tomorrow)).isEmpty();
    }
}