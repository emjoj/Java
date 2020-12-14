package cz.muni.fi.pv168.jate.hotelreservationsystem.model;

import java.time.LocalDate;
import java.util.Objects;

public final class Person {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String evidence;
    private String phoneNumber;
    private String email;

    public Person(Long id, String firstName, String lastName, LocalDate birthDate, String evidence, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.evidence = evidence;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Person(String firstName, String lastName, LocalDate birthDate, String evidence) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.evidence = evidence;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = Objects.requireNonNull(evidence, "Evidence ID must not be null");
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
                ", evidenceID='" + evidence + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                birthDate.equals(person.birthDate) &&
                evidence.equals(person.evidence) &&
                Objects.equals(phoneNumber, person.phoneNumber) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, evidence, phoneNumber, email);
    }
}
