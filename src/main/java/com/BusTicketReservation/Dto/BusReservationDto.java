package com.BusTicketReservation.Dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class BusReservationDto {
	
	private String fromCity;
	private String toCity;
	
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date date;
	
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}
