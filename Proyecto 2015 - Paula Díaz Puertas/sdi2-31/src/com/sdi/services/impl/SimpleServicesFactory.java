package com.sdi.services.impl;

import com.sdi.services.CambioUsuarioService;
import com.sdi.services.LoginService;
import com.sdi.services.ReinicioBBDDService;
import com.sdi.services.ServicesFactory;

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

}
