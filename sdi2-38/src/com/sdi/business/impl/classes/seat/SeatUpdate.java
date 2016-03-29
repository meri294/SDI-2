package com.sdi.business.impl.classes.seat;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;

public class SeatUpdate {

    public void update(Seat seat) {
	SeatDao dao = Factories.persistence.createSeatDao();
	dao.update(seat);
    }

}
