package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Carpeta;
import com.sdi.model.Contacto;
import com.sdi.model.Correo;
import com.sdi.persistence.exception.NotPersistedException;

public interface CorreoDao {
	
	/**
	 * Devuelve todos los correos de la base de datos.
	 */
	List<Correo> getCorreos();
	
	/**
	 * Devuelve todos los correos enviados por el usuario cuyo 
	 * login se ha pasado.
	 * 
	 * @param login : Login del usuario del que se quiere obtener los correos.
	 * @return Devuelve una lista con los correos del usuario. Si no existe
	 * ningun usuario con el login pasado, se devuelve una lista vacia.
	 */
	List<Correo> getLoginCorreos(String login);
	
	/**
	 * Persiste el correo en la base de datos y le proporciona su id.
	 * 
	 * @param a : El correo que se quiere persistir.
	 * @return Devuelve el id del correo que se acaba de persistir.
	 */
	int save(Correo a);
	
	/**
	 * Actualiza en la base de datos los datos del correo.
	 * 
	 * @param a : El correo que se quiere actualizar.
	 */
	void update(Correo a);
	
	/**
	 * Elimina el correo de la base de datos.
	 * 
	 * @param id : El id del correo que se quiere eliminar.
	 * @throws NotPersistedException
	 */
	void delete(int id) throws NotPersistedException;
	
	/**
	 * Devuelve una lista con los correos de una carpeta de un usuario.
	 * 
	 * @param login : El login del usuario del que queremos conseguir 
	 * los correos.
	 * @param carpeta : La carpeta de la que queremos obtener los correos.
	 */
	List<Correo> getLoginCarpetaCorreos(String login, Carpeta carpeta);
	
	/**
	 * Devuelve una lista con los destinatarios del correo.
	 * 
	 * @param idCorreo : Id del correo del que se quiere obtener 
	 * los destinatarios.
	 * @return Si no existe ningun correo con el id pasado, se devuelve una
	 * lista vacia.
	 */
	List<Contacto> getDestinatariosCorreo(int idCorreo);
	
	/**
	 * Borra todos los correos de la base de datos.
	 */
	void deleteCorreos();
	
	/**
	 * Borra todos los correos del usuario.
	 * 
	 * @param login : Login del usuario del que se quiere borrar los correos.
	 */
	void deleteCorreosLogin(String login);
	
	/**
	 * Reinicia el contador de ids de la tabla Correo.
	 */
	void reiniciaID();
	
	/**
	 * Busca y devuelve el correo con el id dado.
	 * 
	 * @param idCorreo : Id del correo que se quiere obtener.
	 * @return Devuelve el correo. Si no existe ningun correo con el id pasado,
	 * se devuelve null.
	 */
	Correo getCorreo(int idCorreo);
	
	/**
	 * Borra todos los correos de la base de datos.
	 */
	void deleteAll();

}
