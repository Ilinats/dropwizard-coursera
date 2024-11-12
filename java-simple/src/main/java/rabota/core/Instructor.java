package rabota.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Instructor {
    private int id;
    private String firstName;
    private String lastName;
    private java.util.Date timeCreated;

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, java.util.Date timeCreated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }

    @JsonProperty
    public int getId() {
        return id;
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
