package net.gesher.rides.server.services.unauthorized;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.sun.jersey.api.view.Viewable;
import net.gesher.rides.server.dal.DateUtils;
import net.gesher.rides.server.dal.UserDal;
import net.gesher.rides.server.entity.User;
import net.gesher.rides.server.services.ApplicationProvider;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static net.gesher.rides.server.auth.JwtAuthProvider.*;

@Path("user")
public class UserRestService {

    @POST
    @Path("login")
    public Response login(@HeaderParam("Authorization") String authHeader){
        String userBundle = authHeader.split(" ")[1];

        String[] credentials = new String(Base64.decodeBase64(userBundle)).split(":");
        if(credentials.length != 2)
            throw new WebApplicationException(403);
        String userId = credentials[0];
        String password = credentials[1];
        User verifiedUser = new UserDal(ApplicationProvider.getSessionManager()).verify(userId, DigestUtils.sha256Hex(password));
        if(verifiedUser == null) throw new WebApplicationException(403);
        return Response.ok("Verified")
                .cookie(new NewCookie("jwt", createToken(verifiedUser.getName(), verifiedUser.getPhone(), verifiedUser.getRole())))
                .build();
    }

    private String createToken(String name, String phone, String role) {
        try {
            Algorithm algorithm = null;
            try {
                algorithm = Algorithm.HMAC256(SECRET);
            } catch (UnsupportedEncodingException e) {
                throw new WebApplicationException(e);
            }
            return JWT.create()
                    .withClaim("iat", new Date())
                    .withClaim("aud", name)
                    .withClaim("tel", phone)
                    .withClaim("rol", role)
                    .withClaim("exp", DateUtils.getXDaysAhead(new Date(), 90))
                    .withIssuer(ISSUER)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new WebApplicationException(exception);
        }
    }

    /**
     * TODO replace with google social signin
     * @param password
     * @param name
     * @param phone
     * @return
     */
    @POST
    @Path("register")
    public Response register(
            @QueryParam("password") String password,
            @QueryParam("phone") String phone,
            @QueryParam("name") String name
    ){
        User user = new User();
        user.setName(name);
        user.setPassword(DigestUtils.sha256Hex(password));
        user.setPhone(phone);
        user.setRole(User.ROLE_USER);
        user.setId(Base64.encodeBase64String((phone+name).getBytes()));
        UserDal dal = new UserDal(ApplicationProvider.getSessionManager());
        dal.registerUser(user);
        return Response.ok(user.getId())
                .cookie(new NewCookie("jwt", createToken(user.getName(), user.getPhone(), user.getRole())))
                .build();
    }
}
