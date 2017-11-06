package net.gesher.rides.server;

import net.gesher.rides.server.dal.DbSessionManager;
import net.gesher.rides.server.dal.RideDal;
import net.gesher.rides.server.entity.Ride;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
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
        List<Ride> rides = dal.getTodayRides();

        assert rides.size() > 0;
        for(Ride r : rides)
            assert r.getDepartureDate().compareTo(new Date()) == 0;
    }
}
