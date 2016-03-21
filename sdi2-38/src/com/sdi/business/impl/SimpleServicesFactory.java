package com.sdi.business.impl;

import com.sdi.business.CambioUsuarioService;
import com.sdi.business.LoginService;
import com.sdi.business.ReinicioBBDDService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;

public class SimpleServicesFactory implements ServicesFactory {

	@Override
	public LoginService createLoginService() {
		return new SimpleLoginService();
	}

	@Override
	public CambioUsuarioService createCambioUsuarioService() {
		return new SimpleCambioUsuarioService();
	}

	@Override
	public ReinicioBBDDService createReinicioBBDDService() {
		return new SimpleReinicioBBDDService();
	}

	@Override
	public TripService createTripService() {
		return new SimpleTripService();
	}

}
