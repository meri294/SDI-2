package com.sdi.services;

import com.sdi.model.Usuario;

public interface LoginService {

	/**
	 * Valida la existencia del login introducido y que la pass sea correcta.
	 * 
	 * @param login
	 *            Login del usuario
	 * @param pass
	 *            Password del usuario
	 * @return El usuario si la validacion ha sido correcta; null en caso
	 *         contrario
	 */
	public Usuario validar(String login, String pass);

}
