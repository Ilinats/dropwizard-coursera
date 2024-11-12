package rabota.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jdbi.v3.core.Jdbi;
import rabota.db.UsersDAO;
import rabota.core.User;
import rabota.util.JwtUtil;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthAPI {
    private final UsersDAO usersDAO;

    public AuthAPI(Jdbi jdbi) {
        this.usersDAO = jdbi.onDemand(UsersDAO.class);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        User storedUser = usersDAO.getUserByUsername(user.getUsername());
        if (storedUser != null && storedUser.getPassword().equals(user.getPassword())) {
            String token = JwtUtil.generateToken(storedUser.getUsername());
            return Response.ok().entity("{\"token\":\"" + token + "\"}").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }
    }
}