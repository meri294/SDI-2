package com.sdi.business.impl.classes.seat;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;

public class SeatBuscar {

    public Seat findByUserAndTrip(Long userId, Long tripId) {
	SeatDao dao = Factories.persistence.createSeatDao();
	return dao.findByUserAndTrip(userId, tripId);
    }

}
