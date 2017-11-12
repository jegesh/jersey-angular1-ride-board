package net.gesher.rides.server.services.authenticated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.gesher.rides.server.dal.PassengerDal;
import net.gesher.rides.server.dal.RideDal;
import net.gesher.rides.server.entity.Passenger;
import net.gesher.rides.server.entity.Ride;
import net.gesher.rides.server.services.HibernateInitializer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Path("ride")
public class RideRestService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("today")
    public List<Ride> getTodayRides(
            @QueryParam("hasFreeSpace") @DefaultValue("true") boolean hasSpace
    ) {
        return new RideDal(HibernateInitializer.getSessionManager()).getSingleDayRides(new Date(), hasSpace);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ride> getRides() {
        return new RideDal(HibernateInitializer.getSessionManager()).getAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{rideId}")
    public Response getRide(
            @PathParam("rideId") long rideId
    ) throws JsonProcessingException {
        Ride r = new RideDal(HibernateInitializer.getSessionManager()).getById(rideId);
        List<Passenger> passengers = new PassengerDal(HibernateInitializer.getSessionManager()).getByRide(r);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("ride", r);
        resultMap.put("passengers", passengers);
        ObjectMapper mapper = new ObjectMapper();
        return Response.ok(mapper.writeValueAsString(resultMap)).build();
    }
}