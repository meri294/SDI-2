package com.sdi.business.impl;

import com.sdi.business.ApplicationService;
import com.sdi.business.LoginService;
import com.sdi.business.SeatsService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.business.UserService;

public class SimpleServicesFactory implements ServicesFactory {

	@Override
	public LoginService createLoginService() {
		return new SimpleLoginService();
	}

	@Override
	public TripService createTripService() {
		return new SimpleTripService();
	}

	@Override
	public ApplicationService createApplicationService() {
		return new SimpleApplicationService();
	}

	@Override
	public SeatsService createSeatsService() {
		return new SimpleSeatsService();
	}

	@Override
	public UserService createUserService() {
		return new SimpleUserService();
	}

}
