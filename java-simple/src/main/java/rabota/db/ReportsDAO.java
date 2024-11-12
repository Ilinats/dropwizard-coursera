package rabota.db;

import org.jdbi.v3.core.Jdbi;
import rabota.core.StudentReport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportsDAO {
    private final Connection dbConnection;

    public ReportsDAO(Jdbi jdbi) {
        this.dbConnection = jdbi.open().getConnection();
    }

    public List<StudentReport> fetchStudentReports(List<String> pins, int minCredit, String startDate, String endDate) {
        List<StudentReport> reportList = new ArrayList<>();

        String pinFilter = (pins != null && !pins.isEmpty()) ? " AND s.pin IN (" + pins.stream().map(pin -> "'" + pin + "'").collect(Collectors.joining(",")) + ")" : "";
        String query = "SELECT s.first_name || ' ' || s.last_name AS student_name, SUM(c.credit) AS total_credit, "
                + "STRING_AGG(c.name || ' (' || c.total_time || ' hours, ' || c.credit || ' credits, Instructor: ' || i.first_name || ' ' || i.last_name || ')', '; ') AS courses "
                + "FROM students s "
                + "JOIN students_courses_ref e ON s.pin = e.student_pin "
                + "JOIN courses c ON e.course_id = c.id "
                + "JOIN instructors i ON c.instructor_id = i.id "
                + "WHERE e.time_finished IS NOT NULL "
                + "AND e.time_finished BETWEEN CAST(? AS DATE) AND CAST(? AS DATE) "
                + pinFilter
                + "GROUP BY s.first_name, s.last_name "
                + "HAVING SUM(c.credit) >= ?";

        try (PreparedStatement stmt = dbConnection.prepareStatement(query)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            stmt.setInt(3, minCredit);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                int totalCredit = rs.getInt("total_credit");
                String courses = rs.getString("courses");

                reportList.add(new StudentReport(studentName, totalCredit, courses));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportList;
    }
}