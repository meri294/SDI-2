package com.sdi.business;

import java.util.List;

import com.sdi.model.Application;

public interface ApplicationService {

	/**
	 * Introduce en la BBDD la solicitud pasada
	 * 
	 * @param application
	 *            La solicitud a persistir
	 * @throws Exception
	 */
	void saveApplication(Application application) throws Exception;

	/**
	 * Persiste los cambios hechos a la solicitud pasada
	 * 
	 * @param application
	 *            La solicitud a persistir
	 * @throws Exception
	 */
	void updateApplication(Application application) throws Exception;

	/**
	 * Devuelve todas las solicitudes
	 * 
	 * @return Devuelve una lista con todas las solicitudes. En caso de no haber
	 *         solicitudes devuelve una lista vacía
	 * @throws Exception
	 */
	List<Application> getApplications() throws Exception;

	/**
	 * Elimina la solicitud en la que coincida los ids.
	 * 
	 * @param ids
	 *            Array con los ids de la solicitud. Se debe pasar un array en
	 *            la que en la posición 0 esté el id del usuario y en la 1 el id
	 *            del viaje
	 * @throws Exception
	 */
	void deleteApplication(Long[] ids) throws Exception;

	/**
	 * Devuelve todas las solicitudes hechas por el usuario cuyo id se pase
	 * 
	 * @param userId
	 *            Id del usuario del que se quieren obtener las solicitudes
	 * @return Devuelve una lista con las solicitudes del usuario. Si el usuario
	 *         no ha hecho ninguna solicitud se devuelve una lista vacía
	 * @throws Exception
	 */
	List<Application> findByUserId(Long userId) throws Exception;

	/**
	 * Devuelve todas las solicitudes hechas para el viaje cuyo id se pase
	 * 
	 * @param tripId
	 *            Id del viaje del que se quieren obtener las solicitudes
	 * @return Devuelve una lista con las solicitudes para el viaje. Si no se ha
	 *         hecho ninguna solicitud se devuelve una lista vacía
	 * @throws Exception
	 */
	List<Application> findByTripId(Long tripId) throws Exception;

	/**
	 * Busca y devuelve la solicitud en la que coincidan los ids
	 * 
	 * @param ids
	 *            Array con los ids de la solicitud. Se debe pasar un array en
	 *            la que en la posición 0 esté el id del usuario y en la 1 el id
	 *            del viaje
	 * @return La solicitud en la que coinciden los ids. Si no existe ninguna se
	 *         devuelve null
	 * @throws Exception
	 */
	Application findById(Long[] ids) throws Exception;

	/**
	 * Devuelve todas las solicitudes sin plaza del viaje cuyo id es pasado
	 * 
	 * @param tripId
	 *            Id del viaje del que se quieren obtener las solicitudes sin
	 *            plaza
	 * @return Lista con las solicitudes sin plaza creada. En caso de que no
	 *         existan se devuelve una lista vacía
	 */
	List<Application> getApplicationsWithoutSeatFor(Long tripId);

	/**
	 * Elimina la solicitud cuyos ids coincidan
	 * 
	 * @param userId
	 *            Id del usuario propietario de la solicitud
	 * @param tripId
	 *            Id del viaje al que se hizo la solicitud
	 * @throws Exception
	 */
	void deleteApplication(Long userId, Long tripId) throws Exception;

}
