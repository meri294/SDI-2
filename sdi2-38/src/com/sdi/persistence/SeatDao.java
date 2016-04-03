package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Seat;
import com.sdi.persistence.util.GenericDAO;

public interface SeatDao extends GenericDAO<Seat, Long[]> {

	Seat findByUserAndTrip(Long userId, Long tripId);

	List<Seat> findByTrip(Long tripId);

	List<Seat> findByUserId(Long id);

	int deleteSinPlaza(Long id);

	List<Seat> findAcceptedByUserId(Long userId);

	List<Seat> findSinPlaza(Long id);
}
