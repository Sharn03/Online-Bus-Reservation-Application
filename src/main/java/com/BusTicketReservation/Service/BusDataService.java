package com.BusTicketReservation.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.BusTicketReservation.Entity.BusData;
import com.BusTicketReservation.Repository.BusDataRepository;


@Service
public class BusDataService {

	@Autowired
	private BusDataRepository busDataRepo;
	
	public List<BusData>getAllBusData()
	{
		List<BusData> busList = busDataRepo.findAll();
		return busList;
	}
	
	//Method to get busdata by id
	public BusData getBusById(int id)
	{
		BusData busData = null;
		if(Objects.nonNull(id))
		{
			Optional<BusData>optionalBusData = busDataRepo.findById(id);
			if(optionalBusData.isPresent())
			{
				busData = optionalBusData.get();
			}
			else
			{
				throw new RuntimeException("Booking ID "+ id +" Not Found ");
			}
		}
		return busData;
		
				
	}
	
	//Method to save BusData
	public void saveBusData(BusData busData)
	{
		//checking the arguments is null or not
		if(Objects.nonNull(busData))
		{
			
		 busDataRepo.save(busData);
		
		}
	}
	
	//Method to search Buses
	 public List<BusData> searchBuses(BusData busData)
	 {
	        return busDataRepo.searchBuses(busData.getFromCity(), busData.getToCity(), busData.getDate().toString());
	  }
	
	
	//Method to update BusData
	public BusData updateBusData(BusData busData)
	{
		BusData existingBusData = null;
		
		if(Objects.nonNull(busData))
		{
			existingBusData =  busDataRepo.findById(busData.getId()).get();
			existingBusData.setBusName(busData.getBusName());
			existingBusData.setBusNo(busData.getBusNo());
			existingBusData.setDate(busData.getDate());
			existingBusData.setFromCity(busData.getFromCity());
			existingBusData.setToCity(busData.getToCity());
			existingBusData.setTiming(busData.getTiming());
			existingBusData.setTicketPrice(busData.getTicketPrice());
			existingBusData.setTravelDuration(busData.getTravelDuration());
		}
		
		return busDataRepo.save(existingBusData);
	}
	
	
	//Method to delete Bus Details
	public void deleteById(int id)
	{
		if(Objects.nonNull(id))
		{
			busDataRepo.deleteById(id);
		}
	}
	
}
