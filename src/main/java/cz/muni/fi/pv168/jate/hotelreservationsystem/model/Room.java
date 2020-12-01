package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

public final class Room {
    private long id;
    private RoomType roomType;

    public Room(long id, RoomType roomType) {
        this.id = id;
        this.roomType = roomType;
    }

    public long getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }
}
