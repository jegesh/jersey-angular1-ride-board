package net.gesher.rides.server.dal;

import net.gesher.rides.server.entity.Passenger;
import net.gesher.rides.server.entity.Ride;

import java.util.ArrayList;
import java.util.List;

public class PassengerDal extends AbstractDal{

    public PassengerDal(DbSessionManager sessionManager){
        super(sessionManager);
    }

    public List<Passenger> getByRide(Ride ride){
        ExpressionBuilderHelper expressionBuilderHelper = new ExpressionBuilderHelper("rideId", Long.class, ride.getId(), ExpressionBuilderHelper.PREDICATE_EQUALS);
        List<ExpressionBuilderHelper> exbhList = new ArrayList<>();
        exbhList.add(expressionBuilderHelper);
        return getEntityList(Passenger.class, exbhList);
    }

}
