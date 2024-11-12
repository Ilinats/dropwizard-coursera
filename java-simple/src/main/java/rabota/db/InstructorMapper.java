package rabota.db;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import rabota.core.Instructor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InstructorMapper implements RowMapper<Instructor> {
    @Override
    public Instructor map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Instructor(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getDate("time_created")
        );
    }
}