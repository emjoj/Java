package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String document;
    private String contact;
    private long reservationId;

    public Person(String firstName, String lastName, LocalDate birthDate, String document, String contact) {

        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setDocument(document);
        setContact(contact);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(firstName, "firstName must not be null");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(lastName, "lastName must not be null");
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = Objects.requireNonNull(birthDate, "birthDate must not be null");
    }

    public String getDocument() {return document;}

    public void setDocument(String document){this.document = document;}

    public String getContact() {return contact;}

    public void setContact(String contact){this.contact = contact;}

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) { this.reservationId = reservationId;
    }


}
