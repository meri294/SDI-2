package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.SeatsService;
import com.sdi.business.impl.classes.seat.SeatAlta;
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

    }

    public void update(Seat seat) {
	new SeatUpdate().update(seat);
    }

    @Override
    public void aceptarPlaza(Long userId, Long tripId) throws Exception {
	Seat seat = new Seat();
	seat.setTripId(tripId);
	seat.setUserId(userId);
	seat.setStatus(SeatStatus.ACCEPTED);

	save(seat);

	// Al aceptarse una plaza se disminuye el número de plazas disponibles
	// en el viaje
	Factories.services.createTripService().disminuirPlazasDisponibles(
		tripId);

    }

}
