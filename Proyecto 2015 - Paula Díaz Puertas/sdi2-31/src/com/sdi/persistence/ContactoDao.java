package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Contacto;
import com.sdi.model.Usuario;
import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.NotPersistedException;

public interface ContactoDao {
	
	/**
	 * Devuelve todos los contactos de la base de datos.
	 */
	List<Contacto> getContactos();
	
	/**
	 * Devuelve todos los contactos que estan en la agenda privada de un usuario.
	 * 
	 * @param login : Login del usuario del que se quiere obtener los contactos.
	 * @return Devuelve una lista con los contactos del usuario cuyo login se 
	 * ha pasado.
	 * Si el usuario no existe o no tiene contactos se devuelve una lista vacia.
	 */
	List<Contacto> getLoginContactos(String login);
	
	/**
	 * Persiste el contacto en la base de datos y le proporciona su id.
	 * 
	 * @param a : El contacto que se quiere persistir.
	 * @throws AlreadyPersistedException
	 */
	void save(Contacto a) throws AlreadyPersistedException;
	
	/**
	 * Actualiza en la base de datos los datos del contacto.
	 * 
	 * @param a : El contacto que se quiere actualizar.
	 * @throws NotPersistedException
	 */
	void update(Contacto a) throws NotPersistedException;
	
	/**
	 * Elimina al contacto de la base de datos.
	 * 
	 * @param id : El id del contacto que se quiere eliminar.
	 * @throws NotPersistedException
	 */
	void delete(int id) throws NotPersistedException;
	
	/**
	 * Borra todos los contactos de la base de datos.
	 */
	void deleteContactos();
	
	/**
	 * Borra todos los contactos de la agenda publica.
	 */
	void deleteContactosAdmin();
	
	/**
	 * Devuelve una lista con los contactos de la agenda publica.
	 */
	List<Contacto> getAdminContactos();
	
	/**
	 * Reinicia el contador de ids de la tabla Contacto.
	 */
	void reiniciaID();
	
	/**
	 * Comprueba si la id pasada corresponde a un contacto que este en la agenda
	 * publica o privada del usuario pasado y lo devuelve.
	 * 
	 * @param idContacto : Id del contacto que se quiere obtener.
	 * @param usuario : Usuario del que estamos mirando la agenda publica
	 * y privada.
	 * @return Devuelve el contacto cuya id se ha pasado si pertenece a la
	 * agenda publica o privada del usuario pasado. Si no pertenece o no existe
	 * se devuelve null.
	 */
	Contacto getContacto(int idContacto, Usuario usuario);
	
	/**
	 * Borra todos los contactos de la base de datos.
	 */
	void deleteAll();
}
