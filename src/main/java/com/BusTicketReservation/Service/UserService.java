package com.BusTicketReservation.Service;

import org.springframework.security.core.userdetails.UserDetailsService;


import com.BusTicketReservation.Dto.UserRegisteredDto;
import com.BusTicketReservation.Entity.User;


public interface UserService  extends UserDetailsService{
	
	User save(UserRegisteredDto userRegisteredDto);

}
