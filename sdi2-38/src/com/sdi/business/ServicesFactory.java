package com.sdi.business;

public interface ServicesFactory {
	
	public LoginService createLoginService();
	
	public TripService createTripService();

	public ApplicationService createApplicationService();

	public SeatsService createSeatsService();

	public UserService createUserService();

}
