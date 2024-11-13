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
            checkStudentInsert(student);
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

    void checkStudentInsert(Student student) {
        if(student.getPin() == null || student.getFirstName() == null || student.getLastName() == null) {
            throw new WebApplicationException("Student must have a pin, first name, and last name", Response.Status.BAD_REQUEST);
        }

        if(student.getPin().length() != 10) {
            throw new WebApplicationException("Student pin must be 10 characters long", Response.Status.BAD_REQUEST);
        }

        if(student.getFirstName().length() > 50 || student.getLastName().length() > 50) {
            throw new WebApplicationException("Student first and last names must be less than 50 characters long", Response.Status.BAD_REQUEST);
        }

        if(student.getFirstName().isEmpty() || student.getLastName().isEmpty()) {
            throw new WebApplicationException("Student first and last names must not be empty", Response.Status.BAD_REQUEST);
        }

        if(studentsDAO.listStudents().stream().anyMatch(s -> s.getPin().equals(student.getPin()))) {
            throw new WebApplicationException("Student with this pin already exists", Response.Status.BAD_REQUEST);
        }
    }
}
