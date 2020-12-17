package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.BedType;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomTypeName;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomTypeV2;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomV2;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

final class RoomDaoTest {

    private static EmbeddedDataSource dataSource;
    private RoomDao roomDao;
    private static RoomTypeDao roomTypeDao;

    @BeforeAll
    static void initTestDataSource() {
        dataSource = new EmbeddedDataSource();
        dataSource.setDatabaseName("memory:hotel-reservation-system-test");
        dataSource.setCreateDatabase("create");
        roomTypeDao = new RoomTypeDao(dataSource);
    }

    @BeforeEach
    void createRoomDao() {
        roomDao = new RoomDao(dataSource);
    }

    @AfterEach
    void cleanUp() {
        roomDao.dropTable();
    }

    @Test
    void findAll() {
        List<RoomV2> list = new ArrayList<>();
        for (int i = 1; i < 21; i++) {
            if (i <= 8) {
                list.add(new RoomV2(i,
                        new RoomTypeV2(RoomTypeName.SMALL, 1,
                                BedType.SEPARATED_BEDS, new BigDecimal(100))));
                continue;
            }
            if (i <= 15) {
                list.add(new RoomV2(i,
                        new RoomTypeV2(RoomTypeName.MEDIUM, 2,
                                BedType.QUEEN_SIZE, new BigDecimal(250))));
                continue;
            }
            list.add(new RoomV2(i,
                    new RoomTypeV2(RoomTypeName.BIG, 2,
                            BedType.KING_SIZE, new BigDecimal(500))));
        }
        assertThat(roomDao.findAll())
                .isEqualTo(list);
    }
}
