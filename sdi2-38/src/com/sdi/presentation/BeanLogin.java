package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.util.CifradoMD5;

@ManagedBean(name = "login")
@ViewScoped
public class BeanLogin implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login = "";
	private String pass = "";

	@ManagedProperty(value = "#{sesion}")
	private BeanSesion sesion;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public BeanSesion getSesion() {
		return sesion;
	}

	public void setSesion(BeanSesion sesion) {
		this.sesion = sesion;
	}

	public String validar() {

		User usuario = sesion.getUsuario();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");

		if (usuario != null) {
			Log.debug("Se ha intentado iniciar sesión como [%s] "
					+ "teniendo la sesión iniciada como [%s]", login,
					usuario.getName());
			sesion.cerrar();
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					bundle.getString("error_sesionYaIniciada"),
					bundle.getString("error_sesionYaIniciada"));
			facesContext.addMessage(null, message);
			return Resultado.error.name();
		}
		// El service debe devolver un usuario si se ha validado correctamente
		// o null si no.
		try {
			usuario = Factories.services.createLoginService().validar(login,
					CifradoMD5.getStringMessageDigest(pass));
			sesion.setUsuario(usuario);
			Log.debug("El usuario [%s] ha iniciado sesión", login);
			return Resultado.exito.name();
		} catch (Exception e) {
			if(e instanceof ValidatorException)
				facesContext.addMessage(null, ((ValidatorException) e).getFacesMessage());
			
			else
				e.printStackTrace();
		}
		return Resultado.fracaso.name();		
	}

	@PostConstruct
	public void init() {
		sesion = (BeanSesion) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("sesion");

		if (sesion == null) {
			sesion = new BeanSesion();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("sesion", sesion);
		}
	}

}
