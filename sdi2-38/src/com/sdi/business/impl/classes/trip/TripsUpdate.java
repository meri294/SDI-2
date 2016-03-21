package com.sdi.business.impl.classes.trip;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.TripDao;
import com.sdi.model.Trip;

public class TripsUpdate {

	public void update(Trip trip) throws Exception {
		TripDao dao = Factories.persistence.createTripDao();
		try {
			dao.update(trip);
		}
		catch (Exception ex) {
			throw new Exception("Viaje no actalizado " + trip, ex);
		}
	}
}
