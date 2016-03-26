package com.sdi.business.impl.classes.trip;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.TripDao;

public class TripsBaja {

	public void delete(Long id) throws Exception {
		TripDao dao = Factories.persistence.createTripDao();
		try {
			dao.delete(id);
		}
		catch (Exception ex) {
			throw new Exception("Viaje no eliminado " + id, ex);
		}
	}
}
