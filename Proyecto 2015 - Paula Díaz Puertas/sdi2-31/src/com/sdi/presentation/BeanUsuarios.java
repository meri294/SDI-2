package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;

@ManagedBean(name = "usuarios")
@SessionScoped
public class BeanUsuarios implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Reinicia la lista de usuarios segun qué tipo de usuario pasa a tener 
	 * la sesion. Si el usuario es un administrador, se refresca la lista de 
	 * usuarios. Si no, deja la lista de usuarios vacia.
	 * 
	 * @param u : Usuario en control de la sesion.
	 */
	public void reiniciar(Usuario u) {
		if(u == null || u.getRol() == null)
			usuarios = new ArrayList<Usuario>();
		else {
			switch(u.getRol()) {
				case administrador : recuperarUsuarios();
					break;
				case admin2 : recuperarClientes();
					break;
				default : usuarios = new ArrayList<Usuario>();
			}
		}
		/*
		if(u == null || (!u.getRol().equals(Rol.administrador) && u.getRol().equals(Rol.admin2)))
			usuarios = new ArrayList<Usuario>();
		else if(u.getRol().equals(Rol.administrador)){
			recuperarUsuarios();
		}
		*/
	}
	
	private void recuperarClientes() {
		usuarios = Factories.persistence.createUsuarioDao()
				.getClientesInactiveFirst();
		Log.debug("Obtenida lista de clientes conteniendo [%d] usuarios", 
				usuarios.size());
	}

	/**
	 * Actualiza la lista de usuarios.
	 */
	private void recuperarUsuarios() {
		usuarios = Factories.persistence.createUsuarioDao()
				.getUsuariosInactiveFirst();
		Log.debug("Obtenida lista de usuarios conteniendo [%d] usuarios", 
				usuarios.size());
	}
	
	/**
	 * Cambia el valor de activo del usuario a true.
	 * 
	 * @param u : Usuario a activar.
	 * @return Devuelve el resultado de la accion.
	 */
	public String activar(Usuario u) {
		u.setActivo(true);
		return cambiarActivo(u, "activar");
		
	}
	
	/**
	 * Cambia el valor de activo del usuario a false.
	 * 
	 * @param u : Usuario a desactivar.
	 * @return Devuelve el resultado de la accion.
	 */
	public String desactivar(Usuario u) {
		u.setActivo(false);
		return cambiarActivo(u, "desactivar");
	}
	
	/**
	 * Persiste el usuario modificado en la base de datos y actualiza
	 * la lista de usuarios.
	 * 
	 * @param u : Usuario a persistir.
	 * @param accion : String de la accion que se acaba de ejecutar (activar o 
	 * desactivar). Necesario para el mensaje del log.
	 * @return Devuelve el resultado de la accion.
	 */
	private String cambiarActivo(Usuario u, String accion) {

		try {
			Factories.persistence.createUsuarioDao().update(u);
			Log.debug("Se ha conseguido %s la cuenta del usuario [%s]", 
					accion, u.getLogin());
		} catch (Exception e) {
			Log.error("Algo ha ocurrido al intentar %s la cuenta "
					+ "del usuario [%s]", accion, u.getLogin());
			return Resultado.error.name();
		}
		//Hay que volver a generar la lista de usuarios
		recuperarUsuarios();
		
		return Resultado.exito.name();
		
	}
	
	/**
	 * Refresca la lista de usuarios.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String refrescar() {
		recuperarUsuarios();
		return Resultado.exito.name();
	}
	
	public String activar2(Usuario u) {
		u.setActivo(true);
		return cambiarActivo2(u, "activar");
		
	}
	
	public String desactivar2(Usuario u) {
		u.setActivo(false);
		return cambiarActivo2(u, "desactivar");
	}

	private String cambiarActivo2(Usuario u, String accion) {
		try {
			Factories.persistence.createUsuarioDao().update(u);
			Log.debug("Se ha conseguido %s la cuenta del usuario [%s]", 
					accion, u.getLogin());
		} catch (Exception e) {
			Log.error("Algo ha ocurrido al intentar %s la cuenta "
					+ "del usuario [%s]", accion, u.getLogin());
			return Resultado.error.name();
		}
		//Hay que volver a generar la lista de clientes
		recuperarClientes();
		
		return Resultado.exito.name();
	}

}
