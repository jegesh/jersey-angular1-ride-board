package net.gesher.rides.server.entity;

import javax.persistence.*;

@Entity
@Table(name = "passenger")
public class Passenger {
    private User user;
    private long rideId;
    private long id;
    private String passengerName;
    private String passengerPhone;

    @ManyToOne
    @JoinColumn(name="user_fk")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "ride_fk")
    public long getRideId() {
        return rideId;
    }

    public void setRideId(long rideId) {
        this.rideId = rideId;
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

    @Override
    public boolean equals(Object obj) {
        if(super.equals(obj)) return true;
        if(!(obj instanceof Passenger)) return false;
        Passenger p = (Passenger)obj;
        if(this.getUser().getId().equals(p.getUser().getId()) )
            return true;
        if(this.getPassengerPhone() != null && p.getPassengerPhone() != null &&
                this.getPassengerPhone().equals(p.getPassengerPhone()))
            return true;
        return false;
    }
}
