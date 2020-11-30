package cz.muni.fi.pv168.jate.hotelreservationsystem.data;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Reservation reservation;

    public Person(String firstName, String lastName, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
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

    public Reservation getReservation() {
        return reservation;
    }

    public void setDepartment(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "'" + firstName + '\'' +
                " '" + lastName + '\'' +
                ", born " + birthDate +
                ", from {" + reservation + "}" +
                " (ID " + id + ")";
    }

}
