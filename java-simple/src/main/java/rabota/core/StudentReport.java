package rabota.core;

public class StudentReport {
    private String studentName;
    private int totalCredit;
    private String courses;

    public StudentReport() {
    }

    public StudentReport(String studentName, int totalCredit, String courses) {
        this.studentName = studentName;
        this.totalCredit = totalCredit;
        this.courses = courses;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }
}