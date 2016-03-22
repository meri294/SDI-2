package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Usuario;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public interface UsuarioDao {

	/**
	 * Devuelve todos los usuarios de la base de datos.
	 * 
	 * @throws PersistenceException
	 */
	List<Usuario> getUsuarios() throws PersistenceException;
	
	/**
	 * Persiste el usuario en la base de datos.
	 * 
	 * @param a : El usuario que se quiere persistir.
	 * @throws AlreadyPersistedException
	 * @throws PersistenceException
	 */
	void save(Usuario a) throws AlreadyPersistedException, PersistenceException;
	
	/**
	 * Actualiza en la base de datos los datos del usuario.
	 * 
	 * @param a : El usuario que se quiere actualizar.
	 * @throws NotPersistedException
	 * @throws PersistenceException
	 */
	void update(Usuario a) throws NotPersistedException, PersistenceException;
	
	/**
	 * Elimina al usuario de la base de datos.
	 * 
	 * @param login : El login del usuario que se quiere eliminar.
	 * @throws NotPersistedException
	 * @throws PersistenceException
	 */
	void delete(String login) throws NotPersistedException, PersistenceException;
	
	/**
	 * Busca y devuelve el usuario con el login pasado.
	 * 
	 * @param login : El login del usuario que se quiere obtener.
	 * @return El usuario si existe. Si no existe ningun usuario con el login
	 * pasado, se devuelve null.
	 * @throws PersistenceException
	 */
	Usuario findByLogin(String login) throws PersistenceException;
	
	/**
	 * Borra todos los usuarios de la base de datos.
	 */
	void deleteUsuarios();
	
	/**
	 * Devuelve todos los usuarios de la base de datos, ordenados por activo.
	 * Devuelve a los usuarios inactivos primero.
	 */
	List<Usuario> getUsuariosInactiveFirst();
	
	/**
	 * Devuelve el valor del activo del usuario.
	 * 
	 * @param login : Login del usuario del que se quiere comprobar el activo.
	 * @return El activo del usuario. Si se pasa un login de un usuario 
	 * inexistente se devuelve false.
	 */
	boolean getActivoByLogin(String login);
	
	/**
	 * Borra todos los usuarios de la base de datos.
	 */
	void deleteAll();

	List<Usuario> getClientesInactiveFirst();

}