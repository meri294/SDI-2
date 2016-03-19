package com.sdi.services.impl.classes.trip;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;

public class TripsListado {

	public List<Trip> getTrips() throws Exception {
		TripDao dao = Factories.persistence.createTripDao();
		return  dao.findAll();

	}
}
