package com.sdi.business.impl;

import alb.util.log.Log;

import com.sdi.business.LoginService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class SimpleLoginService implements LoginService {

	@Override
	public User validar(String login, String pass) {

		User usuario = Factories.persistence.createUserDao().findByLogin(
				login);
		if (usuario != null) { // Si el usuario existe...
			if (usuario.getPassword().equals(pass)) { // y coincide su
													// contraseña...
				// TODO Revisar - if (usuario.isActivo()) { // Y su cuenta esta activada...
					return usuario;
					/*
				} else { // Si la cuenta no esta activada...

					Log.info(
							"El usuario [%s] ha intentado iniciar sesión estando su cuenta desactivada",
							login);
					/*
					 * TODO request.setAttribute( "mensaje",
					 * "Su cuenta no esta activada. Por favor, hable con un administrador para activarla"
					 * );
					return null;
				}
					 */
			
			}

			else { // Si no coincide su contraseña...
				Log.info(
						"El usuario [%s] ha introducido una contraseña incorrecta [%s]",
						login, pass);
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
