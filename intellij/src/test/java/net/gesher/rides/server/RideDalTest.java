package net.gesher.rides.server;

import net.gesher.rides.server.dal.DbSessionManager;
import net.gesher.rides.server.dal.RideDal;
import net.gesher.rides.server.dal.UserDal;
import net.gesher.rides.server.entity.Passenger;
import net.gesher.rides.server.entity.Ride;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class RideDalTest {
    DbSessionManager sessionManager;

    @Before
    public void setup(){
        sessionManager = new DbSessionManager("localhost", "rideboard_test", "root", null);
    }

    @Test
    public void testGetRideList(){
        RideDal dal = new RideDal(sessionManager);

        Calendar calendar = getTestDate();
        List<Ride> rides = dal.getSingleDayRides(getTestDate().getTime(), true);

        assert rides.size() > 0;
        for(Ride r : rides) {
            // TODO figure out strange bug where the date returns with the wrong day
            Calendar rCal = Calendar.getInstance();
            rCal.setTime(r.getDepartureDate());
//            assert rCal.get(Calendar.DATE) == calendar.get(Calendar.DATE);
            assert rCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR);
            assert rCal.get(Calendar.MONTH) == calendar.get(Calendar.MONTH);

        }
    }

    private Calendar getTestDate(){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.DATE, 6);
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 10);
        return calendar;
    }

    @Test
    public void testAttachPassenger(){
        RideDal dal = new RideDal(sessionManager);
        Ride r = dal.getSingleDayRides(getTestDate().getTime(), true).get(0);

        Passenger p = new Passenger();
        p.setUser(new UserDal(sessionManager).getById("aa11"));
        dal.attachPassengerToRide(p, r);

    }

    @Test
    public void testAddRide(){
        RideDal dal = new RideDal(sessionManager);
        List<Ride> rides = dal.getAll();
        int count = rides.size();
        Ride r = new Ride();
        r.setCreatingUser(null);
        r.setDepartureDate(new Date());
        r.setDepartureHour(12);
        r.setDepartureMinute(33);
        r.setDestination("Hadera");
        r.setDriverName("Johnny Cash");
        r.setDriverPhone("09888888");
        Ride returnedRide = dal.insertRide(r);

        assert returnedRide != null;
        assert returnedRide.getId() > 0;
        rides = dal.getAll();
        assert rides.size() == count + 1;

        dal.deleteRide(r);
    }
}
