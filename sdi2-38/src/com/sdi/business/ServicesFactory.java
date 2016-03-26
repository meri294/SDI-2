package com.sdi.business;

public interface ServicesFactory {
	
	public LoginService createLoginService();
	
	public CambioUsuarioService createCambioUsuarioService();
	
	public ReinicioBBDDService createReinicioBBDDService();
	
	public TripService createTripService();

	public ApplicationService createApplicationService();

}
