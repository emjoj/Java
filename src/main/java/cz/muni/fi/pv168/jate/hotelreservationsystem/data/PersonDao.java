package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PersonDao {

    private final DataSource dataSource;

    public PersonDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void create(Person person) {
        if (person.getId() != null) {
            throw new IllegalArgumentException("Person already has ID: " + person);
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO PERSON (FIRST_NAME, LAST_NAME, BIRTH_DATE, RESERVATION_ID) VALUES (?, ?, ?, ?)",
                     RETURN_GENERATED_KEYS)) {
            st.setString(1, person.getFirstName());
            st.setString(2, person.getLastName());
            st.setDate(3, Date.valueOf(person.getBirthDate()));
            var reservation = person.getReservation();

            /*to work reservation class needs to be created

            if (reservation == null) {
                st.setNull(4, Types.BIGINT);
            } else {
                st.setLong(4, reservation.getId());
            }
            */
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

    public List<Person> findAll() {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement("SELECT ID, FIRST_NAME, LAST_NAME, BIRTH_DATE, RESERVATION_ID FROM PERSON")) {

            List<Person> persons = new ArrayList<>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    Person person = new Person(
                            rs.getString("FIRST_NAME"),
                            rs.getString("LAST_NAME"),
                            rs.getDate("BIRTH_DATE").toLocalDate());
                    person.setId(rs.getLong("ID"));
                    var reservationId = rs.getLong("RESERVATION_ID");
                    /*if (!rs.wasNull())*/
                    // reservationDao needs to be created to work
                    //{person.setReservation(reservationDao.findById(reservationId)); }
                    persons.add(person);
                }
            }
            return persons;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to load all persons", ex);
        }
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("CREATE TABLE APP.PERSON (" +
                    "ID BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
                    "FIRST_NAME VARCHAR(100) NOT NULL," +
                    "LAST_NAME VARCHAR(100) NOT NULL," +
                    "BIRTH_DATE DATE NOT NULL," +
                    "RESERVATION_ID BIGINT REFERENCES APP.RESERVATION(ID)" +
                    ")");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create PERSON table", ex);
        }
    }
}
