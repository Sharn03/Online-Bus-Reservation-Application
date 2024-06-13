package com.BusTicketReservation.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.BusTicketReservation.Service.UserServiceImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig{
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	    
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userServiceImpl);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
	
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	
	{
        auth.authenticationProvider(authenticationProvider());
    }
	
	 @Bean
	  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 //Here we making the custom login page for Login end points
		 http
		 .csrf().disable()
		 .authorizeRequests().requestMatchers("/registration").permitAll()
		  .requestMatchers("/busData","/addNewBusData", "/updateBusData/**", "/deleteBusData/**","/bookingHistory").hasRole("ADMIN") // Restrict these endpoints to ADMIN role
          .anyRequest() // Any other request needs authentication
         .authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/busReservation") .permitAll().and().logout()
        
         .invalidateHttpSession(true).clearAuthentication(true)
         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
         .logoutSuccessUrl("/login?logout")
                                
         .permitAll();
		 
		 return http.build();
	
	 	}

	

    		
}
