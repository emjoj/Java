package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.BedType;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.ReservationState;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Room;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomType;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomTypeName;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomTypeV2;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomV2;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public final class ReservationDao {

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
                     "INSERT INTO RESERVATION (OWNER_ID, ROOM_ID, CHECKIN, CHECKOUT, STATE) VALUES (?, ?, ?, ?, ?)",
                     RETURN_GENERATED_KEYS)) {
            st.setLong(1, reservation.getOwner().getId());
            st.setLong(2, reservation.getRoom().getId());
            st.setDate(3, Date.valueOf(reservation.getCheckinDate()));
            st.setDate(4, Date.valueOf(reservation.getCheckoutDate()));
            st.setString(5, reservation.getState().name());

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

    public List<Reservation> findAll() {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT R.ID, OWNER_ID, ROOM_ID, CHECKIN, CHECKOUT, STATE," +
                     " FIRST_NAME, LAST_NAME, BIRTH_DATE," +
                     " EVIDENCE, EMAIL, PHONE_NUMBER, NAME, BED_COUNT, BED_TYPE, PRICE_PER_NIGHT" +
                     " FROM RESERVATION AS R" +
                     " INNER JOIN PERSON AS P" +
                     " ON OWNER_ID = P.ID" +
                     " INNER JOIN ROOM AS RO" +
                     " ON ROOM_ID = RO.ID" +
                     " INNER JOIN ROOM_TYPE AS RT" +
                     " ON ROOM_TYPE_NAME = NAME")) {

            return getReservations(st);
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load all reservations", ex);
        }
    }

    public Reservation findByID(Long ID) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT R.ID, OWNER_ID, ROOM_ID, CHECKIN, CHECKOUT, STATE," +
                     " FIRST_NAME, LAST_NAME, BIRTH_DATE," +
                     " EVIDENCE, EMAIL, PHONE_NUMBER, NAME, BED_COUNT, BED_TYPE, PRICE_PER_NIGHT" +
                     " FROM RESERVATION AS R" +
                     " INNER JOIN PERSON AS P" +
                     " ON OWNER_ID = P.ID" +
                     " INNER JOIN ROOM AS RO" +
                     " ON ROOM_ID = RO.ID" +
                     " INNER JOIN ROOM_TYPE AS RT" +
                     " ON ROOM_TYPE_NAME = NAME" +
                     " WHERE R.ID = ?")) {
            st.setLong(1, ID);
            try (var rs = st.executeQuery()) {
                Reservation reservation = null;
                while (rs.next()) {
                    reservation = new Reservation(
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
                            rs.getDate("CHECKOUT").toLocalDate(),
                            ReservationState.valueOf(rs.getString("STATE")));
                    reservation.setId(rs.getLong("ID"));
                    reservation.setRoomV2(new RoomV2(
                            rs.getLong("ROOM_ID"),
                            new RoomTypeV2(RoomTypeName.valueOf(rs.getString("NAME")),
                                    rs.getInt("BED_COUNT"),
                                    BedType.valueOf(rs.getString("BED_TYPE")),
                                    rs.getBigDecimal("PRICE_PER_NIGHT")
                            )
                    ));
                }
                return reservation;
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed find reservation by id", ex);
        }
    }

    public List<Reservation> findByState(ReservationState state) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT R.ID, OWNER_ID, ROOM_ID, CHECKIN, CHECKOUT, STATE," +
                     " FIRST_NAME, LAST_NAME, BIRTH_DATE," +
                     " EVIDENCE, EMAIL, PHONE_NUMBER, NAME, BED_COUNT, BED_TYPE, PRICE_PER_NIGHT" +
                     " FROM RESERVATION AS R" +
                     " INNER JOIN PERSON AS P" +
                     " ON OWNER_ID = P.ID" +
                     " INNER JOIN ROOM AS RO" +
                     " ON ROOM_ID = RO.ID" +
                     " INNER JOIN ROOM_TYPE AS RT" +
                     " ON ROOM_TYPE_NAME = NAME" +
                     " WHERE STATE = ?")) {

            st.setString(1, state.name());
            return getReservations(st);
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to find reservations by state: " + state, ex);
        }
    }

    private List<Reservation> getReservations(PreparedStatement st) throws SQLException {
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
                        rs.getDate("CHECKOUT").toLocalDate(),
                        ReservationState.valueOf(rs.getString("STATE")));
                reservation.setId(rs.getLong("ID"));
                reservation.setRoomV2(new RoomV2(
                        rs.getLong("ROOM_ID"),
                        new RoomTypeV2(RoomTypeName.valueOf(rs.getString("NAME")),
                                rs.getInt("BED_COUNT"),
                                BedType.valueOf(rs.getString("BED_TYPE")),
                                rs.getBigDecimal("PRICE_PER_NIGHT")
                        )
                ));
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public void update(Reservation reservation) {
        if (reservation.getId() == null) {
            throw new IllegalArgumentException("Reservation has null ID");
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "UPDATE RESERVATION SET OWNER_ID = ?, ROOM_ID = ?, CHECKIN = ?, CHECKOUT = ?, STATE = ? WHERE ID = ?")) {
            st.setLong(1, reservation.getOwner().getId());
            st.setLong(2, reservation.getRoom().getId());
            st.setDate(3, Date.valueOf(reservation.getCheckinDate()));
            st.setDate(4, Date.valueOf(reservation.getCheckoutDate()));
            st.setString(5, reservation.getState().name());
            st.setLong(6, reservation.getId());
            int rowsUpdated = st.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataAccessException("Failed to update non-existing employee: " + reservation);
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to update employee " + reservation, ex);
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
                    "ROOM_ID BIGINT NOT NULL REFERENCES ROOM(ID)," +
                    "CHECKIN DATE NOT NULL," +
                    "CHECKOUT DATE NOT NULL," +
                    "STATE VARCHAR(100) NOT NULL" +
                    ")");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create RESERVATION table", ex);
        }
    }
}
