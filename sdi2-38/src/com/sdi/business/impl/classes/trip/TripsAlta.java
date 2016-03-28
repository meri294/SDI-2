package com.sdi.business.impl.classes.trip;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;

public class TripsAlta {

	public void save(Trip trip) {
		TripDao dao = Factories.persistence.createTripDao();
		Long id = dao.save(trip);
		trip.setId(id);
	}
}
