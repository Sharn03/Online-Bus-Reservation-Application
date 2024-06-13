package com.BusTicketReservation.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.BusTicketReservation.Dto.UserRegisteredDto;
import com.BusTicketReservation.Entity.User;
import com.BusTicketReservation.Repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {
	
	 @Autowired
	    private UserRepository userRepo;
	    
	   
	 private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    @Override
	    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
	        Optional<User> user = Optional.of(userRepo.findByEmailId(emailId));
	        System.out.println(emailId);
	        return user.map(UserInfoUserDetails::new)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + emailId));
	    }
	    
	    public User save(UserRegisteredDto userRegisteredDto) {
	        User user = new User();
	        user.setName(userRegisteredDto.getName());
	        user.setEmailId(userRegisteredDto.getEmailId());
	        // Encode the password before saving
	        user.setPassword(passwordEncoder.encode(userRegisteredDto.getPassword()));
	        // Set user role based on registration
	        if ("ADMIN".equals(userRegisteredDto.getRole())) {
	            user.setRole("ROLE_ADMIN");
	        } else {
	            user.setRole("ROLE_USER"); // Default role for new users
	        }
	       return userRepo.save(user);
	    }
	


}
