package com.sdi.business;

import java.util.List;
import java.util.Map;

import com.sdi.model.Trip;

public interface TripService {

	/**
	 * Introduce en la BBDD el viaje pasado
	 * 
	 * @param trip
	 *            El viaje a persistir
	 */
	void saveTrip(Trip trip);

	/**
	 * Persiste los cambios hechos al viaje pasado
	 * 
	 * @param trip
	 *            El viaje a persistir
	 * @throws Exception
	 */
	void updateTrip(Trip trip) throws Exception;

	/**
	 * Devuelve una lista con todos los viajes en estado OPEN
	 * 
	 * @return Una lista con todos los viajes en estado OPEN. Si no existen
	 *         viajes en OPEN devuelve una lista vacía
	 * @throws Exception
	 */
	List<Trip> getTrips() throws Exception;

	/**
	 * Elimina el viaje que tenga el id que se haya pasado
	 * 
	 * @param id
	 *            El id del viaje que se quiere eliminar
	 * @throws Exception
	 */
	void deleteTrip(Long id) throws Exception;

	/**
	 * Busca y devuelve un viaje con el id que se haya pasado
	 * 
	 * @param id
	 *            Id del viaje que se quiere obtener
	 * @return El viaje con el id pasado. Devuelve null si no existe ningún
	 *         viaje con ese id
	 * @throws Exception
	 */
	Trip findById(Long id) throws Exception;

	/**
	 * Devuelve una lista con todos los viajes en los que está involucrado el
	 * usuario del id que se ha pasado
	 * 
	 * @param id
	 *            Id del usuario del que se quieren obtener los viajes en los
	 *            que está involucrado
	 * @return Los viajes en los que está involucrado
	 * @throws Exception
	 */
	Map<String, List<Trip>> findInvolucrado(Long id) throws Exception;

	/**
	 * Disminuye y persiste el número de plazas disponibles en el viaje cuyo id
	 * se ha pasado
	 * 
	 * @param id
	 *            Id del viaje en el que se quieren disminuir las plazas
	 *            disponibles
	 * @throws Exception
	 */
	void disminuirPlazasDisponibles(Long id) throws Exception;

	/**
	 * Disminuye y persiste el número de plazas disponibles en el viaje que se
	 * ha pasado
	 * 
	 * @param trip
	 *            Viaje en el que se quieren disminuir las plazas disponibles
	 * @throws Exception
	 */
	void disminuirPlazasDisponibles(Trip trip) throws Exception;

	/**
	 * Aumenta y persiste el número de plazas disponibles en el viaje cuyo id se
	 * ha pasado
	 * 
	 * @param id
	 *            Id del viaje en el que se quieren aumentar las plazas
	 *            disponibles
	 * @throws Exception
	 */
	void aumentarPlazasDisponibles(Long id) throws Exception;

	/**
	 * Aumenta y persiste el número de plazas disponibles en el viaje que se ha
	 * pasado
	 * 
	 * @param trip
	 *            Viaje en el que se quieren aumentar las plazas disponibles
	 * @throws Exception
	 */
	void aumentarPlazasDisponibles(Trip trip) throws Exception;

	/**
	 * Cancela el viaje pasado, y por consiguiente pasa todas las plazas en el
	 * viaje a cancelado, crea plazas canceladas a partir de las solicitudes
	 * pendientes y elimina todas las solicitudes al viaje
	 * 
	 * @param viaje
	 *            Viaje que se quiere cancelar
	 * @throws Exception
	 */
	void cancelar(Trip viaje) throws Exception;

}
