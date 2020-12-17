package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import cz.muni.fi.pv168.jate.hotelreservationsystem.model.BedType;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.Room;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomTypeName;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomTypeV2;
import cz.muni.fi.pv168.jate.hotelreservationsystem.model.RoomV2;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class RoomDao {

    private DataSource dataSource;

    public RoomDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initTable();
    }

    public void create(RoomV2 room) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "INSERT INTO ROOM (ID, ROOM_TYPE_NAME) VALUES (?, ?)"
             )) {
            st.setLong(1, room.getId());
            st.setString(2, room.getRoomTypeV2().getRoomTypeName().name());

            st.executeUpdate();
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to store room" + room, ex);
        }
    }

    public RoomV2 findById(Long id) {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "SELECT ID, NAME, BED_COUNT, BED_TYPE, PRICE_PER_NIGHT" +
                             " FROM ROOM INNER JOIN ROOM_TYPE" +
                             " ON ROOM_TYPE_NAME = NAME" +
                             " WHERE ID = ?")) {
            st.setLong(1, id);
            RoomV2 roomV2 = null;
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    roomV2 = new RoomV2(
                            rs.getLong("ID"),
                            new RoomTypeV2(
                                    RoomTypeName.valueOf(rs.getString("NAME")),
                                    rs.getInt("BED_COUNT"),
                                    BedType.valueOf(rs.getString("BED_TYPE")),
                                    rs.getBigDecimal("PRICE_PER_NIGHT")
                            )
                    );
                }
            }
            return roomV2;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to find room " + id + " in the database", ex);
        }
    }

    public List<RoomV2> findAll() {
        try (var connection = dataSource.getConnection();
             var st = connection.prepareStatement(
                     "SELECT ID, NAME, BED_COUNT, BED_TYPE, PRICE_PER_NIGHT" +
                             " FROM ROOM INNER JOIN ROOM_TYPE" +
                             " ON ROOM_TYPE_NAME = NAME")) {
            List<RoomV2> returnList = new ArrayList<RoomV2>();
            try (var rs = st.executeQuery()) {
                while (rs.next()) {
                    var roomV2 = new RoomV2(
                            rs.getLong("ID"),
                            new RoomTypeV2(
                                    RoomTypeName.valueOf(rs.getString("NAME")),
                                    rs.getInt("BED_COUNT"),
                                    BedType.valueOf(rs.getString("BED_TYPE")),
                                    rs.getBigDecimal("PRICE_PER_NIGHT")
                            )
                    );
                    returnList.add(roomV2);
                }
            }
            return returnList;
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to get all rooms", ex);
        }
    }

    private void initTable() {
        if (!tableExists("ROOM")) {
            createTable();
            fillTable();
        }
    }

    private void fillTable() {
        for (int i = 1; i < 21; i++) {
            if (i <= 8) {
                create(new RoomV2(i,
                        new RoomTypeV2(RoomTypeName.SMALL, 1,
                                BedType.SEPARATED_BEDS, new BigDecimal(100))));
                continue;
            }
            if (i <= 15) {
                create(new RoomV2(i,
                        new RoomTypeV2(RoomTypeName.MEDIUM, 2,
                                BedType.QUEEN_SIZE, new BigDecimal(250))));
                continue;
            }
            create(new RoomV2(i,
                    new RoomTypeV2(RoomTypeName.BIG, 2,
                            BedType.KING_SIZE, new BigDecimal(500))));
        }
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

            st.executeUpdate("CREATE TABLE ROOM (" +
                    "ID BIGINT PRIMARY KEY," +
                    "ROOM_TYPE_NAME VARCHAR(100) NOT NULL REFERENCES ROOM_TYPE" +
                    ")");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to create ROOM table", ex);
        }
    }

    public void dropTable() {
        try (var connection = dataSource.getConnection();
             var st = connection.createStatement()) {

            st.executeUpdate("DROP TABLE ROOM");
        } catch (SQLException ex) {
            throw new DataAccessException("Failed to drop ROOM table", ex);
        }
    }
}
