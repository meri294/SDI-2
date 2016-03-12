package com.sdi.persistence;

import com.sdi.persistence.impl.ApplicationJdbcDAO;
import com.sdi.persistence.impl.RatingJdbcDAO;
import com.sdi.persistence.impl.SeatJdbcDAO;
import com.sdi.persistence.impl.TransactionJdbcImpl;
import com.sdi.persistence.impl.TripJdbcDAO;
import com.sdi.persistence.impl.UserJdbcDAO;

public class PersistenceFactory {

	public static Transaction createTransaction() {
		return new TransactionJdbcImpl();
	}

	public static RatingDao createRatingDao() {
		return new RatingJdbcDAO();
	}

	public static UserDao createUserDao() {
		return new UserJdbcDAO();
	}

	public static TripDao createTripDao() {
		return new TripJdbcDAO();
	}

	public static SeatDao createSeatDao() {
		return new SeatJdbcDAO();
	}

	public static ApplicationDao createApplicationDao() {
		return new ApplicationJdbcDAO();
	}
}
