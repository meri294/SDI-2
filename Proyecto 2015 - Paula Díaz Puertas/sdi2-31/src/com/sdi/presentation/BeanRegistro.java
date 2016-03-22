package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rol;
import com.sdi.model.Usuario;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

@ManagedBean(name = "registro")
@ViewScoped
//Tiene que ser view scope porque utiliza ajax
public class BeanRegistro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String login = "";
	private String nombre = "";
	private String apellidos = "";
	private String pass = "";
	private String repitePass = "";
	
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getRepitePass() {
		return repitePass;
	}
	
	public void setRepitePass(String repitePass) {
		this.repitePass = repitePass;
	}
	
	/**
	 * Comprueba que el login elegido esta libre.
	 * Comprueba que las contraseñas introducidas coinciden.
	 * Si es asi, crea y registra un usuario de rol cliente con los datos
	 * introducidos.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String registrar() {
		
		Usuario u = Factories.persistence.createUsuarioDao().findByLogin(login);
		
		if(u != null) { //Si ya existe un usuario con ese login...
			Log.info("Se ha intentado registrar un usuario con login "
					+ "[%s] ya existente", login);
			//TODO request.setAttribute("mensaje", "El login elegido ya existe. Por favor, escoja otro.");
			return Resultado.fracaso.name();
		}
		
		if(!pass.equals(repitePass)) {
			//TODO request.setAttribute("mensaje", "Sus contraseñas no coinciden.");
			return Resultado.fracaso.name();
		}
		
		u = new Usuario();
		u.setLogin(login);
		u.setNombre(nombre);
		u.setApellidos(apellidos);
		u.setPasswd(pass);
		u.setEmail(login + "@micorreo.es");
		u.setActivo(false);
		u.setRol(Rol.cliente);
		
		try {
			Factories.persistence.createUsuarioDao().save(u);
		} catch (PersistenceException | AlreadyPersistedException e) {
			Log.error("Ha ocurrido un error intentando persistir "
					+ "al usuario [%s]: %s", login, e.getMessage());
			return Resultado.error.name();
		}
		Log.info("Se ha creado un nuevo usuario cliente con login [%s]", login);
		//TODO request.setAttribute("mensaje", "¡Se ha realizado el registro satisfactoriamente!");
		
		return Resultado.exito.name();
	}
	
}
