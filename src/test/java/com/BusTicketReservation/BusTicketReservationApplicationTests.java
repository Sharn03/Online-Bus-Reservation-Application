package com.BusTicketReservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.BusTicketReservation.Dto.UserRegisteredDto;
import com.BusTicketReservation.Entity.Booking;
import com.BusTicketReservation.Entity.BusData;
import com.BusTicketReservation.Entity.BusReservation;
import com.BusTicketReservation.Repository.BookingRepository;
import com.BusTicketReservation.Repository.BusDataRepository;
import com.BusTicketReservation.Repository.UserRepository;
import com.BusTicketReservation.Service.BookingService;
import com.BusTicketReservation.Service.BusDataService;
import com.BusTicketReservation.Service.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
class BusTicketReservationApplicationTests {

	@Autowired
	private UserRepository userRepo;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BusDataService busDataService;
	
	@Autowired
	private BusDataRepository busDataRepo;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private BookingRepository bookingRepo;
	
	    @Test
	    public void checkingRegisterAndLoginAsAdmin() 
	  
	    {
	        UserRegisteredDto userRegisteredDto = new UserRegisteredDto();
	        userRegisteredDto.setEmailId("tempAdmin@gmail.com");
	        userRegisteredDto.setName("tempAdminName");
	        userRegisteredDto.setPassword("123");
	        userRegisteredDto.setRole("ADMIN");
	        userService.save(userRegisteredDto);

	        // checking  Admin role user data is found in the database
	        Assert.notNull(userRepo.findByEmailId("tempAdmin@gmail.com"), "User data found in database");

	        // checking the admin user can log in
	        UserDetails user = userService.loadUserByUsername("tempAdmin@gmail.com"); // Corrected email ID
	        Assert.notNull(user, "Logged in Successfully");
	    }
	

	    @Test
	    public void checkingRegisterAndLoginAsUser()
	   
	    {
	        UserRegisteredDto userRegisteredDto = new UserRegisteredDto();
	        userRegisteredDto.setEmailId("tempUser@gmail.com");
	        userRegisteredDto.setName("tempUserName");
	        userRegisteredDto.setPassword("123");
	        userRegisteredDto.setRole("USER");
	        userService.save(userRegisteredDto);

	        // checking the User role user is found in the database
	        Assert.notNull(userRepo.findByEmailId("tempUser@gmail.com"), "User data found in database");

	        // checking the User user can log in
	        UserDetails user = userService.loadUserByUsername("tempUser@gmail.com"); // Corrected email ID
	        Assert.notNull(user, "Logged in Successfully");
	    }
	    
	    @Test
	    public void saveBusData()
	    {
	    	BusData busData = new BusData();
	    
	    	busData.setBusName("busName");
	    	busData.setBusNo("0000");
	    	busData.setDate("1/1/2000");
	    	busData.setFromCity("tempFromCity");
	    	busData.setToCity("tempToCity");
	    	busData.setTiming("n am ");
	    	busData.setTotalSeats("n");
	    	busData.setTravelDuration("n hrs");
	    	busData.setTicketPrice("1000");
	    	
	    	busDataService.saveBusData(busData);
	    	assertThat(busData.getId()).isNotNull();
	    	  
    	   //checking the the bus-data is present in the repository or not
           Optional<BusData> savedBusData = busDataRepo.findById(busData.getId());
           assertThat(savedBusData).isPresent();
    	
	    }
	    
	    @Test
	    public void saveBooking()
	    {
	    	Booking booking = new Booking();
	    	booking.setBusName("busName");
	    	booking.setBusNo("0000");
	    	booking.setDate("1/1/2000");
	    	booking.setFromCity("tempFromCity");
	    	booking.setToCity("tempToCity");
	    	booking.setTiming("n am ");
	    	booking.setTotalSeats("n");
	    	booking.setTravelDuration("n hrs");
	    	booking.setTicketPrice("1000");
	    	
	    	bookingService.saveBooking(booking);
	    	assertThat(booking.getId()).isNotNull();
	    	  
	        //checking the the booking is present in the repository or not
	         Optional<Booking> savedBooking = bookingRepo.findById(booking.getId());
	         assertThat(savedBooking).isPresent();
	    	
	    }
	    
	    
	    @Test
	    public void fetchBusData() {
	   
	        BusData busData = new BusData();
	        busData.setBusName("busName");
	        busData.setBusNo("0000");
	        busData.setDate("1/1/2000");
	        busData.setFromCity("tempFromCity");
	        busData.setToCity("tempToCity");
	        busData.setTiming("n am");
	        busData.setTotalSeats("n");
	        busData.setTravelDuration("n hrs");
	        busData.setTicketPrice("1000");

	        busDataService.saveBusData(busData);

	        // Fetching the bus-data that matches the parameters
	        List<BusData> fetchedBusData = busDataRepo.searchBuses("tempFromCity", "tempToCity", "1/1/2000");

	        // checking that data is fetched correctly
	        assertThat(fetchedBusData).isNotEmpty();
	        assertThat(fetchedBusData).contains(busData);
	    }
	    
	

}
