package rabota.db;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import rabota.core.Instructor;

import java.util.List;

@RegisterRowMapper(InstructorMapper.class)
public interface InstructorsDAO {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS instructors (" +
            "id SERIAL PRIMARY KEY, " +
            "first_name VARCHAR(100) NOT NULL, " +
            "last_name VARCHAR(100) NOT NULL, " +
            "time_created DATE NOT NULL)")
    void createInstructorsTable();

    @SqlUpdate("INSERT INTO instructors (first_name, last_name, time_created) VALUES (:firstName, :lastName, :timeCreated)")
    void insertInstructor(@Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("timeCreated") java.util.Date timeCreated);

    @SqlQuery("SELECT * FROM instructors")
    List<Instructor> listInstructors();

    @SqlQuery("select first_name from instructors where id = 1")
    String testQuery();
}
