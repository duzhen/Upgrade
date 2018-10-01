package com.upgrade.pacificocean.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "slots")
public class Slots implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4207006385218791959L;
	@XmlElement(name="slot")
	private List<Slot> slots;

	public Slots(List<Slot> slots) {
		this.slots = slots;
	}
	
}
