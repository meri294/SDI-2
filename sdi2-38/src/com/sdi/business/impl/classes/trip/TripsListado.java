package com.sdi.business.impl.classes.trip;

import java.util.ArrayList;
import java.util.List;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;
import com.sdi.persistence.TripDao;
import com.sdi.util.MariaDateUtil;

public class TripsListado {

	public List<Trip> getTrips() throws Exception {
		TripDao dao = Factories.persistence.createTripDao();
		actualizarViajes(dao);
		return buscarDisponibles(dao.findAll());
	}

	private List<Trip> buscarDisponibles(List<Trip> trips) {
		List<Trip> activos = new ArrayList<Trip>();
		for (Trip trip : trips) {
			if (trip.getStatus().equals(TripStatus.OPEN)
					&& trip.getAvailablePax() > 0)
				activos.add(trip);
		}
		return activos;
	}

	private void actualizarViajes(TripDao dao) {
		List<Trip> viajes = dao.findAll();
		try {
			for (Trip trip : viajes) {
				if (!trip.getStatus().equals(TripStatus.CANCELLED)) {
					TripStatus nuevoStatus = calcularNuevoEstado(trip);

					if (nuevoStatus != trip.getStatus()) {
						trip.setStatus(nuevoStatus);

						dao.update(trip);

						if (nuevoStatus.equals(TripStatus.CLOSED)) {
							// Crear seats sin plaza a partir de las
							// applicaciones pendientes

							Log.debug(
									"Se ha cerrado el plazo para modificar el viaje [%d]",
									trip.getId());

							List<Application> apps = Factories.services
									.createApplicationService()
									.getApplicationsWithoutSeatFor(trip.getId());

							for (Application app : apps)
								Factories.services.createSeatsService()
										.crearSinPlaza(app.getUserId(),
												app.getTripId());

						}
					}
				}
			}
		} catch (Exception e) {
			Log.error("No se ha podido actualizar la lista de viajes");
		}
	}

	private TripStatus calcularNuevoEstado(Trip trip) {

		if (MariaDateUtil.isAfter(MariaDateUtil.now(), trip.getClosingDate())) {
			if (MariaDateUtil.isAfter(MariaDateUtil.now(),
					trip.getDepartureDate()))
				return TripStatus.DONE;

			return TripStatus.CLOSED;
		}

		return TripStatus.OPEN;
	}
}
