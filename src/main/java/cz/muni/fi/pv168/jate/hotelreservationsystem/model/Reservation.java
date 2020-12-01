package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.time.LocalDate;

public class Reservation {
    private long id;
    private Person creator;
    private Room room;
    private LocalDate from;
    private LocalDate to;

    public Reservation(Person creator, Room room, LocalDate from, LocalDate to) {
        this.creator = creator;
        this.room = room;
        this.from = from;
        this.to = to;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
