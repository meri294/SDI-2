package com.sdi.business.impl;

import java.util.List;

import alb.util.log.Log;

import com.sdi.business.SeatsService;
import com.sdi.business.impl.classes.seat.SeatAlta;
import com.sdi.business.impl.classes.seat.SeatBaja;
import com.sdi.business.impl.classes.seat.SeatBuscar;
import com.sdi.business.impl.classes.seat.SeatListado;
import com.sdi.business.impl.classes.seat.SeatUpdate;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;

public class SimpleSeatsService implements SeatsService {

	@Override
	public List<Seat> getParticipantes(Long idTrip) {
		return new SeatListado().getParticipantes(idTrip);
	}

	@Override
	public void save(Seat seat) {
		new SeatAlta().save(seat);
	}

	@Override
	public void aceptarPlaza(Seat seat) throws Exception {
		seat.setStatus(SeatStatus.ACCEPTED);

		update(seat);

		// Al aceptarse una plaza se disminuye el número de plazas disponibles
		// en el viaje
		Factories.services.createTripService().disminuirPlazasDisponibles(
				seat.getTripId());

		Log.debug("El usuario [%d] ha sido aceptado para el viaje [%d]",
				seat.getUserId(), seat.getTripId());

	}

	public void update(Seat seat) {
		new SeatUpdate().update(seat);
	}

	@Override
	public void aceptarPlaza(Long userId, Long tripId) throws Exception {
		Seat seat = crearPlaza(userId, tripId);
		seat.setStatus(SeatStatus.ACCEPTED);

		save(seat);

		// Al aceptarse una plaza se disminuye el número de plazas disponibles
		// en el viaje
		Factories.services.createTripService().disminuirPlazasDisponibles(
				tripId);
		
		Log.debug("El usuario [%d] ha sido aceptado para el viaje [%d]",
				userId, tripId);

	}

	@Override
	public void excluirPlaza(Long userId, Long tripId) {

		// Crear plaza
		Seat seat = crearPlaza(userId, tripId);

		// Pasarla al estado excluido
		seat.setStatus(SeatStatus.EXCLUDED);

		// Persistirla
		save(seat);

		Log.debug("El usuario [%d] ha sido excluido del viaje [%d]", userId,
				tripId);
	}

	private Seat crearPlaza(Long userId, Long tripId) {

		Seat seat = new Seat();
		seat.setTripId(tripId);
		seat.setUserId(userId);

		return seat;
	}

	@Override
	public Seat findByUserAndTrip(Long userId, Long tripId) {
		return new SeatBuscar().findByUserAndTrip(userId, tripId);
	}

	@Override
	public void delete(Seat seat) throws Exception {
		// Si el seat esta en aceptado habra que aumentarse las plazas
		// disponibles
		// en el trip tras borrarlo

		new SeatBaja().delete(seat);

		if (seat.getStatus().equals(SeatStatus.ACCEPTED))
			Factories.services.createTripService().aumentarPlazasDisponibles(
					seat.getTripId());

	}

	@Override
	public void cancelarPlaza(Long userId, Long tripId) {
		Seat seat = crearPlaza(userId, tripId);

		seat.setStatus(SeatStatus.CANCELLED);

		save(seat);

		Log.debug(
				"Se ha cancelado la plaza del usuario [%d] para el viaje [%d]",
				userId, tripId);

	}

	@Override
	public void cancelarPlaza(Seat seat) throws Exception {
		// Si el seat esta en aceptado habra que aumentarse las plazas
		// disponibles
		// en el trip tras cancelarlo
		boolean aumentarPlazas = seat.getStatus().equals(SeatStatus.ACCEPTED);

		seat.setStatus(SeatStatus.CANCELLED);

		update(seat);

		Log.debug(
				"Se ha cancelado la plaza del usuario [%d] para el viaje [%d]",
				seat.getUserId(), seat.getTripId());

		if (aumentarPlazas)
			Factories.services.createTripService().aumentarPlazasDisponibles(
					seat.getTripId());
	}

	@Override
	public void crearSinPlaza(Long userId, Long tripId) {
		Seat seat = crearPlaza(userId, tripId);

		seat.setStatus(SeatStatus.SIN_PLAZA);

		save(seat);

		Log.debug("El usuario [%d] se ha quedado sin plaza para el viaje [%d]",
				seat.getUserId(), seat.getTripId());

	}

	@Override
	public void excluirPlaza(Seat seat) throws Exception {
		// Si el seat esta en aceptado habra que aumentarse las plazas
		// disponibles
		// en el trip tras excluirlo
		boolean aumentarPlazas = seat.getStatus().equals(SeatStatus.ACCEPTED);

		seat.setStatus(SeatStatus.EXCLUDED);

		update(seat);

		Log.debug("El usuario [%d] ha sido excluido del viaje [%d]",
				seat.getUserId(), seat.getTripId());

		if (aumentarPlazas)
			Factories.services.createTripService().aumentarPlazasDisponibles(
					seat.getTripId());

	}

	@Override
	public List<Seat> obtenerSinPLaza(Long id) {
		return new SeatListado().listarSinPlaza(id);
	}

}
