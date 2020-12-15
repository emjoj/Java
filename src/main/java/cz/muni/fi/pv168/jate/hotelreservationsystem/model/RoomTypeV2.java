package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.math.BigDecimal;
import java.util.Objects;

public class RoomTypeV2 {

    RoomTypeName roomTypeName;
    int bedCount;
    BedType bedType;
    BigDecimal pricePerNight;

    public RoomTypeV2(RoomTypeName roomTypeName,
                      int bedCount, BedType bedType, BigDecimal pricePerNight) {
        this.roomTypeName = roomTypeName;
        this.bedCount = bedCount;
        this.bedType = bedType;
        this.pricePerNight = pricePerNight;
    }

    public RoomTypeName getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(RoomTypeName roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public int getBedCount() {
        return bedCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public BedType getBedType() {
        return bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomTypeV2 that = (RoomTypeV2) o;
        return roomTypeName == that.roomTypeName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomTypeName);
    }

    @Override
    public String toString() {
        return "RoomTypeV2{" +
                "roomTypeName=" + roomTypeName +
                ", bedCount=" + bedCount +
                ", bedType=" + bedType +
                ", pricePerNight=" + pricePerNight +
                '}';
    }
}
