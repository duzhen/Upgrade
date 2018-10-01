package com.upgrade.pacificocean.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "results")
public class RestResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -872877884975979692L;
	@XmlElement
	private int code = 200;
	@XmlElement
	private String version = "v0.1";
	@XmlElement
	private String message;
	@XmlElement
	private Object result;
	
	public RestResult(int code, String message, Object o) {
		super();
		this.code = code;
		this.message = message;
		this.result = o;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
