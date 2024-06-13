package com.BusTicketReservation.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.BusTicketReservation.Dto.BusReservationDto;

import com.BusTicketReservation.Entity.BusData;
import com.BusTicketReservation.Service.BusDataService;

@Controller

public class BusReservationController {

	  @Autowired
	    private BusDataService busDataService;

	
	
	@GetMapping("/busReservation")
	public String busReservation(Model m)
	{
		 BusReservationDto busReservationDtO = new  BusReservationDto();
		m.addAttribute("reservation", busReservationDtO);
		return "USER_REGISTER/busReservation";
	}
	
	
	@PostMapping("/busReservation")
	public String busReservation( @ModelAttribute("reservation") BusReservationDto reservationDTO)
	{
	    return "USER_REGISTER/busReservation";
	}
	

	//Method to search buses
	 @GetMapping("/searchBuses")
	    public String searchBuses(@ModelAttribute("busData") BusData busData, Model m) {
	        m.addAttribute("searchListofBuses", busDataService.searchBuses(busData));
	        return "SEARCHBUS/searchBuses";
	    }
		
		
}
