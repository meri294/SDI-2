package com.sdi.business.impl;

import java.util.List;
import java.util.Map;

import alb.util.log.Log;

import com.sdi.business.ApplicationService;
import com.sdi.business.SeatsService;
import com.sdi.business.TripService;
import com.sdi.business.impl.classes.trip.TripsAlta;
import com.sdi.business.impl.classes.trip.TripsBaja;
import com.sdi.business.impl.classes.trip.TripsBuscar;
import com.sdi.business.impl.classes.trip.TripsListado;
import com.sdi.business.impl.classes.trip.TripsUpdate;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.TripStatus;

public class SimpleTripService implements TripService {

	@Override
	public List<Trip> getTrips() throws Exception {
		return new TripsListado().getTrips();
	}

	@Override
	public void saveTrip(Trip trip) {
		new TripsAlta().save(trip);
	}

	@Override
	public void updateTrip(Trip trip) throws Exception {
		new TripsUpdate().update(trip);
	}

	@Override
	public void deleteTrip(Long id) throws Exception {
		new TripsBaja().delete(id);
	}

	@Override
	public Trip findById(Long id) throws Exception {
		return new TripsBuscar().find(id);
	}

	@Override
	public Map<String, List<Trip>> findInvolucrado(Long id) throws Exception {
		return new TripsBuscar().findInvolucrado(id);
	}

	@Override
	public void disminuirPlazasDisponibles(Long id) throws Exception {
		disminuirPlazasDisponibles(this.findById(id));

	}

	@Override
	public void disminuirPlazasDisponibles(Trip trip) throws Exception {

		int nuevasPlazasDisponibles = trip.getAvailablePax() - 1;

		if (nuevasPlazasDisponibles < 0)
			throw new Exception("No hay plazas disponibles para disminuir");

		trip.setAvailablePax(nuevasPlazasDisponibles);

		updateTrip(trip);

		Log.info(
				"Se ha disminuido en una las plazas disponibles del viaje [%d]",
				trip.getId());

	}

	@Override
	public void aumentarPlazasDisponibles(Long id) throws Exception {
		aumentarPlazasDisponibles(this.findById(id));

	}

	@Override
	public void aumentarPlazasDisponibles(Trip trip) throws Exception {
		SeatsService service = Factories.services.createSeatsService();
		int nuevasPlazasDisponibles = trip.getAvailablePax() + 1;

		if (nuevasPlazasDisponibles > trip.getMaxPax())
			throw new Exception(
					"Ya están todas las plazas libres, no puede haber más "
					+ "plazas libres que plazas máximas");

		trip.setAvailablePax(nuevasPlazasDisponibles);
		List<Seat> sinPlaza=service.obtenerSinPLaza(trip.getId());
		for (Seat seat: sinPlaza)
			service.delete(seat);

		updateTrip(trip);

		Log.info(
				"Se ha aumentado en una las plazas disponibles del viaje [%d]",
				trip.getId());

	}

	@Override
	public void cancelar(Trip trip) throws Exception {

		if (!trip.getStatus().equals(TripStatus.OPEN)) {
			throw new Exception("No se puede cancelar este viaje");
		}

		trip.setStatus(TripStatus.CANCELLED);

		updateTrip(trip);

		Log.info("Se ha cancelado el viaje [%d]", trip.getId());

		ApplicationService appService = Factories.services
				.createApplicationService();
		SeatsService sService = Factories.services.createSeatsService();

		List<Seat> seats = sService.getParticipantes(trip.getId());

		for (Seat s : seats)
			sService.cancelarPlaza(s);

		List<Application> applications = appService.findByTripId(trip.getId());

		for (Application app : applications) {

			Seat seat = sService.findByUserAndTrip(app.getUserId(),
					app.getTripId());

			if (seat == null)
				sService.cancelarPlaza(app.getUserId(), app.getTripId());

			else
				sService.cancelarPlaza(seat);

			appService.deleteApplication(app.getUserId(), app.getTripId());
		}

	}

}
