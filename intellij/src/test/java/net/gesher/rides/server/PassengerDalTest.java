package net.gesher.rides.server;

import net.gesher.rides.server.dal.DbSessionManager;
import net.gesher.rides.server.dal.PassengerDal;
import net.gesher.rides.server.dal.RideDal;
import net.gesher.rides.server.entity.Passenger;
import net.gesher.rides.server.entity.Ride;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PassengerDalTest {
    DbSessionManager sessionManager;

    @Before
    public void setup(){
        sessionManager = new DbSessionManager("localhost", "rideboard_test", "root", null);
    }

    @Test
    public void testGetPassengersFromRide(){
        PassengerDal dal = new PassengerDal(sessionManager);
        RideDal rideDal = new RideDal(sessionManager);

        Ride r = rideDal.getById(1);
        List<Passenger> passengers = dal.getByRide(r);

        assert passengers.size() > 0;
    }
}
