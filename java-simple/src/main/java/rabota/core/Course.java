package rabota.core;

import java.sql.Date;

public class Course {
    private int id;
    private String name;
    private int instructorId;
    private java.util.Date timeCreated;
    private int credit;
    private int totalTime;

    public Course(int id, String name, int instructorId, Date timeCreated, int credit, int totalTime) {
        this.id = id;
        this.name = name;
        this.instructorId = instructorId;
        this.timeCreated = timeCreated;
        this.credit = credit;
        this.totalTime = totalTime;
    }

    public Course() {
    }

    public Course(String name, int instructorId, java.util.Date timeCreated, int Credit, int totalTime) {
        this.name = name;
        this.instructorId = instructorId;
        this.timeCreated = timeCreated != null ? timeCreated : new java.util.Date();
        this.credit = Credit;
        this.totalTime = totalTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getInstructorId() {
        return instructorId;
    }

    public java.util.Date getTimeCreated() {
        return timeCreated;
    }

    public int getCredit() {
        return credit;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public void setTimeCreated(java.util.Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setCredit(int Credit) {
        this.credit = Credit;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
