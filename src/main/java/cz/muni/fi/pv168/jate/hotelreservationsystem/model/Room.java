package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

public final class Room {
    private long id;
    private RoomType roomType;

    public Room(Long id, RoomType roomType) {
        this.id = id;
        this.roomType = roomType;
    }

    public Long getId() {
        return id;
    }

    public RoomType getRoomType() {
        return roomType;
    }
}
