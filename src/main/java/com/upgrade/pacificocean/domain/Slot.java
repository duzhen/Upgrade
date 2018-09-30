package com.upgrade.pacificocean.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "slot")
public class Slot implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6049826396270086639L;
	
	@XmlAttribute(name = "date")
	private int date;
	@XmlAttribute(name = "available")
	private boolean available;
	
	public Slot(int date, boolean available) {
		super();
		this.date = date;
		this.available = available;
	}
	
}
