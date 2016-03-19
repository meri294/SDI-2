package com.sdi.services.impl.classes.trip;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;
import com.sdi.services.exception.EntityAlreadyExistsException;

public class TripsAlta {

	public void save(Trip trip) throws EntityAlreadyExistsException {
		TripDao dao = Factories.persistence.createTripDao();
		try {
			dao.save(trip);
		}
		catch (AlreadyPersistedException ex) {
			throw new EntityAlreadyExistsException("Trip ya existe " + trip, ex);
		}
	}
}
