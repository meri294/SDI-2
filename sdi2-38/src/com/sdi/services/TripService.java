package com.sdi.services;

import java.util.List;

import com.sdi.model.Trip;

public interface TripService {

	void saveTrip(Trip trip);

	void updateTrip(Trip trip);

	List<Trip> getTrips() throws Exception;

	void deleteTrip(Long id);

	Trip findById(Long id);

}
