package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Reservation;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class ReservationDao {
    private final DataSource dataSource;

    public ReservationDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    public void create(Reservation reservation) {
        if (reservation.getId() != null) {
            throw new IllegalArgumentException("Person already has ID: " + person);
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO PERSON (FIRST_NAME, LAST_NAME, BIRTH_DATE, EVIDEDNCEID, EMAIL, PHONE_NUMBER) VALUES (?, ?, ?, ?, ?, ?)",
                     RETURN_GENERATED_KEYS)) {
            st.setString(1, person.getFirstName());
            st.setString(2, person.getLastName());
            st.setDate(3, Date.valueOf(person.getBirthDate()));
            st.setString(4, person.getEvidenceID());
            st.setString(5, person.getEmail());
            st.setString(6, person.getPhoneNumber());

            st.executeUpdate();
            try (var rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    person.setId(rs.getLong(1));
                } else {
                    throw new DataAccessException("Failed to fetch generated key: no key returned for person: " + person);
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store person" + person, ex);
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
                    "OWNERID BIGINT NOT NULL REFERENCES PERSON(ID)," +
                    "ROOMID BIGINT NOT NULL," +
                    "CHECKIN DATE NOT NULL," +
                    "CHECKOUT DATE NOT NULL" +
                    ")");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create RESERVATION table", ex);
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
