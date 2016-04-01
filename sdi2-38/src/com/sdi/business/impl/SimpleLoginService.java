package com.sdi.business.impl;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import alb.util.log.Log;

import com.sdi.business.LoginService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.model.UserStatus;

public class SimpleLoginService implements LoginService {

	@Override
	public User validar(String login, String pass) {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msgs");
		User usuario = Factories.persistence.createUserDao().findByLogin(login);
		if (usuario != null) {
			if (usuario.getPassword().equals(pass)) {
				if (usuario.getStatus().equals(UserStatus.ACTIVE)) { 
					return usuario;
				} else { // Si la cuenta no esta activada...
					Log.error(
							"El usuario [%s] ha intentado iniciar sesión estando su cuenta desactivada",
							login);
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							bundle.getString("error_cancelledAccount"),
							bundle.getString("error_cancelledAccount"));
					throw new ValidatorException(message);			
				}

			}

			else { // Si no coincide su contraseña...
				Log.error(
						"El usuario [%s] ha introducido una contraseña incorrecta",
						login);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						bundle.getString("error_invalidPassword"),
						bundle.getString("error_invalidPassword"));
				throw new ValidatorException(message);
			}

		} else { // Si el usuario no existe...
			Log.error("El usuario [%s] no está registrado", login);
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("error_noSuchUser"),
					bundle.getString("error_noSuchUser"));
			throw new ValidatorException(message);
			
		}
	}

}
