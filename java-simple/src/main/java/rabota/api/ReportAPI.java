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
}