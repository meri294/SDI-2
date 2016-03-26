package com.sdi.business;

import java.util.List;

import com.sdi.model.Application;

public interface ApplicationService {

	void saveApplication(Application application) throws Exception;

	void updateApplication(Application application) throws Exception;

	List<Application> getApplications() throws Exception;

	void deleteApplication(Long[] ids) throws Exception;

	List<Application> findByUserId (Long userId) throws Exception;
	
	List<Application> findByTripId (Long tripId) throws Exception;
	
	Application findById(Long[] ids) throws Exception;

}
