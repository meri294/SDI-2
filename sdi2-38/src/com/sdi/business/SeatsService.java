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
	 * Crea una plaza en estado aceptado con los datos pasados
	 * y disminuye el número de plazas disponibles en el viaje
	 * 
	 * @param userId ID del usuario
	 * @param tripId ID del viaje
	 * @throws Exception
	 */
	public void aceptarPlaza(Long userId, Long tripId) throws Exception;

}
