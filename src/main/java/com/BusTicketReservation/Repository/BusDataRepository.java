package com.BusTicketReservation.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.BusTicketReservation.Entity.BusData;

@Repository
public interface BusDataRepository extends JpaRepository<BusData,Integer> {


	
	  @Query("SELECT b FROM BusData b WHERE b.fromCity = :fromCity AND b.toCity = :toCity AND b.date = :date")
	   List<BusData> searchBuses(@Param("fromCity") String fromCity, @Param("toCity") String toCity, @Param("date") String date);
}
