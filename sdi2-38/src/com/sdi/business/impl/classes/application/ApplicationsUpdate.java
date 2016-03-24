package com.sdi.business.impl.classes.application;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;

public class ApplicationsUpdate {

	public void update(Application application) throws Exception {
		ApplicationDao dao = Factories.persistence.createApplicationDao();
		try {
			dao.update(application);
		}
		catch (Exception ex) {
			throw new Exception("Solicitud no actualizada " + application, ex);
		}
	}
}
