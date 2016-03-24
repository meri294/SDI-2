package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import com.sdi.model.Application;

@ManagedBean(name = "application")
@SessionScoped
public class BeanApplication extends Application implements Serializable {
	private static final long serialVersionUID = 55556L;

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