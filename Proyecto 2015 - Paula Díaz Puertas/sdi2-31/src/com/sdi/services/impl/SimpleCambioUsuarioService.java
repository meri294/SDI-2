package com.sdi.services.impl;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;
import com.sdi.services.CambioUsuarioService;

public class SimpleCambioUsuarioService implements CambioUsuarioService {

	@Override
	public Usuario modificarDatos(Usuario u, String nombre, String apellidos,
			String email) {
		u.setNombre(nombre);
		u.setApellidos(apellidos);
		u.setEmail(email);
		try {
			Factories.persistence.createUsuarioDao().update(u);
			Log.debug("Modificados datos de [%s]: nuevo nombre = [%s], "
					+ "nuevos apellidos = [%s], nuevo email = [%s]", 
					u.getLogin(), nombre, apellidos, email);
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido actualizando los datos de [%s]", 
					u.getLogin());
			return null;
		}
		return u;
	}

	@Override
	public Usuario modificarPass(Usuario u, String password) {
		u.setPasswd(password);
		try {
			Factories.persistence.createUsuarioDao().update(u);
			Log.debug("Modificada la contraseña de [%s]", u.getLogin());
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido modificando la contraseña de [%s]", 
					u.getLogin());
			return null;
		}
		return u;
	}

}
