package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Application;
import com.sdi.persistence.util.GenericDAO;

public interface ApplicationDao extends GenericDAO<Application, Long[]> {

	List<Application> findByUserId(Long userId);

	List<Application> findByTripId(Long tripId);

}
