package net.gesher.rides.server.services.authenticated;

import net.gesher.rides.server.dal.RideDal;
import net.gesher.rides.server.entity.Ride;
import net.gesher.rides.server.services.HibernateInitializer;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;

@Path("ride")
public class RideRestService {

    @GET
    @Path("today")
    public List<Ride> getTodayRides(
            @QueryParam("hasFreeSpace") @DefaultValue("true") boolean hasSpace
    ) {
        return new RideDal(HibernateInitializer.getSessionManager()).getSingleDayRides(new Date(), hasSpace);
    }

    @GET
    public List<Ride> getRides() {
        return new RideDal(HibernateInitializer.getSessionManager()).getAll();
    }
}