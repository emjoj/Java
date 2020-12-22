package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        var person = PersonGenerator.getRandomPerson();
        personDao.create(person);

        assertThat(person.getId())
                .isNotNull();
        assertThat(personDao.findAll()).containsExactly(person);
    }

    @Test
    void findByEvidence() {
        var person = PersonGenerator.getRandomPerson();
        personDao.create(person);

        assertThat(personDao.findByEvidence(person.getEvidence()))
                .isEqualTo(person);
    }

    @Test
    void findById() {
        var person = PersonGenerator.getRandomPerson();
        personDao.create(person);

        assertThat(personDao.findById(person.getId()))
                .isEqualTo(person);
    }

}
