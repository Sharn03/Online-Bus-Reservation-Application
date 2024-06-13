package com.BusTicketReservation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.BusTicketReservation.Entity.Booking;
import com.BusTicketReservation.Entity.BusData;
import com.BusTicketReservation.Service.BookingService;
import com.BusTicketReservation.Service.BusDataService;


@Controller

public class BusDataController {
	@Autowired
	private BusDataService busDataService;
	
	
	@Autowired
	private BookingService bookingService;
	
	//End point for Bus Data List
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/busData")
    public String busData(Model m)
	{
		m.addAttribute("allBusDataDetails", busDataService.getAllBusData());
		return "BUSDATA/busDataIndex";
	}
	
	
	//End points to add new BusData
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/addNewBusData")
	
	public String addNewBusData(Model m)
	{
		BusData busData = new BusData();
		m.addAttribute("busData", busData);
		return "BUSDATA/addBusData";
	}
	
	
	//End points to save Bus data
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/saveBusData")
	
	public String saveBusData(@ModelAttribute("busData") BusData busData)
	{
		busDataService.saveBusData(busData);
		return "redirect:/busData";
	}
	
	
	//End points to update BusData
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/updateBusData/{id}")
	
	public String updateBusData(@PathVariable("id") int id, Model m)
	{
		BusData busData = busDataService.getBusById(id);
		m.addAttribute("busData", busData);
		return "BUSDATA/updateBusData";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/deleteBusData/{id}")

    public String deleteBusData(@PathVariable("id") int id)
	{
		 busDataService.deleteById(id);
		 return "redirect:/busData";
	}
	
	//End points for book ticket
	@GetMapping("/bookTicket/{id}")
	public String bookTicket(@PathVariable("id") int id,Model m)
	{
		BusData busData = busDataService.getBusById(id);
		Booking booking = new Booking();
		booking.setBusNo(busData.getBusNo());
        booking.setBusName(busData.getBusName());
        booking.setFromCity(busData.getFromCity());
        booking.setToCity(busData.getToCity());
        booking.setDate(busData.getDate());
        booking.setTiming(busData.getTiming());
        booking.setTravelDuration(busData.getTravelDuration());
        booking.setTicketPrice(busData.getTicketPrice());
        booking.setTotalSeats(busData.getTotalSeats());
   
	
		m.addAttribute("booking",booking);
		return "TICKETBOOKING/ticketBookingPage";
		
	}
	
	
	
	//Method the Confirm Booking
	 @PostMapping("/confirmBooking")
	 public String confirmBooking(@ModelAttribute("booking")Booking booking,Model m) 
	      {
	      	
	        bookingService.saveBooking(booking);
	        m.addAttribute("booking",booking);
			return "BOOKINGSUCCESS/bookingSuccess";
	    }
	

}
