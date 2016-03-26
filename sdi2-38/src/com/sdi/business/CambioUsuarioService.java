package com.sdi.business;

import com.sdi.model.User;

public interface CambioUsuarioService {

	public User modificarDatos(User u, String nombre, String apellidos,
			String email);
	public User modificarPass(User u, String password);

}
