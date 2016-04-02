package com.sdi.business.impl.classes.seat;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;

public class SeatBaja {

	public void delete(Seat seat) {
		SeatDao dao = Factories.persistence.createSeatDao();

		Long[] ids = { seat.getUserId(), seat.getTripId() };
		dao.delete(ids);
	}

}
