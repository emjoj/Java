package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
