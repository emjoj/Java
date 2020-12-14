package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.util.Objects;

public class RoomV2 {

    private long id;
    private RoomTypeV2 roomType;

    public RoomV2(Long id, RoomTypeV2 roomType) {
        this.id = id;
        this.roomType = roomType;
    }

    public Long getId() {
        return id;
    }

    public RoomTypeV2 getRoomTypeV2() {
        return roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomV2 room = (RoomV2) o;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
