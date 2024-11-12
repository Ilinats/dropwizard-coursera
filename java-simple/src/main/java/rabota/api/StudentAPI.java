package rabota.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rabota.core.Student;
import rabota.db.StudentsDAO;

import java.util.List;
import java.util.stream.Collectors;

@Path("/student")
@Produces(MediaType.APPLICATION_JSON)
public class StudentAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentAPI.class);
    StudentsDAO studentsDAO;

    public StudentAPI(Jdbi jdbi) {
        studentsDAO = jdbi.onDemand(StudentsDAO.class);
        studentsDAO.createStudentsTable();
//        if (studentsDAO.listStudents().isEmpty()) {
//            studentsDAO.insertStudent("123456789", "Test", "Testov", new java.util.Date());
//            studentsDAO.insertStudent("987654321", "Test2", "Testov2", new java.util.Date());
//            studentsDAO.insertStudent("123123123", "Test3", "Testov3", new java.util.Date());
//        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addStudent(Student student) {
        try {
            studentsDAO.insertStudent(student.getPin(), student.getFirstName(), student.getLastName(), new java.util.Date());
            return Response.status(Response.Status.CREATED)
                    .entity("Student added successfully")
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error adding student", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }

    @GET
    public Response listStudents() {
        try {
            List<Student> students = studentsDAO.listStudents();
            List<String> formattedStudents = students.stream()
                    .map(student -> String.format("Name: %s %s, Date: %s",
                            student.getFirstName(),
                            student.getLastName(),
                            student.getTimeCreated().toString()))
                    .collect(Collectors.toList());
            System.out.println(formattedStudents);
            return Response.ok(formattedStudents).build();
        } catch (Exception e) {
            LOGGER.error("Error listing students", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }
}
