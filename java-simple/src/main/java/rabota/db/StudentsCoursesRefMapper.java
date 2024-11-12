package rabota.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import rabota.core.StudentCourseRef;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentsCoursesRefMapper implements RowMapper<StudentCourseRef> {
    @Override
    public StudentCourseRef map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new StudentCourseRef(
                rs.getString("student_pin"),
                rs.getInt("course_id"),
                rs.getDate("time_finished")
        );
    }
}
