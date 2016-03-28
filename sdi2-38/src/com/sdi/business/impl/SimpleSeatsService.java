package com.sdi.business.impl;

import java.util.List;

import com.sdi.business.SeatsService;
import com.sdi.business.impl.classes.seat.SeatAlta;
import com.sdi.business.impl.classes.seat.SeatListado;
import com.sdi.model.Seat;

public class SimpleSeatsService implements SeatsService {

	@Override
	public List<Seat> getParticipantes(Long idTrip) {
		return new SeatListado().getParticipantes(idTrip);
	}

	@Override
	public void save(Seat seat) {
	    new SeatAlta().save(seat);
	    
	}

}
