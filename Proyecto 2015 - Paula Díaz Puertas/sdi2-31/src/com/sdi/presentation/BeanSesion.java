package com.sdi.presentation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rol;
import com.sdi.model.Usuario;

@ManagedBean(name = "sesion")
@SessionScoped
public class BeanSesion implements Serializable {

	private Usuario usuario;
	
	private boolean admin = false;

	@ManagedProperty(value = "#{contactos}")
	private BeanContactos contactos;
	@ManagedProperty(value = "#{correos}")
	private BeanCorreos correos;
	@ManagedProperty(value = "#{usuarios}")
	private BeanUsuarios usuarios;

	private static final long serialVersionUID = 1L;
	
	public BeanSesion() {
		init();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		if(usuario == null || !usuario.getRol().equals(Rol.administrador))
			admin = false;
		else
			admin = true;
		contactos.reiniciar(usuario);
		correos.reiniciar(usuario);
		usuarios.reiniciar(usuario);
	}

	public BeanContactos getContactos() {
		return contactos;
	}

	public void setContactos(BeanContactos contactos) {
		this.contactos = contactos;
	}

	public BeanCorreos getCorreos() {
		return correos;
	}

	public void setCorreos(BeanCorreos correos) {
		this.correos = correos;
	}

	public BeanUsuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(BeanUsuarios usuarios) {
		this.usuarios = usuarios;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * Cierra la sesion.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String cerrar() {

		setUsuario(null);

		return "cerrar";
	}

	public boolean esAdmin() {
		if (usuario.getRol().equals(Rol.administrador))
			return true;
		return false;
	}

	/**
	 * Comprueba que el usuario en sesion es administrador. Si lo es, reinicia
	 * la base de datos y la rellena con los datos minimos pedidos en el
	 * enunciado. Cierra la sesion.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String reiniciarBBDD() {
		if (usuario == null || !esAdmin())
			return Resultado.error.name();

		Log.debug("Se comienza el reinicio de la base de datos, "
				+ "provocada por el usuario [%s]", usuario.getLogin());
		try {
			Factories.services.createReinicioBBDDService()
			.borrarContenidoTablas();
			Factories.services.createReinicioBBDDService()
			.rellenarTablasMinimos();
		} catch (Exception e) {
			Log.error("Ha ocurrido un error intentando reiniciar "
					+ "la base de datos: %s", e.getMessage());
			return Resultado.error.name();
		}
		Log.debug("Se ha finalizado el reinicio de la base de datos");
		cerrar();
		return Resultado.exito.name();
	}

	@PostConstruct
	public void init() {
		contactos = (BeanContactos) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("contactos");

		if (contactos == null) {
			contactos = new BeanContactos();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("contactos", contactos);
		}
		
		correos = (BeanCorreos) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("correos");

		if (correos == null) {
			correos = new BeanCorreos();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("correos", correos);
		}
		
		usuarios = (BeanUsuarios) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("usuarios");

		if (usuarios == null) {
			usuarios = new BeanUsuarios();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("usuarios", usuarios);
		}
	}

}
