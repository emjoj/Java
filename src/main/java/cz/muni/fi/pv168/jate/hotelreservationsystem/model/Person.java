package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String evidenceID;
    private String phoneNumber;
    private String email;

    public Person(String firstName, String lastName, LocalDate birthDate, String evidenceID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.evidenceID = evidenceID;
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
        this.firstName = Objects.requireNonNull(firstName, "First name must not be null");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(lastName, "Last name must not be null");
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = Objects.requireNonNull(birthDate, "Birth date must not be null");
    }

    public String getEvidenceID() {
        return evidenceID;
    }

    public void setEvidenceID(String evidenceID) {
        this.evidenceID = Objects.requireNonNull(evidenceID, "Evidence ID must not be null");
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", evidenceID='" + evidenceID + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }
}
