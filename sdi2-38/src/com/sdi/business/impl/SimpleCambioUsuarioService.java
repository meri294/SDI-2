package com.sdi.business.impl;

import alb.util.log.Log;

import com.sdi.business.CambioUsuarioService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class SimpleCambioUsuarioService implements CambioUsuarioService {

	@Override
	public User modificarDatos(User u, String nombre, String apellidos,
			String email) {
		u.setName(nombre);
		u.setSurname(apellidos);
		u.setEmail(email);
		try {
			Factories.persistence.createUserDao().update(u);
			Log.debug("Modificados datos de [%s]: nuevo nombre = [%s], nuevos apellidos = [%s], nuevo email = [%s]", u.getLogin(), nombre, apellidos, email);
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido actualizando los datos de [%s]", u.getLogin());
			return null;
		}
		return u;
	}

	@Override
	public User modificarPass(User u, String password) {
		u.setPassword(password);
		try {
			Factories.persistence.createUserDao().update(u);
			Log.debug("Modificada la contraseña de [%s]", u.getLogin());
		}
		catch (Exception e) {
			Log.error("Algo ha ocurrido modificando la contraseña de [%s]", u.getLogin());
			return null;
		}
		return u;
	}

}
