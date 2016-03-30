package com.sdi.business;

import java.util.List;

import com.sdi.model.Seat;

public interface SeatsService {

	public List<Seat> getParticipantes(Long idTrip);
	
	public void save(Seat seat);
	
	/**
	 * Pasa el estado de la plaza a aceptado, persiste el cambio
	 * y disminuye el número de plazas disponibles en el viaje
	 * 
	 * @param seat Plaza que va a ser aceptada
	 * @throws Exception 
	 */
	public void aceptarPlaza(Seat seat) throws Exception;
	
	/**
	 * Crea y persiste una plaza en estado aceptado con los datos pasados
	 * y disminuye el número de plazas disponibles en el viaje
	 * 
	 * @param userId ID del usuario
	 * @param tripId ID del viaje
	 * @throws Exception
	 */
	public void aceptarPlaza(Long userId, Long tripId) throws Exception;

	/**
	 * Crea y persiste una plaza en estado excluido con los datos pasados
	 * 
	 * @param userId ID del usuario
	 * @param tripId ID del viaje
	 */
	public void excluirPlaza(Long userId, Long tripId);
	
	public Seat findByUserAndTrip(Long userId, Long tripId);

	/**
	 * Elimina la plaza.
	 * Si estaba en estado aceptado, aumenta las plazas disponibles en el trip
	 * 
	 * @param seat Plaza a eliminar
	 * @throws Exception
	 */
	public void delete(Seat seat) throws Exception;

	/**
	 * Crea y persiste una plaza en estado cancelado con los datos pasados
	 * 
	 * @param userId ID del usuario
	 * @param tripId ID del viaje
	 */
	public void cancelarPlaza(Long userId, Long tripId);

	/**
	 * Cancela la plaza.
	 * Si estaba en estado aceptado, aumenta las plazas disponibles en el trip
	 * 
	 * @param seat Plaza a cancelar
	 * @throws Exception
	 */
	public void cancelarPlaza(Seat seat) throws Exception;

	public void crearSinPlaza(Long userId, Long tripId);

}
