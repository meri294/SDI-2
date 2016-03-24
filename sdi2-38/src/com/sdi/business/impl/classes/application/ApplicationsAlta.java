package com.sdi.business.impl.classes.application;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.TripDao;
import com.sdi.util.MariaDateUtil;
import com.sdi.model.Trip;

public class ApplicationsAlta {

	public void save(Application application) throws Exception {
		ApplicationDao dao = Factories.persistence.createApplicationDao();
		String posible= solicitudPosible(application);
		if(posible==null)
			dao.save(application);
		else{
			throw new Exception (posible);
		}
	}
	
	private String solicitudPosible(Application app){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		TripDao dao= Factories.persistence.createTripDao();
		Trip trip=dao.findById(app.getTripId());
		if (trip.getPromoterId().equals(app.getUserId()))
			return bundle.getString("error_esPromotor");
		if(MariaDateUtil.isAfter(MariaDateUtil.now(), trip.getClosingDate()))
			return bundle.getString("error_viajeCerrado");
		return null;
	}

}

	