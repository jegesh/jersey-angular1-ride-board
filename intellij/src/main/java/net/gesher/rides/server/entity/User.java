package net.gesher.rides.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.Principal;

@Entity
@Table(name = "user")
public class User implements Principal{
    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";
    private String id;
    private String name;
    private String phone;
    private String password;
    private String role;

    @Column
    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if(!role.equals(ROLE_ADMIN) && !role.equals(ROLE_USER))
            throw new IllegalArgumentException("Role must be either 'user' or 'admin'");
        this.role = role;
    }

}
