package com.sdi.business;

import java.util.List;

import com.sdi.model.Seat;

public interface SeatsService {

	/**
	 * Busca y devuelve a todos los participantes de un viaje, o sea, aquellas
	 * plazas que estén en estado aceptado
	 * 
	 * @param idTrip
	 *            Id del viaje del que se quieren obtener los participantes
	 * @return Listado con todos los participantes. Si el viaje no tiene
	 *         participantes se devuelve una lista vacía
	 */
	public List<Seat> getParticipantes(Long idTrip);

	/**
	 * Introduce en la BBDD la plaza pasada
	 * 
	 * @param seat
	 *            La plaza a persistir
	 */
	public void save(Seat seat);

	/**
	 * Pasa el estado de la plaza a aceptado, persiste el cambio y disminuye el
	 * número de plazas disponibles en el viaje
	 * 
	 * @param seat
	 *            Plaza que va a ser aceptada
	 * @throws Exception
	 */
	public void aceptarPlaza(Seat seat) throws Exception;

	/**
	 * Crea y persiste una plaza en estado aceptado con los datos pasados y
	 * disminuye el número de plazas disponibles en el viaje
	 * 
	 * @param userId
	 *            ID del usuario
	 * @param tripId
	 *            ID del viaje
	 * @throws Exception
	 */
	public void aceptarPlaza(Long userId, Long tripId) throws Exception;

	/**
	 * Crea y persiste una plaza en estado excluido con los datos pasados
	 * 
	 * @param userId
	 *            ID del usuario
	 * @param tripId
	 *            ID del viaje
	 */
	public void excluirPlaza(Long userId, Long tripId);

	/**
	 * Busca y devuelve una plaza con el id de usuario y de viaje pasados
	 * 
	 * @param userId
	 *            Id del usuario propietario de la plaza
	 * @param tripId
	 *            Id del viaje al que se refiere la plaza
	 * @return La plaza apropiada. Si no existe ninguna plaza con los ids
	 *         apropiados se devuelve null
	 */
	public Seat findByUserAndTrip(Long userId, Long tripId);

	/**
	 * Elimina la plaza. Si estaba en estado aceptado, aumenta las plazas
	 * disponibles en el trip
	 * 
	 * @param seat
	 *            Plaza a eliminar
	 * @throws Exception
	 */
	public void delete(Seat seat) throws Exception;

	/**
	 * Crea y persiste una plaza en estado cancelado con los datos pasados
	 * 
	 * @param userId
	 *            ID del usuario
	 * @param tripId
	 *            ID del viaje
	 */
	public void cancelarPlaza(Long userId, Long tripId);

	/**
	 * Cancela la plaza. Si estaba en estado aceptado, aumenta las plazas
	 * disponibles en el trip
	 * 
	 * @param seat
	 *            Plaza a cancelar
	 * @throws Exception
	 */
	public void cancelarPlaza(Seat seat) throws Exception;

	/**
	 * Crea una plaza en estado sin plaza a partir de los ids pasados
	 * 
	 * @param userId
	 *            Id del usuario propietario de la plaza
	 * @param tripId
	 *            Id del viaje al que se refiere la plaza
	 */
	public void crearSinPlaza(Long userId, Long tripId);

	/**
	 * Pasa la plaza a estado excluido. Si la plaza estaba en estado aceptado,
	 * aumenta las plazas disponibles del viaje al que pertenece
	 * 
	 * @param seat
	 *            Plaza a excluir
	 * @throws Exception
	 */
	public void excluirPlaza(Seat seat) throws Exception;

	public List<Seat> obtenerSinPLaza(Long id);

}
