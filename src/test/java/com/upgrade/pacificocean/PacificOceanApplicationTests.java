package com.upgrade.pacificocean;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.upgrade.pacificocean.domain.Booking;
import com.upgrade.pacificocean.domain.Campsite;
import com.upgrade.pacificocean.domain.Slots;
import com.upgrade.pacificocean.service.CampsiteService;
import com.upgrade.pacificocean.utils.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PacificOceanApplicationTests {

	@Autowired
    private CampsiteService campsiteService;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void testCampsite() {
		Campsite c = campsiteService.getCampsite(1);
		Assert.assertNotNull(c);
	}

	@Test
	public void testSchedule() {
		int today = Utils.today();
		int end = Utils.getNextMonth();
		Slots slots = campsiteService.getSchedule(1, today, end);
		Assert.assertNotNull(slots);
	}
	
	@Test
	public void testBooking() {
		int today = Utils.today();
		campsiteService.createBooking(999, "Zhen", "zhen@du.com", 1, today, today);
		Booking b = campsiteService.getBooking(999);
		Assert.assertEquals("Zhen", b.getName());
	}
	
	@Test
	public void testUpdateBooking() {
		int today = Utils.today();
		campsiteService.updateBooking(999, "Du", "zhen@du.com", today, today, false);
		Booking b = campsiteService.getBooking(999);
		Assert.assertEquals("Du", b.getName());
	}
	
	@Test
	public void testDeleteBooking() {
		campsiteService.deleteBooking(999);
		Booking b = campsiteService.getBooking(999);
		Assert.assertNull(b);
	}
}
