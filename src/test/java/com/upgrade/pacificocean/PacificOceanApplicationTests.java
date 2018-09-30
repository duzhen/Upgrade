package com.upgrade.pacificocean;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.upgrade.pacificocean.dao.CampsiteMapper;
import com.upgrade.pacificocean.domain.Booking;
import com.upgrade.pacificocean.domain.Campsite;
import com.upgrade.pacificocean.domain.Schedule;
import com.upgrade.pacificocean.utils.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PacificOceanApplicationTests {

	@Autowired
    private CampsiteMapper campsiteMapper;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testCampsite() {
		Campsite c = campsiteMapper.getCampsite(1);
		Assert.assertNotNull(c);
	}

	@Test
	public void testSchedule() {
		int today = Utils.today();
		List<Schedule> schedule = campsiteMapper.getSchedule(1, today);
		Assert.assertNotNull(schedule);
	}
	
	@Test
	public void testBooking() {
		int today = Utils.today();
		campsiteMapper.createBooking(999, "Zhen", "zhen@du.com", 1, today, today);
		Booking b = campsiteMapper.getBooking(999);
		Assert.assertEquals("Zhen", b.getName());
	}
	
	@Test
	public void testUpdateBooking() {
		int today = Utils.today();
		campsiteMapper.updateBooking(999, "Du", "zhen@du.com", today, today, false);
		Booking b = campsiteMapper.getBooking(999);
		Assert.assertEquals("Du", b.getName());
	}
	
	@Test
	public void testDeleteBooking() {
		campsiteMapper.deleteBooking(999);
		Booking b = campsiteMapper.getBooking(999);
		Assert.assertNull(b);
	}
}
