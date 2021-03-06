package com.sdi.presentation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rol;
import com.sdi.model.Usuario;

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

	/**
	 * Comprueba que el login y contraseña coinciden con el de algun usuario.
	 * Si es asi, le mete en sesion.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String validar() {

		Usuario usuario = sesion.getUsuario();

		if (usuario != null) {
			Log.info("Se ha intentado iniciar sesión como [%s] "
					+ "teniendo la sesión iniciada como [%s]",
					login, usuario.getNombre());
			sesion.cerrar();
			return Resultado.error.name();
		}
		// El service debe devolver un usuario si se ha validado correctamente
		// o null si no.

		usuario = Factories.services.createLoginService().validar(login, pass);
		if (usuario != null) {
			sesion.setUsuario(usuario);
			Log.info("El usuario [%s] ha iniciado sesión", login);
			if(usuario.getRol().equals(Rol.admin2))
				return Rol.admin2.name();
			return Resultado.exito.name();
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
