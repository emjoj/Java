package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Person;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

final class PersonDaoTest {
    private static EmbeddedDataSource dataSource;
    private PersonDao personDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotel-reservation-system-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createPersonDao() {
        personDao = new PersonDao(dataSource);
    }
    @AfterEach
    void cleanUp() {
        personDao.dropTable();
    }

    @Test
    void createPerson() {
        var tomik = new Person("Tomík", "Drobík",
                LocalDate.of(1998,1, 20), "123456789kek");
        personDao.create(tomik);

        assertThat(tomik.getId())
                .isNotNull();
        assertThat(personDao.findAll()).containsExactly(tomik);
    }

}
