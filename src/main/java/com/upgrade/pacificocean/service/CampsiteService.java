package com.upgrade.pacificocean.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.upgrade.pacificocean.dao.CampsiteMapper;
import com.upgrade.pacificocean.domain.Booking;
import com.upgrade.pacificocean.domain.Campsite;
import com.upgrade.pacificocean.domain.Schedule;
import com.upgrade.pacificocean.domain.Slots;
import com.upgrade.pacificocean.utils.Utils;

@Service
@CacheConfig(cacheNames="campsiteCache")
@Transactional(readOnly = true)
public class CampsiteService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private CampsiteMapper campsiteMapper;
    
    @Cacheable(value="campiste", key = "#p0")
    public Campsite getCampsite(int id) {
    	return campsiteMapper.getCampsite(id);
    }
    
    @Cacheable(value="schedule")
    public Slots getSchedule(int id, int start, int end) {
    	if(start == 0) {
    		start = Utils.getTomorrow();
    	}
    	if(end == 0) {
    		end = Utils.getNextMonth();
    	}
    	List<Schedule> schedule =  campsiteMapper.getSchedule(id, start, end);
    	Slots slots = Utils.getSlot(schedule, start, end);
//    	logger.info("slots size:"+slots.size());
//    	for (Slot s:slots) {
//    		logger.info(s.toString());
//    	}
    	return slots;
    }
    
    @Cacheable(value="booking", key = "#p0")
    public Booking getBooking(int id) {
    	return campsiteMapper.getBooking(id);
    }
    
    @CachePut(value="booking", key = "#p0")
    @CacheEvict(value="schedule", allEntries=true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Booking createBooking(int id, String name, String email, int camp_id, int start_date, int end_date) {
    	campsiteMapper.createBooking(id, name, email, camp_id, start_date, end_date);
    	Booking booking = campsiteMapper.getBooking(id);
    	List<Schedule> schedules = Utils.getSchedule(booking.getCamp_id(), booking.getId(), start_date, end_date);
    	campsiteMapper.insertSchedule(schedules);
    	
    	return booking;
    }
    
    @CachePut(value="booking", key = "#p0")
    @CacheEvict(value="schedule", allEntries=true)
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public Booking updateBooking(int id, String name, String email, int start_date, int end_date) {    	
    	Booking booking = campsiteMapper.getBooking(id);
    	if(booking == null) {
    		return booking;
    	}
    	campsiteMapper.deleteScheduleByBookingId(booking.getId());
    	campsiteMapper.updateBooking(id, name, email, start_date, end_date);
    	List<Schedule> schedules = Utils.getSchedule(booking.getCamp_id(), booking.getId(), start_date, end_date);
    	campsiteMapper.insertSchedule(schedules);
    	
    	return campsiteMapper.getBooking(id);
    }
    
    @CacheEvict(value="booking", key = "#p0")
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public void deleteBooking(int id) {
    	Booking booking = campsiteMapper.getBooking(id);
    	//already did by foreignkey cascade
//    	campsiteMapper.deleteScheduleByBookingId(booking.getId());
    	campsiteMapper.deleteBooking(id);
    }

    @CacheEvict(value="schedule", allEntries=true)
    public void refreshScheduleCache() {
    	
    }
    
    @Async
    @Transactional(readOnly = false)
	public void asyncBooking(int id, String name, int start, int end) {
		Utils.randomSleep(500);
		logger.info("start perform booking " + id);
		createBooking(id, "Zhen", "zhen@du.com", 1, start, end);
	}
}