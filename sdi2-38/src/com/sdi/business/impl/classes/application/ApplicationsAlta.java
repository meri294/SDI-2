package com.sdi.business.impl.classes.application;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

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
			FacesMessage message= new FacesMessage(FacesMessage.SEVERITY_ERROR,
					posible, posible);
			throw new ValidatorException(message);
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
		if(trip.getAvailablePax()==0)
			return bundle.getString("error_sinPlaza");
		return null;
	}

}

	