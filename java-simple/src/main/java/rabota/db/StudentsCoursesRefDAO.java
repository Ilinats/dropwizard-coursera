package rabota.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import rabota.core.Course;
import rabota.core.StudentCourseRef;

import java.util.List;

// ako takuv course id ili student pin ne sushtestvuvat da vurna nqkakva gotina greshka ddz

@RegisterRowMapper(StudentsCoursesRefMapper.class)
public interface StudentsCoursesRefDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS students_courses_ref (" +
            "id SERIAL PRIMARY KEY, " +
            "course_id INT NOT NULL, " +
            "student_pin CHAR(10) NOT NULL, " +
            "time_finished DATE, " +
            "FOREIGN KEY (course_id) REFERENCES courses(id), " +
            "FOREIGN KEY (student_pin) REFERENCES students(pin))")
    void createStudentsCoursesRefTable();

    @SqlUpdate("INSERT INTO students_courses_ref (course_id, student_pin, time_finished) VALUES (:courseId, :studentPin, :timeFinished)")
    void insertStudentCourseRef(@Bind("courseId") int courseId, @Bind("studentPin") String studentPin, @Bind("timeFinished") java.util.Date timeFinished);


    @SqlUpdate("UPDATE students_courses_ref SET time_finished = :timeFinished WHERE course_id = :courseId AND student_pin = :studentPin")
    void updateCompletionDate(@Bind("courseId") int courseId, @Bind("studentPin") String studentPin, @Bind("timeFinished") java.util.Date timeFinished);

    @SqlQuery("SELECT * FROM students_courses_ref")
    List<StudentCourseRef> listStudentsCoursesRef();

    @SqlQuery("SELECT * FROM students_courses_ref WHERE student_pin = :studentPin")
    List<Course> listStudentCourses(@Bind("studentPin") String studentPin);


}
