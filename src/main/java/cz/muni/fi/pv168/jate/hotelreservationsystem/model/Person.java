package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    private long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String evidenceID;
    private String contactEmail;
    private String contactTelephone;

    public Person(String firstName, String lastName, LocalDate birthDate, String evidenceID,
                  String contactEmail, String contactTelephone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.evidenceID = evidenceID;
        this.contactEmail = contactEmail;
        this.contactTelephone = contactTelephone;
    }

    public Person(String firstName, String lastName, String contactTelephone, String contactEmail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactTelephone = contactTelephone;
        this.contactEmail = contactEmail;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone;
    }

    @Override
    public String toString() {
        return "Person{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", evidenceID='" + evidenceID + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactTelephone='" + contactTelephone + '\'' +
                "id=" + id +
                '}';
    }
}
