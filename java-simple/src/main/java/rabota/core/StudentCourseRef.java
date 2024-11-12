package rabota.core;

public class StudentCourseRef {
    private String studentPin;
    private int courseId;
    private java.util.Date completionDate;

    public StudentCourseRef() {
    }

    public StudentCourseRef(String studentPin, int courseId, java.util.Date completionDate) {
        this.studentPin = studentPin;
        this.courseId = courseId;
        this.completionDate = completionDate;
    }

    public String getStudentPin() {
        return studentPin;
    }

    public int getCourseId() {
        return courseId;
    }

    public java.util.Date getCompletionDate() {
        return completionDate;
    }

    public void setStudentPin(String studentPin) {
        this.studentPin = studentPin;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCompletionDate(java.util.Date completionDate) {
        this.completionDate = completionDate;
    }
}
