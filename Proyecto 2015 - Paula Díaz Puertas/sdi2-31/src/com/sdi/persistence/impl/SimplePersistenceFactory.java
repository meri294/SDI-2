package com.sdi.persistence.impl;

import com.sdi.persistence.ContactoDao;
import com.sdi.persistence.CorreoDao;
import com.sdi.persistence.PersistenceFactory;
import com.sdi.persistence.UsuarioDao;

/**
 * Implementaci??????n de la factoria que devuelve implementaci??????n de la capa
 * de persistencia con Jdbc 
 * 
 * @author Enrique
 *
 */
public class SimplePersistenceFactory implements PersistenceFactory {

	@Override
	public UsuarioDao createUsuarioDao() {
		return new UsuarioJdbcDao();
	}

	@Override
	public CorreoDao createCorreoDao() {
		return new CorreoJdbcDao();
	}
	
	@Override
	public ContactoDao createContactoDao() {
		return new ContactoJdbcDao();
	}
	

}
