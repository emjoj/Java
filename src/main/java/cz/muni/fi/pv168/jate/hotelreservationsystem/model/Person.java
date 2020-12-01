package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String evidenceID;
    private String email;
    private String phoneNumber;

    public Person(String firstName, String lastName, LocalDate birthDate, String evidenceID,
                  String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.evidenceID = evidenceID;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Person(String firstName, String lastName, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getEvidenceID() {
        return evidenceID;
    }

    public void setEvidenceID(String evidenceID) {
        this.evidenceID = evidenceID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
                " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", evidenceID='" + evidenceID + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", id=" + id +
                '}';
    }
}
