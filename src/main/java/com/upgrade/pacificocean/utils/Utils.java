package com.upgrade.pacificocean.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upgrade.pacificocean.domain.Schedule;
import com.upgrade.pacificocean.domain.Slot;
import com.upgrade.pacificocean.domain.Slots;

public class Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static int today() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(date);
		
		return Integer.valueOf(strDate);
	}
	
	public static int getTomorrow() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 1);
		Date date = c.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(date);
		
		return Integer.valueOf(strDate);
	}
	
	public static int getNextMonth() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		Date date = c.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(date);
		
		return Integer.valueOf(strDate);
	}
	
	public static int getDayInt(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(date);
		
		return Integer.valueOf(strDate);
	}
	
	private static Date getDateByInt(int date) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		return dateFormat.parse(String.valueOf(date));
	}
	
	private static boolean checkAvailable(int day, List<Schedule> schedule) {
		for(Schedule s:schedule) {
			if(s.getBook_date() == day) {
				return false;
			}
		}
		
		return true;
	}
	
	public static Slots getSlot(List<Schedule> schedule, int start, int end) {
		List<Slot> slots = new ArrayList<Slot>();
		
		Date startDate;
		try {
			startDate = getDateByInt(start);
		} catch (ParseException e) {
			Calendar s = Calendar.getInstance();
			s.add(Calendar.DATE, 1);
			startDate = s.getTime();
		}
		logger.info("start:" + startDate.toString());
		Date endDate;
		try {
			endDate = getDateByInt(end);
		} catch (ParseException e1) {
			Calendar e = Calendar.getInstance();
			e.add(Calendar.MONTH, 1);
			endDate = e.getTime();
		}
		logger.info("end:" + endDate.toString());
		
		for(Date i=startDate;!i.after(endDate);) {
			if(checkAvailable(getDayInt(i), schedule)) {
				slots.add(new Slot(getDayInt(i), true));
			}
			Calendar c = Calendar.getInstance();
			c.setTime(i);
			c.add(Calendar.DATE, 1);
			i = c.getTime();
//			logger.info("loop:" + i.toString());
		}
		
		return new Slots(slots);
	}
		
	public static Slots getMonthSlot(List<Schedule> schedule) {
		List<Slot> slots = new ArrayList<Slot>();
		Calendar start = Calendar.getInstance();
		start.add(Calendar.DATE, 1);
		Calendar end = Calendar.getInstance();
		end.add(Calendar.MONTH, 1);
		
		Date startDate = start.getTime();
		logger.info("start:" + startDate.toString());
		Date endDate = end.getTime();
		logger.info("end:" + endDate.toString());
		
		for(Date i=startDate;!i.after(endDate);) {
			if(checkAvailable(getDayInt(i), schedule)) {
				slots.add(new Slot(getDayInt(i), true));
			}
			Calendar c = Calendar.getInstance();
			c.setTime(i);
			c.add(Calendar.DATE, 1);
			i = c.getTime();
//			logger.info("loop:" + i.toString());
		}
		
		return new Slots(slots);
	}
}
