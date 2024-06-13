package com.BusTicketReservation.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.BusTicketReservation.Entity.Booking;
import com.BusTicketReservation.Entity.BusData;


@Repository
public interface BookingRepository extends  JpaRepository<Booking,Integer> {

	List<Booking> findBookingByEmailId(String userEmail);

	
}
