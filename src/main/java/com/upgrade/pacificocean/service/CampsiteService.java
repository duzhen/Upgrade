package com.upgrade.pacificocean.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upgrade.pacificocean.dao.CampsiteMapper;
import com.upgrade.pacificocean.domain.Booking;
import com.upgrade.pacificocean.domain.Campsite;
import com.upgrade.pacificocean.domain.Schedule;

@Service
@CacheConfig(cacheNames="campsiteCache")
@Transactional(readOnly = false)
public class CampsiteService {
    @Autowired
    private CampsiteMapper campsiteMapper;

    @Cacheable(key = "#p0")
    public Campsite getCampsite(int id) {
    	return campsiteMapper.getCampsite(id);
    }
    
    @Cacheable(key = "#p0"+"#p1")
    public List<Schedule> getSchedule(int id, int start) {
    	return campsiteMapper.getSchedule(id, start);
    }
    
    @Cacheable(key = "#p0")
    public Booking getBooking(int id) {
    	return campsiteMapper.getBooking(id);
    }
    
    @CachePut(key = "#p0")
    public Booking createBooking(int id, String name, String email, int camp_id, int start_date, int end_date) {
    	campsiteMapper.createBooking(id, name, email, camp_id, start_date, end_date);
    	return campsiteMapper.getBooking(id);
    }
    
    @CachePut(key = "#p0")
    public Booking updateBooking(int id, String name, String email, int start_date, int end_date, boolean cancelled) {
    	campsiteMapper.updateBooking(id, name, email, start_date, end_date, cancelled);
    	return campsiteMapper.getBooking(id);
    }
    
    @CacheEvict(key="#p0")
    public void deleteBooking(int id) {
    	campsiteMapper.deleteBooking(id);
    }

}