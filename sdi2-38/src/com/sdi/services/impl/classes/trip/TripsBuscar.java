package com.sdi.services.impl.classes.trip;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;

public class TripsBuscar {
	
	public Trip find(Long id) throws Exception {
		TripDao dao = Factories.persistence.createTripDao();
		Trip trip = dao.findById(id);
		if ( trip == null) {
			throw new Exception("No se ha encontrado el viaje");
		}
		
		return trip;
	}

}
