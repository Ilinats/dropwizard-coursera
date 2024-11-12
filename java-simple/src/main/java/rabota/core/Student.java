package rabota.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
    private String pin;
    private String firstName;
    private String lastName;
    private java.util.Date timeCreated;

    public Student() {
    }

    public Student(String pin, String firstName, String lastName, java.util.Date timeCreated) {
        this.pin = pin;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    @JsonProperty
    public String getPin() {
        return pin;
    }

    @JsonProperty
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty
    public String getLastName() {
        return lastName;
    }

    @JsonProperty
    public java.util.Date getTimeCreated() {
        return timeCreated;
    }
}
