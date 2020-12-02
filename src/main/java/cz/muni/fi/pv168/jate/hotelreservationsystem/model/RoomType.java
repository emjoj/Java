package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

public enum RoomType {
    SMALL,
    MEDIUM,
    BIG;

    public static RoomType GetType(Long id) {
        if (id == null ) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        if (id >= 1 && id < 8) {
            return RoomType.SMALL;
        }
        if (id >= 8 && id < 15) {
            return RoomType.MEDIUM;
        }
        if (id >= 15 && id <= 20) {
            return RoomType.BIG;
        }
        throw new IllegalArgumentException("No room has this ID");
    }
}
