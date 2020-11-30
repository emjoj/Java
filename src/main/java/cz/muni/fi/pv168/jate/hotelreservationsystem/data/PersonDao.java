package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.data.Person;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PersonDao {

    private final DataSource dataSource;

    public PersonDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public void create(Person person) {
        if (person.getId() != null) {
            throw new IllegalArgumentException("Employee already has ID: " + person);
        }
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO PERSON (FIRST_NAME, LAST_NAME, BIRTH_DATE, RESERVATION_ID) VALUES (?, ?, ?, ?)",
                     RETURN_GENERATED_KEYS)) {
            st.setString(1, person.getFirstName());
            st.setString(2, person.getLastName());
            st.setDate(3, Date.valueOf(person.getBirthDate()));
            var reservation = person.getReservation();

            /*to work reservation class needs to be created and
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
                    throw new DataAccessException("Failed to fetch generated key: no key returned for employee: " + person);
                }
            }


        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store employee " + person, ex);
        }
    }
}
