package com.sdi.presentation;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ActionEvent;

import com.sdi.model.Application;

@ManagedBean(name = "application")
public class BeanApplication extends Application{

	public BeanApplication() {
		iniciaApplication(null);
	}

	public void setApplication(Application application) {
		setTripId(application.getTripId());
		setUserId(application.getUserId());
	}

	public void iniciaApplication(ActionEvent event) {
		setTripId(null);
		setUserId(null);		
	}
}