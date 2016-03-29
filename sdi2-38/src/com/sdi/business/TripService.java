package com.sdi.business;

import java.util.List;
import java.util.Map;

import com.sdi.model.Trip;

public interface TripService {

	void saveTrip(Trip trip);

	void updateTrip(Trip trip) throws Exception;

	List<Trip> getTrips() throws Exception;

	void deleteTrip(Long id) throws Exception;

	Trip findById(Long id) throws Exception;

	Map<String, List<Trip>> findInvolucrado(Long id) throws Exception;
	
	void disminuirPlazasDisponibles(Long id) throws Exception;
	
	void disminuirPlazasDisponibles(Trip trip) throws Exception;
	
	void aumentarPlazasDisponibles(Long id) throws Exception;
	
	void aumentarPlazasDisponibles(Trip trip) throws Exception;

}
