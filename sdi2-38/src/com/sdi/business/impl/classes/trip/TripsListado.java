package com.sdi.business.impl.classes.trip;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.persistence.TripDao;
import com.sdi.util.MariaDateUtil;

public class TripsListado {

	public List<Trip> getTrips() throws Exception {	
		TripDao dao = Factories.persistence.createTripDao();
		actualizarViajes(dao);
		return buscarDisponibles(dao.findAll());
	}
	
	private List<Trip> buscarDisponibles(List<Trip> trips){
		List<Trip> activos = new ArrayList<Trip>();
		for (Trip trip : trips) {
			if (trip.getStatus().equals(TripStatus.OPEN)
					&& trip.getAvailablePax() > 0)
				activos.add(trip);
		}
		return activos;
	}
	
	private void actualizarViajes(TripDao dao) {
		List<Trip> viajes = dao.findAll();
		try {
			for (Trip trip : viajes) {
				if (MariaDateUtil.isAfter(MariaDateUtil.now(),
						trip.getClosingDate())) {
					if (MariaDateUtil.isAfter(MariaDateUtil.now(),
							trip.getDepartureDate()))
						trip.setStatus(TripStatus.DONE);
					else
						trip.setStatus(TripStatus.CLOSED);
				} else
					trip.setStatus(TripStatus.OPEN);
				dao.update(trip);
			}
		} catch (Exception e) {
			Log.error("No se ha podido actualizar la lista de viajes");
		}
	}
}
