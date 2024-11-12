package rabota.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import rabota.core.Student;

import java.util.List;

@RegisterRowMapper(StudentMapper.class)
public interface StudentsDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS students (" +
            "pin CHAR(10) PRIMARY KEY NOT NULL UNIQUE, " +
            "first_name VARCHAR(50) NOT NULL, " +
            "last_name VARCHAR(50) NOT NULL, " +
            "time_created DATE NOT NULL)")
    void createStudentsTable();

    @SqlUpdate("INSERT INTO students (pin, first_name, last_name, time_created) VALUES (:pin, :firstName, :lastName, :timeCreated)")
    void insertStudent(@Bind("pin") String pin, @Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("timeCreated") java.util.Date timeCreated);

    @SqlQuery("SELECT * FROM students")
    List<Student> listStudents();
}
