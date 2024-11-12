package rabota.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "your-secret-key";
    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public static String generateToken(String username) {
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("username", username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);
    }

    public static boolean verifyToken(String token) {
        try {
            JWT.require(algorithm).withIssuer("auth0").build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}