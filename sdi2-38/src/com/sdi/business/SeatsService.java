package com.sdi.business;

import java.util.List;

import com.sdi.model.Seat;

public interface SeatsService {

	public List<Seat> getParticipantes(Long idTrip);

}
