package com.BusTicketReservation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.BusTicketReservation.Dto.UserLoginDto;
import com.BusTicketReservation.Service.UserService;



@Controller
public class LoginController {
	
	@Autowired
	 private UserService userService;
	
	 @Autowired
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	  
	@GetMapping("/login")
	public String login(Model m)
	{
		UserLoginDto userLoginDto = new UserLoginDto();
		m.addAttribute("userLoginDto", userLoginDto);
		return "USER_REGISTER/loginPage";
	}
	
	
	//End to point to check if the user-details are correct or not 
	@PostMapping("/login")
	public String loginUser(@ModelAttribute("user") 
	UserLoginDto userLoginDto)
	{
		System.out.println("UserDTO"+userLoginDto);
		userService.loadUserByUsername(userLoginDto.getUsername());
		
		if(userLoginDto.getPassword().equals(passwordEncoder.encode(userLoginDto.getPassword())))
		{
			return "redirect:/busReservation";
		}
		else
		{
			return "redirect:/login?error";
		}
	
	}
}
