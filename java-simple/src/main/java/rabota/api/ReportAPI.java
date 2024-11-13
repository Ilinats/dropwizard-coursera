package rabota.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;
import rabota.core.StudentReport;
import rabota.db.ReportsDAO;

import java.util.List;

@Path("/report")
@Produces(MediaType.APPLICATION_JSON)
public class ReportAPI {
    private final ReportsDAO reportsDAO;

    public ReportAPI(Jdbi jdbi) {
        this.reportsDAO = new ReportsDAO(jdbi);
    }

    @GET
    public Response generateReport(@QueryParam("pins") List<String> pins,
                                   @QueryParam("minCredit") int minCredit,
                                   @QueryParam("startDate") String startDate,
                                   @QueryParam("endDate") String endDate) {
        try {
            List<StudentReport> reports = reportsDAO.fetchStudentReports(pins, minCredit, startDate, endDate);
            return Response.ok(reports).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error generating report").build();
        }
    }

    void checkReportGeneration(List<String> pins, int minCredit, String startDate, String endDate) {
        if (pins == null || pins.isEmpty()) {
            throw new IllegalArgumentException("At least one student pin must be provided");
        }

        if (minCredit < 0) {
            throw new IllegalArgumentException("Minimum credit must be a non-negative number");
        }

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start and end dates must be provided");
        }

        if(startDate.compareTo(endDate) > 0) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }
}