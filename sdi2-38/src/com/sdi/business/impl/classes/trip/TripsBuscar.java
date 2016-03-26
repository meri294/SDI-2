package com.sdi.business.impl.classes.trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.persistence.SeatDao;
import com.sdi.persistence.TripDao;

public class TripsBuscar {

	private List<Trip> viajesPromocionados = new ArrayList<Trip>();
	private List<Trip> viajesExcluido = new ArrayList<Trip>();
	private List<Trip> viajesParticipante = new ArrayList<Trip>();
	private List<Trip> viajesSolicitados = new ArrayList<Trip>();
	private List<Trip> viajesSinPlaza = new ArrayList<Trip>();

	public Trip find(Long id) throws Exception {
		TripDao dao = Factories.persistence.createTripDao();
		Trip trip = dao.findById(id);
		if (trip == null) {
			throw new Exception("No se ha encontrado el viaje");
		}

		return trip;
	}

	public Map<String, List<Trip>> findInvolucrado(Long id) {
		Map<String, List<Trip>> involucrados = new HashMap<String, List<Trip>>();
		TripDao tripDao = Factories.persistence.createTripDao();
		viajesPromocionados = tripDao.findByPromoterId(id);
		obtenerViajesInteresado(id, tripDao);
		obtenerViajesEnEspera(id);
		involucrados.put("promotor", viajesPromocionados);
		involucrados.put("enEspera", viajesSolicitados);
		involucrados.put("participante", viajesParticipante);
		involucrados.put("excluido", viajesExcluido);
		involucrados.put("sinPlaza", viajesSinPlaza);
		return involucrados;
	}

	private void obtenerViajesInteresado(Long id, TripDao tripDao) {
		SeatDao seatDao = Factories.persistence.createSeatDao();
		List<Seat> appls = seatDao.findByUserId(id);
		for (Seat seat : appls) {
			Trip t = tripDao.findById(seat.getTripId());
			if (t != null && !esPromotor(t.getPromoterId())) {
				if (seat.getStatus() == SeatStatus.ACCEPTED)
					viajesParticipante.add(t);
				else if (seat.getStatus() == SeatStatus.EXCLUDED)
					viajesExcluido.add(t);
				else
					viajesSinPlaza.add(t);
			}
		}
	}

	private void obtenerViajesEnEspera(Long id) {
		SeatDao seatDao = Factories.persistence.createSeatDao();
		TripDao tripDao = Factories.persistence.createTripDao();
		List<Application> solicitudes = Factories.persistence
				.createApplicationDao().findByUserId(id);
		for (Application app : solicitudes) {
			if (seatDao.findByUserAndTrip(id, app.getTripId()) == null)
				viajesSolicitados.add(tripDao.findById(app.getTripId()));
		}
	}

	private boolean esPromotor(Long id) {
		for (Trip t : viajesPromocionados) {
			if (t.getPromoterId().equals(id))
				return true;
		}
		return false;
	}

}
