package net.gesher.rides.server.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import net.gesher.rides.server.entity.User;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


@Provider
public class JwtAuthProvider implements ContainerRequestFilter {
    public static String SECRET = "";
    public static final String ISSUER = "myserver";

    static {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties"));
            SECRET = properties.getProperty("jwt.secret");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param token
     * @return
     */
    private DecodedJWT verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            return verifier.verify(token);
        } catch (UnsupportedEncodingException exception){
            throw new WebApplicationException(exception);
        } catch (JWTVerificationException exception){
            throw new WebApplicationException(exception);
        }
    }

    /**
     * {@inheritDoc}
     */
    public ContainerRequest filter(ContainerRequest request) {
            MultivaluedMap<String, String> cookieMap = request.getCookieNameValueMap();
            String token = cookieMap.getFirst("jwt");

            DecodedJWT jwt = verifyToken(token);
            if(jwt.getIssuedAt().after(jwt.getExpiresAt())) // compare date
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);

            // passed authorization
            User u = new User();
            u.setRole(jwt.getClaim("rol").asString());
            u.setName(jwt.getClaim("nam").asString());
            u.setId(jwt.getClaim("aud").asString());
            u.setPhone(jwt.getClaim("tel").asString());
            request.setSecurityContext(new MySecurityContext(u, request.getAuthenticationScheme()));
            return request;
        }
}
