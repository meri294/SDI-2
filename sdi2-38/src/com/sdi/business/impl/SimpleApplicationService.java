package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.ApplicationService;
import com.sdi.business.impl.classes.application.ApplicationBuscar;
import com.sdi.business.impl.classes.application.ApplicationsAlta;
import com.sdi.business.impl.classes.application.ApplicationsBaja;
import com.sdi.business.impl.classes.application.ApplicationsListado;
import com.sdi.business.impl.classes.application.ApplicationsUpdate;
import com.sdi.model.Application;

public class SimpleApplicationService implements ApplicationService {

	@Override
	public List<Application> getApplications() throws Exception{
		return new ApplicationsListado().getApplications();
	}

	@Override
	public void saveApplication(Application application) throws Exception {
		new ApplicationsAlta().save(application);
	}

	@Override
	public void updateApplication(Application application) throws Exception {
		new ApplicationsUpdate().update(application);
	}

	@Override
	public void deleteApplication(Long[] ids) throws Exception {
		new ApplicationsBaja().delete(ids);		
	}

	@Override
	public List<Application> findByUserId(Long userId) throws Exception {
		return new ApplicationBuscar().findByUser(userId);
	}

	@Override
	public List<Application> findByTripId(Long tripId) throws Exception {
		return new ApplicationBuscar().findByTrip(tripId);
	}

	@Override
	public Application findById(Long[] ids) throws Exception {
		return new ApplicationBuscar().find(ids);
	}

	@Override
	public List<Application> getApplicationsWithoutSeatFor(Long tripId) {
	    return new ApplicationsListado().getApplicationsWithoutSeatFor(tripId);
	}

}
