package com.upgrade.pacificocean.domain;

public class Schedule {
	private int id;
	private int camp_id;
	private int book_date;
	private int booking_id;

	public Schedule(int id, int camp_id, int book_date, int booking_id) {
		super();
		this.id = id;
		this.camp_id = camp_id;
		this.book_date = book_date;
		this.booking_id = booking_id;
	}

	public int getBook_date() {
		return book_date;
	}

}
