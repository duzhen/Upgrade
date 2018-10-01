package com.upgrade.pacificocean.rest.resource;

import java.net.URISyntaxException;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.upgrade.pacificocean.domain.Booking;
import com.upgrade.pacificocean.domain.Campsite;
import com.upgrade.pacificocean.domain.RestResult;
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
    public Response getCampsiteSchedule(@PathParam("id") Integer id, @QueryParam("start") Integer start, @QueryParam("end") Integer end) {
    	Campsite camp = campsiteService.getCampsite(id);
    	if(camp == null) {
    		return Response.ok().entity(new RestResult(404, "no campsite id", null)).build();
    	}
    	if(start == null) {
    		start = Utils.getTomorrow();
    	}
    	if(end == null) {
    		end = Utils.getNextMonth();
    	}
    	Slots slots = campsiteService.getSchedule(id, start, end);
    	return Response.ok().entity(new RestResult(200, "suceess", slots)).build();
    }

    @GET
    @Path("booking/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookingById(@PathParam("id") Integer id) {
    	Booking booking = campsiteService.getBooking(id);
    	if(booking == null) {
    		return Response.ok().entity(new RestResult(404, "no booking id", null)).build();
    	}
    	return Response.ok().entity(new RestResult(200, "suceess", booking)).build();
    }

    @POST
    @Path("booking")
    @Produces(MediaType.APPLICATION_JSON)
    public Response booking(@FormParam("name") String name, @FormParam("email") String email,
    		@FormParam("start_date") int start_date, @FormParam("end_date") int end_date) {
    	logger.info("name:"+name + "email:"+email + "start_date:" + start_date + "end_date:" + end_date);
    	if(!Utils.checkDate(start_date, end_date)) {
    		return Response.ok().entity(new RestResult(400, "invalid booking date", null)).build();
    	}
    	int id = idService.getNextID();
    	try {
    		Booking booking = campsiteService.createBooking(id, name, email, 1, start_date, end_date);
    		if(booking == null) {
    			return Response.ok().entity(new RestResult(400, "booking failed", null)).build();
        	}
    		return Response.ok().entity(new RestResult(200, "booking suceess", booking)).build();
    	} catch(DataAccessException e) {
    		logger.error(e.getCause().getLocalizedMessage());
    		return Response.ok().entity(new RestResult(400, e.getCause().getLocalizedMessage(), null)).build();
		}
    }

    @PUT
    @Path("booking/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBooking(@PathParam("id") Integer id, @FormParam("name") String name, @FormParam("email") String email,
    		@FormParam("start_date") int start_date, @FormParam("end_date") int end_date) throws URISyntaxException {
    	if(!Utils.checkDate(start_date, end_date)) {
    		return Response.ok().entity(new RestResult(400, "invalid booking date", null)).build();
    	}
    	try {
	    	Booking booking = campsiteService.updateBooking(id, name, email, start_date, end_date);
	    	if(booking == null) {
	    		return Response.ok().entity(new RestResult(400, "no booking id", null)).build();
	    	}
	    	return Response.ok().entity(new RestResult(200, "update suceess", booking)).build();
    	} catch(DataAccessException e) {
    		logger.error(e.getCause().getLocalizedMessage());
    		return Response.ok().entity(new RestResult(400, e.getCause().getLocalizedMessage(), null)).build();
		}
    }
    
    @DELETE
    @Path("booking/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBooking(@PathParam("id") Integer id) {
    	campsiteService.deleteBooking(id);
    	campsiteService.refreshScheduleCache();
    	return Response.ok().entity(new RestResult(200, "suceess", null)).build();
    }
}