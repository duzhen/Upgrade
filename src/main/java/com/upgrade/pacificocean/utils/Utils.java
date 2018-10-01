package com.upgrade.pacificocean.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
	
	public static int getNextDays(int days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, days);
		Date date = c.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String strDate = dateFormat.format(date);
		
		return Integer.valueOf(strDate);
	}
	
	public static int getDiffDay(int start, int end) {
		try {
			Date s = getDateByInt(start);
			Date e = getDateByInt(end);
			
			return (int)((e.getTime() - s.getTime())/(24*3600*1000));
		} catch (ParseException e) {
			return -1;
		}
	}
	
	public static boolean checkDate(int start_date, int end_date) {
		int tomorrow = getTomorrow();
		int nextMonth = getNextMonth();
		if(start_date > end_date || start_date < tomorrow || end_date > nextMonth) {
			return false;
		}
		int diff = getDiffDay(start_date, end_date);
		if(diff < 0 || diff > 2) {
			return false;
		}
		return true;
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
			slots.add(new Slot(getDayInt(i), checkAvailable(getDayInt(i), schedule)));
			Calendar c = Calendar.getInstance();
			c.setTime(i);
			c.add(Calendar.DATE, 1);
			i = c.getTime();
//			logger.info("loop:" + i.toString());
		}
		
		return new Slots(slots);
	}
	
	public static List<Schedule> getSchedule( int camp_id, int booking_id, int start, int end) {
		List<Schedule> schedules = new ArrayList<Schedule>();
		
		Date startDate;
		try {
			startDate = getDateByInt(start);
		} catch (ParseException e) {
			return null;
		}
//		logger.info("start:" + startDate.toString());
		Date endDate;
		try {
			endDate = getDateByInt(end);
		} catch (ParseException e1) {
			return null;
		}
//		logger.info("end:" + endDate.toString());
		
		for(Date i=startDate;!i.after(endDate);) {
			schedules.add(new Schedule(0, camp_id, getDayInt(i), booking_id));
			Calendar c = Calendar.getInstance();
			c.setTime(i);
			c.add(Calendar.DATE, 1);
			i = c.getTime();
//			logger.info("loop:" + i.toString());
		}
		
		return schedules;
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
	
	public static void randomSleep(int bound) {
		try {
			Thread.sleep(new Random().nextInt(bound));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
