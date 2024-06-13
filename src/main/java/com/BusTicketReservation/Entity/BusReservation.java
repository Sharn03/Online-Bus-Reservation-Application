package com.BusTicketReservation.Entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BusReservation {

	private int id;
	private String fromCity;
	private String toCity;
	
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private String date;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	
	
}
