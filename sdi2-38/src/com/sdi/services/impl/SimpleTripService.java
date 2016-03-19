package com.sdi.services.impl;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.sdi.model.Trip;
import com.sdi.services.TripService;
import com.sdi.services.impl.classes.trip.TripsAlta;
import com.sdi.services.impl.classes.trip.TripsListado;

public class SimpleTripService implements TripService {

	@Override
	public List<Trip> getTrips() throws Exception{
		return new TripsListado().getTrips();
	}

	@Override
	public void saveTrip(Trip trip) {
		new TripsAlta().save(trip);
	}

	@Override
	public void updateTrip(Trip trip) {
		new TripsUpdate().update(trip);
	}

	@Override
	public void deleteTrip(Long id) {
		new TripsBaja().delete(id);
	}

	@Override
	public Trip findById(Long id) {
		return new TripsBuscar().find(id);
	}

}
