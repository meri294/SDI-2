package com.sdi.persistence;

public interface PersistenceFactory {

	public Transaction createTransaction();

	public RatingDao createRatingDao();

	public UserDao createUserDao();

	public TripDao createTripDao();

	public SeatDao createSeatDao();

	public ApplicationDao createApplicationDao();
}
