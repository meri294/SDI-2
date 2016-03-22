package com.sdi.services;

import com.sdi.persistence.exception.AlreadyPersistedException;
import com.sdi.persistence.exception.PersistenceException;

public interface ReinicioBBDDService {
	
	/**
	 * Borra el contenido de todas las tablas de la base de datos.
	 */
	public void borrarContenidoTablas();
	
	/**
	 * Rellena la base de datos con el contenido minimo pedido en el enunciado.
	 * 
	 * @throws PersistenceException
	 * @throws AlreadyPersistedException
	 */
	public void rellenarTablasMinimos() throws PersistenceException, AlreadyPersistedException;

}
