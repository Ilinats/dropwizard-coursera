package rabota.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import rabota.iliApplication;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorld {
    @GET
    @Path("/greet")
    public String hello() {
        iliApplication app = new iliApplication();

        return "Hello " + app.getName();
    }
}
