package rabota.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import rabota.core.Course;

import java.util.List;

@RegisterRowMapper(CourseMapper.class)
public interface CoursesDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS courses (" +
            "id SERIAL PRIMARY KEY, " +
            "name VARCHAR(150) NOT NULL UNIQUE, " +
            "instructor_id INT NOT NULL, " +
            "time_created DATE NOT NULL, " +
            "credit SMALLINT NOT NULL, " +
            "total_time SMALLINT NOT NULL, " +
            "FOREIGN KEY (instructor_id) REFERENCES instructors(id))")
    void createCoursesTable();

    @SqlUpdate("INSERT INTO courses (name, instructor_id, time_created, credit, total_time) VALUES (:name, :instructorId, :timeCreated, :credit, :totalTime)")
    void insertCourse(@Bind("name") String name, @Bind("instructorId") int instructorId, @Bind("timeCreated") java.util.Date timeCreated, @Bind("credit") int credit, @Bind("totalTime") int totalTime);

    @SqlQuery("SELECT * FROM courses")
    List<Course> listCourses();
}
