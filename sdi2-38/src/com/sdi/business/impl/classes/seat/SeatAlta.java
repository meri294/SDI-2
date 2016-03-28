package com.sdi.business.impl.classes.seat;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.persistence.SeatDao;

public class SeatAlta {

    public void save(Seat seat) {
	SeatDao dao = Factories.persistence.createSeatDao();
	dao.save(seat);
	
    }

}
