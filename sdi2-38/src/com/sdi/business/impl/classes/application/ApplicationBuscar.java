package com.sdi.business.impl.classes.application;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;

public class ApplicationBuscar {

	public Application find(Long[] ids) throws Exception {
		ApplicationDao dao = Factories.persistence.createApplicationDao();
		Application app = dao.findById(ids);
		if ( app == null) {
			throw new Exception("No se ha encontrado la solicitud");
		}
		
		return app;
	}

	public List<Application> findByUser(Long userId) throws Exception {
		ApplicationDao dao = Factories.persistence.createApplicationDao();
		List<Application> apps = dao.findByUserId(userId);
		if (apps==null){
			throw new Exception ("NO se han encontrado solicitudes para ese"
					+ " usuario");
		}
		
		return apps;
	}

	public List<Application> findByTrip(Long tripId) throws Exception {
		ApplicationDao dao = Factories.persistence.createApplicationDao();
		List<Application> apps = dao.findByTripId(tripId);
		if (apps==null){
			throw new Exception ("No se han encontrado solicitudes para ese"
					+ " viaje");
		}
		
		return apps;
		
	}

}
