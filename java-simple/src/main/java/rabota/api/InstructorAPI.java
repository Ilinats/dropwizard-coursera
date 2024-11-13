package rabota.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rabota.core.Instructor;
import rabota.db.InstructorsDAO;

import java.util.List;
import java.util.stream.Collectors;

@Path("/instructor")
@Produces(MediaType.APPLICATION_JSON)
public class InstructorAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstructorAPI.class);
    InstructorsDAO instructorsDAO;

    public InstructorAPI(Jdbi jdbi) {
        instructorsDAO = jdbi.onDemand(InstructorsDAO.class);
        instructorsDAO.createInstructorsTable();
        if (instructorsDAO.listInstructors().isEmpty()) {
            instructorsDAO.insertInstructor("Test", "Testov", new java.util.Date());
            instructorsDAO.insertInstructor("Test2", "Testov2", new java.util.Date());
            instructorsDAO.insertInstructor("Test3", "Testov3", new java.util.Date());
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addInstructor(Instructor instructor) {
        try {
            checkInstructorInsert(instructor);
            instructorsDAO.insertInstructor(instructor.getFirstName(), instructor.getLastName(), new java.util.Date());
            return Response.status(Response.Status.CREATED)
                    .entity("Instructor added successfully")
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error adding instructor", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }

    @GET
    public Response listInstructors() {
        try {
            List<Instructor> instructors = instructorsDAO.listInstructors();
            List<String> formattedInstructors = instructors.stream()
                    .map(instructor -> String.format("Name: %s %s, Date: %s",
                            instructor.getFirstName(),
                            instructor.getLastName(),
                            instructor.getTimeCreated().toString()))
                    .collect(Collectors.toList());
            return Response.ok(formattedInstructors).build();
        } catch (Exception e) {
            LOGGER.error("Error listing instructors", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }

    void checkInstructorInsert(Instructor instructor) {
        if(instructor.getFirstName() == null || instructor.getFirstName().isEmpty()) {
            throw new WebApplicationException("Instructor first name is required", Response.Status.BAD_REQUEST);
        }

        if(instructor.getLastName() == null || instructor.getLastName().isEmpty()) {
            throw new WebApplicationException("Instructor last name is required", Response.Status.BAD_REQUEST);
        }

        if(instructor.getTimeCreated() == null) {
            throw new WebApplicationException("Instructor time created is required", Response.Status.BAD_REQUEST);
        }

        if(instructor.getFirstName().length() > 100) {
            throw new WebApplicationException("Instructor first name is too long", Response.Status.BAD_REQUEST);
        }

        if(instructor.getLastName().length() > 100) {
            throw new WebApplicationException("Instructor last name is too long", Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/test")
    public String getData() {
        String s = "Hello world";
        return s;
    }
}
