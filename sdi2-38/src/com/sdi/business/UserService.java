package com.sdi.business;

import com.sdi.model.User;

public interface UserService {

	/**
	 * Busca y devuelve a un usuario por su id
	 * 
	 * @param idPromoter
	 *            El id del usuario a buscar
	 * @return Devuelve el usuario o null en caso de que no exista ningún
	 *         usuario con ese id
	 */
	User findById(Long idPromoter);

}
