package com.sdi.business.impl.classes.seat;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;

public class SeatListado {

	public List<Seat> getParticipantes(Long idTrip) {
		SeatDao dao = Factories.persistence.createSeatDao();
		return dao.findByTrip(idTrip);
	}

	public List<Seat> listarSinPlaza(Long id) {
		SeatDao dao = Factories.persistence.createSeatDao();
		return dao.findSinPlaza(id);
	}

}
