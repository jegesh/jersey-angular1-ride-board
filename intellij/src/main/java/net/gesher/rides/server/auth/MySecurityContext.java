package net.gesher.rides.server.auth;


import net.gesher.rides.server.entity.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class MySecurityContext implements SecurityContext {
    private User user;
    private String scheme;

    public MySecurityContext(User user, String scheme) {
        this.user = user;
        this.scheme = scheme;
    }

    public Principal getUserPrincipal() {return this.user;}

    public boolean isUserInRole(String s) {
        if (user.getRole() != null) {
            return user.getRole().contains(s);
        }
        return false;
    }

    public boolean isSecure() {return "https".equals(this.scheme);}

    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }
}

