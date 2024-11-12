package rabota.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import rabota.core.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {
    @Override
    public Course map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Course(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("instructor_id"),
                rs.getDate("time_created"),
                rs.getInt("credit"),
                rs.getInt("total_time")
        );
    }
}