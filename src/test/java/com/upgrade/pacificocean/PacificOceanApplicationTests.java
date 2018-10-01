package com.upgrade.pacificocean;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private CampsiteService campsiteService;
	
	@Test
	public void testCampsite() {
		Campsite c = campsiteService.getCampsite(1);
		Assert.assertNotNull(c);
	}

	@Test
	public void testSchedule() {
		int tomorrow = Utils.getTomorrow();
		int end = Utils.getNextMonth();
		Slots slots = campsiteService.getSchedule(1, tomorrow, end);
		Assert.assertNotNull(slots);
	}
	
	@Test
	public void testBooking() {
		int nextWeek = Utils.getNextDays(7);
		campsiteService.createBooking(999, "Zhen", "zhen@du.com", 1, nextWeek, nextWeek);
		Booking b = campsiteService.getBooking(999);
		Assert.assertEquals("Zhen", b.getName());
	}
	
	@Test
	public void testUpdateBooking() {
		int nextWeek = Utils.getNextDays(7);
		campsiteService.updateBooking(999, "Du", "zhen@du.com", nextWeek, nextWeek);
		Booking b = campsiteService.getBooking(999);
		Assert.assertEquals("Du", b.getName());
	}
	
	@Test
	public void testConcurrentBooking() {
		int tomorrow = Utils.getTomorrow();
		int next2Day = Utils.getNextDays(2);
		logger.info("start call asyncBooking 1000");
		campsiteService.asyncBooking(1000, "Zhen1000", tomorrow, next2Day);
		logger.info("start call asyncBooking 1001");
		campsiteService.asyncBooking(1001, "Zhen1001", tomorrow, tomorrow);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Booking a = campsiteService.getBooking(1000);
		Booking b = campsiteService.getBooking(1001);
		if(a!=null) {
			campsiteService.deleteBooking(1000);
			campsiteService.deleteBooking(1001);
		}
		if(b!=null) {
			campsiteService.deleteBooking(1001);
			campsiteService.deleteBooking(1000);
		}
		Assert.assertTrue(a==null && b!=null || a!=null && b==null);
	}
	
	@Test
	public void testDeleteBooking() {
		campsiteService.deleteBooking(999);
		Booking b = campsiteService.getBooking(999);
		Assert.assertNull(b);
	}
}
