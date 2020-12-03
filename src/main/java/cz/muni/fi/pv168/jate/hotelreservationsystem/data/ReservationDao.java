package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Room;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ReservationDao {
    private final DataSource dataSource;

    public ReservationDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    public void create(Reservation reservation) {
        if (reservation.getId() != null) {
            throw new IllegalArgumentException("Reservation already has ID: " + reservation);
        }
        if (reservation.getOwner().getId() == null) {
            throw new IllegalArgumentException("Owner does not have an ID: " + reservation);
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO RESERVATION (OWNER_ID, ROOM_ID, CHECKIN, CHECKOUT) VALUES (?, ?, ?, ?)",
                     RETURN_GENERATED_KEYS)) {
            st.setLong(1, reservation.getOwner().getId());
            st.setLong(2, reservation.getRoom().getId());
            st.setDate(3, Date.valueOf(reservation.getCheckinDate()));
            st.setDate(4, Date.valueOf(reservation.getCheckoutDate()));

            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    reservation.setId(rs.getLong(1));
                } else {
                    throw new DataAccessException("Failed to fetch generated key: no key returned for reservation: " + reservation);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store reservation" + reservation, ex);
        }
    }

    private void initTable() {
        if (!tableExists("RESERVATION")) {
            createTable();
        }
    }

    private boolean tableExists(String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to detect if the table " + tableName + " exist", ex);
        }
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("CREATE TABLE RESERVATION (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                    "OWNER_ID BIGINT NOT NULL REFERENCES PERSON(ID)," +
                    "ROOM_ID BIGINT NOT NULL," +
                    "CHECKIN DATE NOT NULL," +
                    "CHECKOUT DATE NOT NULL" +
                    ")");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create RESERVATION table", ex);
        }
    }

    public List<Reservation> findAll() {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT R.ID, OWNER_ID, ROOM_ID, CHECKIN, CHECKOUT," +
                     " FIRST_NAME, LAST_NAME, BIRTH_DATE," +
                     " EVIDENCE, EMAIL, PHONE_NUMBER" +
                     " FROM RESERVATION AS R INNER JOIN PERSON AS P" +
                     " ON OWNER_ID = P.ID")) {

            List<Reservation> reservations = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    Reservation reservation = new Reservation(
                            new Person(rs.getLong("OWNER_ID"),
                                    rs.getString("FIRST_NAME"),
                                    rs.getString("LAST_NAME"),
                                    rs.getDate("BIRTH_DATE").toLocalDate(),
                                    rs.getString("EVIDENCE"),
                                    rs.getString("EMAIL"),
                                    rs.getString("PHONE_NUMBER")),
                            new Room(rs.getLong("ROOM_ID"),
                                    RoomType.getType(rs.getLong("ROOM_ID"))),
                            rs.getDate("CHECKIN").toLocalDate(),
                            rs.getDate("CHECKOUT").toLocalDate());
                    reservation.setId(rs.getLong("ID"));
                    reservations.add(reservation);
                }
            }
            return reservations;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load all reservations", ex);
        }
    }

    public void dropTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE RESERVATION");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to drop RESERVATION table", ex);
        }
    }

}
