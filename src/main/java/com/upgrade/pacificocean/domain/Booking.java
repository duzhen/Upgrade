package com.upgrade.pacificocean.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "booking")
public class Booking implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7925437957055476103L;
	@XmlElement
	private int id;
	@XmlElement
	private String name;
	@XmlElement
	private String email;
	@XmlElement
	private int camp_id;
	@XmlElement
	private int start_date;
	@XmlElement
	private int end_date;
	@XmlElement
	private Date check_in;
	@XmlElement
	private Date check_out;
	@XmlElement
	private boolean cancelled;
}
