package com.upgrade.pacificocean.rest.resource;

import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upgrade.pacificocean.domain.Booking;
import com.upgrade.pacificocean.domain.Campsite;
import com.upgrade.pacificocean.domain.Schedule;
import com.upgrade.pacificocean.domain.Slots;
import com.upgrade.pacificocean.service.CampsiteService;
import com.upgrade.pacificocean.service.RedisIDService;
import com.upgrade.pacificocean.utils.Utils;

@Component
@Path("/rest")
public class CampsiteResource {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private CampsiteService campsiteService;
	@Autowired
	private RedisIDService idService;
	
    @GET
    @Path("campsite/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCampsiteById(@PathParam("id") Integer id) {
    	int start = Utils.today();
    	logger.info("id:"+id + " today:"+start);
    	Campsite camp = campsiteService.getCampsite(id);
    	if(camp == null) {
    		return Response.status(404).build();
    	}
    	List<Schedule> schedule = campsiteService.getSchedule(id, start);
    	Slots slots = Utils.getMonthSlot(schedule);
//    	logger.info("slots size:"+slots.size());
//    	for (Slot s:slots) {
//    		logger.info(s.toString());
//    	}
    	return Response.status(200).entity(slots).build();
    }

    @GET
    @Path("booking/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingById(@PathParam("id") Integer id) {
    	Booking booking = campsiteService.getBooking(id);
    	if(booking == null) {
    		return Response.status(404).build();
    	}
    	return Response.status(200).entity(booking).build();
    }

    @POST
    @Path("booking")
    @Produces(MediaType.APPLICATION_JSON)
    public Response booking(@FormParam("name") String name, @FormParam("email") String email,
    		@FormParam("start_date") int start_date, @FormParam("end_date") int end_date) throws URISyntaxException {
    	logger.info("name:"+name + "email:"+email + "start_date:" + start_date + "end_date:" + end_date);
    	int id = idService.getNextID();
    	Booking booking = campsiteService.createBooking(id, name, email, 1, start_date, end_date);
    	if(booking == null) {
    		return Response.status(404).build();
    	}
    	return Response.status(200).entity(booking).build();
//    	return Response.created(new URI("/booking/"+id)).build();
    }

    @PUT
    @Path("booking/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("id") Integer id, @FormParam("name") String name, @FormParam("email") String email,
    		@FormParam("start_date") int start_date, @FormParam("end_date") int end_date, 
    		@FormParam("cancelled") boolean cancelled) throws URISyntaxException {
    	Booking booking = campsiteService.updateBooking(id, name, email, start_date, end_date, cancelled);
    	if(booking == null) {
    		return Response.status(404).build();
    	}
    	return Response.status(200).entity(booking).build();
    }
    
    @DELETE
    @Path("booking/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("id") Integer id) {
    	campsiteService.deleteBooking(id);
    	return Response.ok().build();
    }
}