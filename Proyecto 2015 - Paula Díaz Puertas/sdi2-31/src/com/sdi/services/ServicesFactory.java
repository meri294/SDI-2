package com.sdi.services;

public interface ServicesFactory {
	
	/**
	 * Crea y devuelve un LoginService
	 */
	public LoginService createLoginService();
	
	/**
	 * Crea y devuelve un CambioUsuarioService
	 */
	public CambioUsuarioService createCambioUsuarioService();
	
	/**
	 * Crea y devuelve un ReinicioBBDDService
	 */
	public ReinicioBBDDService createReinicioBBDDService();

}
