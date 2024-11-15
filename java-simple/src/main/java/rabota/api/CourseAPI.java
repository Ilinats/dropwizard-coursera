package rabota.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rabota.core.Course;
import rabota.db.CoursesDAO;
import rabota.db.InstructorsDAO;

import java.util.List;
import java.util.stream.Collectors;


@Path("/course")
public class CourseAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseAPI.class.getName());
    CoursesDAO courseDAO;
    InstructorsDAO instructorDAO;

    public CourseAPI(Jdbi jdbi) {
        courseDAO = jdbi.onDemand(CoursesDAO.class);
        instructorDAO = jdbi.onDemand(InstructorsDAO.class);
        courseDAO.createCoursesTable();
        if (courseDAO.listCourses().isEmpty()) {
            courseDAO.insertCourse("Java", 1, new java.util.Date(), 3, 30);
            courseDAO.insertCourse("Python", 2, new java.util.Date(), 3, 30);
            courseDAO.insertCourse("C++", 3, new java.util.Date(), 3, 30);
        }
    }

    @POST
    @Path("/add")
    public Response addCourse(Course course) {
        try {
            checkCourseInsert(course);
            courseDAO.insertCourse(course.getName(), course.getInstructorId(), new java.util.Date(), course.getCredit(), course.getTotalTime());
            return Response.status(Response.Status.CREATED)
                    .entity("Course added successfully")
                    .build();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error adding course", e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error adding course", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }

    @GET
    public Response getCourses() {
        try {
            List<Course> courses = courseDAO.listCourses();
            System.out.println(courses);
            List<String> formattedCourses = courses.stream()
                    .map(course -> String.format("Name: %s, Instructor ID: %d, Date: %s, Credit: %d, Total Time: %d",
                            course.getName(),
                            course.getInstructorId(),
                            course.getTimeCreated().toString(),
                            course.getCredit(),
                            course.getTotalTime()))
                    .collect(Collectors.toList());
            System.out.println(formattedCourses);
            return Response.ok(formattedCourses)
                    .type("application/json")
                    .build();
        } catch (Exception e) {
            LOGGER.error("Error retrieving courses", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error processing your request.")
                    .build();
        }
    }

    void checkCourseInsert(Course course) {
        if (course.getName() == null || course.getName().isEmpty() || course.getName().isBlank()) {
            throw new IllegalArgumentException("Course name cannot be empty");
        }

       if(instructorDAO.getInstructorById(course.getInstructorId()) == null) {
           throw new IllegalArgumentException("Instructor does not exist");
       }

        if (course.getCredit() <= 0) {
            throw new IllegalArgumentException("Credit must be greater than 0");
        }

        if (course.getTotalTime() <= 0) {
            throw new IllegalArgumentException("Total time must be greater than 0");
        }
    }
}
