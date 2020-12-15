package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.BedType;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomTypeName;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomTypeV2;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;

public class RoomTypeDao {

    private DataSource dataSource;

    public RoomTypeDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    public void create(RoomTypeV2 roomType) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO ROOM_TYPE (NAME, BED_COUNT, BED_TYPE, PRICE_PER_NIGHT) VALUES (?, ?, ?, ?)"
                     )) {
            st.setString(1, roomType.getRoomTypeName().name());
            st.setInt(2, roomType.getBedCount());
            st.setString(3, roomType.getBedType().name());
            st.setBigDecimal(4, roomType.getPricePerNight());

            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store roomType" + roomType, ex);
        }
    }

    private void initTable() {
        if (!tableExists("ROOM_TYPE")) {
            createTable();
            fillTable();
        }
    }

    private void fillTable() {
        var small = new RoomTypeV2(RoomTypeName.SMALL, 1, BedType.SEPARATED_BEDS, new BigDecimal(100));
        var medium = new RoomTypeV2(RoomTypeName.MEDIUM, 2, BedType.QUEEN_SIZE, new BigDecimal(250));
        var big = new RoomTypeV2(RoomTypeName.BIG, 2, BedType.KING_SIZE, new BigDecimal(500));
        create(small);
        create(medium);
        create(big);
    }


    private boolean tableExists(String tableName) {
        try (var connection = dataSource.getConnection();
             var rs = connection.getMetaData().getTables(null, null, tableName, null)) {
            return rs.next();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to detect if the table " + tableName + " exists", ex);
        }
    }

    private void createTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("CREATE TABLE ROOM_TYPE (" +
                    "NAME VARCHAR(100) NOT NULL," +
                    "BED_COUNT SMALLINT NOT NULL," +
                    "BED_TYPE VARCHAR(100) NOT NULL," +
                    "PRICE_PER_NIGHT DECIMAL NOT NULL" +
                    ")");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create ROOM_TYPE table", ex);
        }
    }

    public void dropTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE ROOM_TYPE");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to drop ROOM_TYPE table", ex);
        }
    }
}
