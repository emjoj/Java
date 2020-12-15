package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

final class RoomTypeDaoTest {
    private static EmbeddedDataSource dataSource;
    private RoomTypeDao roomTypeDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotel-reservation-system-test");
        dataSource.setCreateDatabase("create");
    }

    @BeforeEach
    void createPersonDao() {
        roomTypeDao = new RoomTypeDao(dataSource);
    }

    @AfterEach
    void cleanUp() {
        roomTypeDao.dropTable();
    }
}
