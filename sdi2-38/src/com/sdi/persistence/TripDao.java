package com.sdi.persistence;

import java.util.Date;
import java.util.List;

import com.sdi.model.Trip;
import com.sdi.persistence.util.GenericDAO;

public interface TripDao extends GenericDAO<Trip, Long> {

	Trip findByPromoterIdAndArrivalDate(Long id, Date arrivalDate);

	List<Trip> findByPromoterId(Long id);
}
