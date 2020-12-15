package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.util.Objects;

public class RoomV2 {

    private int id;
    private RoomTypeV2 roomType;

    public RoomV2(int id, RoomTypeV2 roomType) {
        this.id = id;
        this.roomType = roomType;
    }

    public int getId() {
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

    @Override
    public String toString() {
        return "RoomV2{" +
                "id=" + id +
                '}';
    }
}
