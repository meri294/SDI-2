package com.sdi.business;

import com.sdi.model.User;

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
	public User validar(String login, String pass);

}
