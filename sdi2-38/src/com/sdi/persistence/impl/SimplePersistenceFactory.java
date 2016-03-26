package com.sdi.persistence.impl;

import com.sdi.persistence.ApplicationDao;
import com.sdi.persistence.PersistenceFactory;
import com.sdi.persistence.RatingDao;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.Transaction;
import com.sdi.persistence.TripDao;
import com.sdi.persistence.UserDao;

public class SimplePersistenceFactory implements PersistenceFactory{

	public Transaction createTransaction() {
		return new TransactionJdbcImpl();
	}

	public RatingDao createRatingDao() {
		return new RatingJdbcDAO();
	}

	public UserDao createUserDao() {
		return new UserJdbcDAO();
	}

	public TripDao createTripDao() {
		return new TripJdbcDAO();
	}

	public SeatDao createSeatDao() {
		return new SeatJdbcDAO();
	}

	public ApplicationDao createApplicationDao() {
		return new ApplicationJdbcDAO();
	}
}
