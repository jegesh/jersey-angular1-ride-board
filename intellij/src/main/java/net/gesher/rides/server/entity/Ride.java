package net.gesher.rides.server.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ride")
public class Ride {
    private long id;
    private User creatingUser;
    private String destination;
    private Date departureDate;
    private Integer departureHour;
    private Integer departureMinute;
    private int freeSpace;
    private String driverName;
    private String driverPhone;
    public static final String FIELD_DEPARTURE_DATE = "departure_date";

    @Column
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="creating_user")
    public User getCreatingUser() {
        return creatingUser;
    }

    public void setCreatingUser(User creatingUser) {
        this.creatingUser = creatingUser;
    }

    @Column
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Column(name = FIELD_DEPARTURE_DATE)
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @Column(name = "departure_hour")
    public Integer getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(Integer departureHour) {
        if(departureHour > 23 || departureHour < 0)
            throw new IllegalArgumentException("Hour can not be greater than 23 or less than zero");
        this.departureHour = departureHour;
    }

    @Column(name = "departure_minute")
    public Integer getDepartureMinute() {
        return departureMinute;
    }

    public void setDepartureMinute(Integer departureMinute) {
        if(departureMinute > 59 || departureMinute < 0)
            throw new IllegalArgumentException("Minute can not be greater than 59 or less than zero");
        this.departureMinute = departureMinute;
    }

    @Column(name = "free_space")
    public int getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(int freeSpace) {
        this.freeSpace = freeSpace;
    }

    @Column(name = "driver_name")
    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Column(name = "driver_phone")
    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

}
