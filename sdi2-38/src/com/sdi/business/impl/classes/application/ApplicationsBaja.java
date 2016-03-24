package com.sdi.business.impl.classes.application;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.ApplicationDao;

public class ApplicationsBaja {

	public void delete(Long[] ids) throws Exception {
		ApplicationDao dao = Factories.persistence.createApplicationDao();
		try {
			dao.delete(ids);
		}
		catch (Exception ex) {
			throw new Exception("Solicitud no eliminada " + ids, ex);
		}
	}
}
