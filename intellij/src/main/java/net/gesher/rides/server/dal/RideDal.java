package net.gesher.rides.server.dal;

import net.gesher.rides.server.entity.Passenger;
import net.gesher.rides.server.entity.Ride;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.*;

import static net.gesher.rides.server.dal.ExpressionBuilderHelper.*;

public class RideDal extends AbstractDal{

    public RideDal(DbSessionManager sessionManager){
        super(sessionManager);
    }

    /**
     *
     * @param r
     * @return null if insert failed, otherwise id of created record
     */
    public Ride insertRide(Ride r){
        Session session = sessionManager.getSessionFactoryInstance().openSession();
        Serializable result = session.save(r);
        session.close();
        if((Long)result < 1) throw new PersistentObjectException("Failed to insert ride");
        return r;
    }

    public List<Ride> getAll(){
        return getEntityList(Ride.class,null);
    }

    /**
     * primarily for use by tests
     * @param rideId
     * @return
     */
    public Ride getById(long rideId){ return sessionManager.getSessionFactoryInstance().openSession().get(Ride.class, rideId); }

    /**
     *
     * @param passenger person to add to ride - must contain user or have name and phone already set
     * @param r Ride to attach to
     */
    public void attachPassengerToRide(Passenger passenger, Ride r){
        passenger.setRideId(r.getId());
        Session session = sessionManager.getSessionFactoryInstance().openSession();
        session.beginTransaction();

        // ride may have been filled in the meanwhile, since data was retrieved by the client
        session.refresh(r);
        if(r.getFreeSpace() == 0) throw new IllegalStateException("Ride is already full");
        if(passenger.getUser().getId() != null && r.getCreatingUser().getId().equals(passenger.getUser().getId())
                || passenger.getPassengerPhone().equals(r.getCreatingUser().getPhone())){
            throw new IllegalArgumentException("Creating user cannot be passenger");
        }
        List<Passenger> passengers = new PassengerDal(sessionManager).getByRide(r);
        for(Passenger p: passengers)
            if(p.equals(passenger))
                throw new IllegalArgumentException("Passenger is already listed as riding");

        session.save(passenger);
        r.setFreeSpace(r.getFreeSpace() - 1);
        session.update(r);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteRide(Ride r) {
        Session session = sessionManager.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.delete(r);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * retrieves all rides scheduled to leave on a particular date
     * @return
     */
    public List<Ride> getSingleDayRides(Date target, boolean hasFreeSpace){

        List<ExpressionBuilderHelper> constraints = new ArrayList<>();
        ExpressionBuilderHelper exBuilder = new ExpressionBuilderHelper("departureDate", Date.class, DateUtils.getEndOfPreviousDay(target), PREDICATE_BETWEEN);
        exBuilder.setAdditionalValue(DateUtils.getStartOfNextDay(target));
        if(hasFreeSpace){
            constraints.add(new ExpressionBuilderHelper("freeSpace", Integer.class, 0, PREDICATE_GREATER_THAN));
        }
        constraints.add(exBuilder);
        return getEntityList(Ride.class, constraints);

    }

}
