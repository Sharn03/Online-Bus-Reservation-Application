package com.BusTicketReservation.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	
	
	//End point to view all the Bookings made
	@GetMapping("/bookingHistory")
	public String bookingHistory(Model m)
	{
		m.addAttribute("allBookings", bookingService.getAllBookings());
		return "BOOKINGHISTORY/bookingHistory";
	}
	
	
	//end point for deleting the booking history
	@GetMapping("/deleteBookingHistory/{id}")
	public String deleteBookingHistory(@PathVariable("id")int id)
	{
		bookingService.deleteBookingHistory(id);
		return "redirect:/bookingHistory";
	}
	
	
    @GetMapping("/myBookings")
    public String myBookings(Model model)
    {
        // Retrieve logged-in user's email ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        System.out.println(userEmail);

        // Fetch bookings related to the user's email ID
        List<Booking> userBookings = bookingService.getBookingsByUserEmail(userEmail);
    	
    	

        // Add bookings to the model
        model.addAttribute("userBookings", userBookings);

        // Return the view to display user's bookings
        return "MYBOOKINGS/myBookings";
    }
	
	
	
	//end point for generating the ticket
    @GetMapping("/generateETicket/{id}")
    public String generateETicket(@PathVariable("id") int id, Model model) {
        
            Booking booking = bookingService.getBookingById(id);
            if (booking != null) {
                bookingService.generateAndSendTicket(booking);
                model.addAttribute("booking", booking);
                //return ResponseEntity.ok("ETICKET/eTicketSuccessResponse");
                return "ETICKET/eTicketSuccessResponse";
            } 
                //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking ID " + id + " not found.");
            	return "ETICKET/eTicketErrorResponse";

    }
		

		
	
}
