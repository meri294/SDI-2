package com.sdi.services;

import com.sdi.model.Usuario;

public interface CambioUsuarioService {

	/**
	 * Modifica el nombre, apellidos e email del usuario y lo persiste.
	 * 
	 * @param u : Usuario a modificar.
	 * @param nombre : Nuevo nombre.
	 * @param apellidos : Nuevos apellidos.
	 * @param email : Nuevo email.
	 * @return Devuelve el usuario modificado.
	 */
	public Usuario modificarDatos(Usuario u, String nombre, String apellidos,
			String email);
	
	/**
	 * Modifica la contraseña del usuario.
	 * 
	 * @param u : Usuario a modificar.
	 * @param password : Nueva contraseña.
	 * @return Devuelve el usuario modificado.
	 */
	public Usuario modificarPass(Usuario u, String password);

}
