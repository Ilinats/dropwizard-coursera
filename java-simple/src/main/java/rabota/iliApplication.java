package rabota;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;
import rabota.api.*;

public class iliApplication extends Application<iliConfiguration> {

    public static void main(final String[] args) throws Exception {
        new iliApplication().run(args);
    }

    @Override
    public String getName() {
        return "ili";
    }

    @Override
    public void initialize(final Bootstrap<iliConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final iliConfiguration configuration,
                    final Environment environment) {
//        HelloWorld helloWorld = new HelloWorld();
//        environment.jersey().register(helloWorld);

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");

        final InstructorAPI instructorAPI = new InstructorAPI(jdbi);
        environment.jersey().register(instructorAPI);

        final StudentAPI studentAPI = new StudentAPI(jdbi);
        environment.jersey().register(studentAPI);

        final CourseAPI courseAPI = new CourseAPI(jdbi);
        environment.jersey().register(courseAPI);

        final StudentCourseRefAPI studentCourseRefAPI = new StudentCourseRefAPI(jdbi);
        environment.jersey().register(studentCourseRefAPI);

        final ReportAPI reportAPI = new ReportAPI(jdbi);
        environment.jersey().register(reportAPI);
    }
}
