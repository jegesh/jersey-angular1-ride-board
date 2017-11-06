package net.gesher.rides.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "passenger")
public class Passenger {
    private User user;
    private Ride ride;
    private long id;
    private String passengerName;
    private String passengerPhone;

    @OneToOne
    @JoinColumn(name="user_fk")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = "ride_fk")
    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    @Column
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "passenger_name")
    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    @Column(name = "passenger_phone")
    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

}
