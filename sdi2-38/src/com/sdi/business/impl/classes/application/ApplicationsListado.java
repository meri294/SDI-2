package com.sdi.business.impl.classes.application;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;

public class ApplicationsListado {
	
	public List<Application> getApplications() throws Exception {	
		ApplicationDao dao = Factories.persistence.createApplicationDao();
		return dao.findAll();
	}

}
