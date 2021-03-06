package com.sdi.services.impl;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Contacto;
import com.sdi.model.Rol;
import com.sdi.model.Usuario;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;
import com.sdi.services.ReinicioBBDDService;

public class SimpleReinicioBBDDService implements ReinicioBBDDService {

	@Override
	public void borrarContenidoTablas() {
		Factories.persistence.createContactoDao().deleteAll();
		Factories.persistence.createCorreoDao().deleteAll();
		Factories.persistence.createUsuarioDao().deleteAll();
		Log.debug("Se ha borrado el contenido de todas las tablas de la base de datos");
	}

	@Override
	public void rellenarTablasMinimos() throws PersistenceException, AlreadyPersistedException {
		// Debe crearse:
		// Un administrador con al menos un contacto
		// 3 usuarios, todos con al menos un contacto MENOS EL PRIMERO
		Usuario administrador = crearUsuario("admin", Rol.administrador);
		Contacto conPublico = crearContacto("Publico", administrador);

		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		for(int i = 1; i < 4; i++)
			usuarios.add(crearUsuario("usuario" + i, Rol.cliente));
		
		List<Contacto> contactos = new ArrayList<Contacto>();
		
		for(int i = 1; i < usuarios.size(); i++)
			contactos.add(crearContacto(i + "", usuarios.get(i)));
		
		usuarios.add(administrador);
		contactos.add(conPublico);
		
		for(Usuario u: usuarios)
			Factories.persistence.createUsuarioDao().save(u);

		Log.debug("Se han creado la cuenta de administrador y "
				+ "%d cuentas de usuario", usuarios.size()-1);
		
		for(Contacto c: contactos)
			Factories.persistence.createContactoDao().save(c);
			
		Log.debug("Se han creado %d contactos", contactos.size());

	}

	/**
	 * Crea un contacto en la agenda privada del usuario pasado, con el valor
	 * de todos sus atributos iguales (excepto el correo)
	 * 
	 * @param indice : String que se utilizara para darle un nombre distintivo
	 * a cada contacto.
	 * @param usuario : Usuario a cuya agenda privada pertenece.
	 * @return Devuelve el contacto resultante.
	 */
	private Contacto crearContacto(String indice, Usuario usuario) {
		Contacto contacto = new Contacto();
		String nombre = "contacto" + indice;
		contacto.setEmail(nombre + "@micorreo.es");
		contacto.setNombre(nombre);
		contacto.setApellidos(nombre);
		contacto.setDireccion(nombre);
		contacto.setCiudad(nombre);
		contacto.setAgenda_Usuario(usuario.getLogin());
		return contacto;
	}

	/**
	 * Crea un usuario activo, con el valor de todos sus atributos iguales
	 * (excepto el correo).
	 * 
	 * @param login : Login del usuario a crear.
	 * @param rol : Rol que tendrá.
	 * @return Devuelve el usuario resultante.
	 */
	private Usuario crearUsuario(String login, Rol rol) {

		Usuario usuario = new Usuario();
		usuario.setLogin(login);
		usuario.setNombre(login);
		usuario.setApellidos(login);
		usuario.setPasswd(login);
		usuario.setEmail(login + "@micorreo.es");
		usuario.setActivo(true);
		usuario.setRol(rol);
		return usuario;
	}

}
