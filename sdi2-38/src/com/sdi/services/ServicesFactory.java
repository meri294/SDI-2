package com.sdi.services;

public interface ServicesFactory {
	
	public LoginService createLoginService();
	
	public CambioUsuarioService createCambioUsuarioService();
	
	public ReinicioBBDDService createReinicioBBDDService();
	
	public TripService createTripService();

}
