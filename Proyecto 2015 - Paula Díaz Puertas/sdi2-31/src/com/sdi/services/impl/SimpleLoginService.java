package com.sdi.services.impl;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;
import com.sdi.services.LoginService;

public class SimpleLoginService implements LoginService {

	@Override
	public Usuario validar(String login, String pass) {

		Usuario usuario = Factories.persistence.createUsuarioDao().findByLogin(
				login);
		if (usuario != null) { // Si el usuario existe...
			if (usuario.getPasswd().equals(pass)) { // y coincide su
													// contraseña...
				if (usuario.isActivo()) { // Y su cuenta esta activada...
					return usuario;
				} else { // Si la cuenta no esta activada...

					Log.info("El usuario [%s] ha intentado iniciar sesión "
							+ "estando su cuenta desactivada", login);
					/*
					 * TODO request.setAttribute( "mensaje",
					 * "Su cuenta no esta activada. Por favor, hable con un administrador para activarla"
					 * );
					 */
					return null;
				}
			}

			else { // Si no coincide su contraseña...
				Log.info("El usuario [%s] ha introducido una contraseña "
						+ "incorrecta [%s]", login, pass);
				/*
				 * TODO request.setAttribute("mensaje",
				 * "El login o la contraseña no son correctas");
				 */
				return null;
			}

		} else { // Si el usuario no existe...
			Log.info("El usuario [%s] no está registrado", login);
			/*
			 * TODO request.setAttribute("mensaje",
			 * "El login o la contraseña no son correctas");
			 */
			return null;
		}
	}

}
