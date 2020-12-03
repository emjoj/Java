package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private Long id;
    private Person owner;
    private Room room;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(room, that.room) &&
                Objects.equals(checkinDate, that.checkinDate) &&
                Objects.equals(checkoutDate, that.checkoutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, room, checkinDate, checkoutDate);
    }

    public Reservation(Person owner, Room room, LocalDate checkinDate, LocalDate checkoutDate) {
        this.owner = owner;
        this.room = room;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                " owner=" + owner +
                ", room=" + room +
                ", checkinDate=" + checkinDate +
                ", checkoutDate=" + checkoutDate +
                ", id=" + id +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(LocalDate checkinDate) {
        this.checkinDate = checkinDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
}
