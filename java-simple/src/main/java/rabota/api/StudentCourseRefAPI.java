package rabota.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rabota.core.StudentCourseRef;
import rabota.db.StudentsCoursesRefDAO;

import java.util.Date;
import java.util.List;

@Path("/student-course-ref")
@Produces(MediaType.APPLICATION_JSON)
public class StudentCourseRefAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentCourseRefAPI.class);
    StudentsCoursesRefDAO studentCourseRefDAO;

    public StudentCourseRefAPI(Jdbi jdbi) {
        studentCourseRefDAO = jdbi.onDemand(StudentsCoursesRefDAO.class);
        studentCourseRefDAO.createStudentsCoursesRefTable();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addStudentCourseRef(StudentCourseRef studentCourseRef) {
        try {
            studentCourseRefDAO.insertStudentCourseRef(studentCourseRef.getCourseId(), studentCourseRef.getStudentPin(), null);
            return Response.status(Response.Status.CREATED)
                    .entity("Student enrolled in course successfully")
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error adding student-course ref", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }

    @GET
    public Response listStudentCourseRefs() {
        try {
            List<StudentCourseRef> studentCourseRefs = studentCourseRefDAO.listStudentsCoursesRef();
            List<String> formattedStudentCourseRefs = studentCourseRefs.stream()
                    .map(studentCourseRef -> String.format("Course ID: %d, Student PIN: %s, Time Finished: %s",
                            studentCourseRef.getCourseId(),
                            studentCourseRef.getStudentPin(),
                            studentCourseRef.getCompletionDate() == null ? "Not finished" : studentCourseRef.getCompletionDate().toString()))
                    .collect(java.util.stream.Collectors.toList());

            return Response.ok(formattedStudentCourseRefs)
                    .type("application/json")
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error listing student-course refs", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCompletionDate(StudentCourseRef studentCourseRef) {
        try {
            studentCourseRefDAO.updateCompletionDate(studentCourseRef.getCourseId(), studentCourseRef.getStudentPin(), new Date());
            return Response.ok("Completion date updated successfully").build();
        } catch (Exception e) {
            LOGGER.error("Error updating completion date", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }

}