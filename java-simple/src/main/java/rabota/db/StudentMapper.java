package rabota.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import rabota.core.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements RowMapper<Student> {
    @Override
    public Student map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Student(
                rs.getString("pin"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("time_created")
        );
    }
}
