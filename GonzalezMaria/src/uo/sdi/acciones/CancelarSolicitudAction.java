package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class CancelarSolicitudAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		SeatDao seatDao = PersistenceFactory.newSeatDao();
		TripDao tripDao = PersistenceFactory.newTripDao();
		ApplicationDao appDao = PersistenceFactory.newApplicationDao();
		Long[] ids = { Long.valueOf(request.getParameter("user")),
				Long.valueOf(request.getParameter("viaje")) };
		try {
			Trip trip = tripDao.findById(ids[1]);
			Seat seat = seatDao.findByUserAndTrip(ids[0], ids[1]);
			if (!(trip.getPromoterId().equals(ids[0]))) {
				if (posibleCancelacionPorFecha(trip)) {
					seatDao.delete(ids);
					appDao.delete(ids);
					if (seat!=null && seat.getStatus().
							equals(SeatStatus.ACCEPTED))
						actualizarViaje(trip, seatDao, tripDao);
					Log.debug(
							"Cancelada solicitud del usuario [%d] al "
							+ "viaje [%d]",	ids[0], ids[1]);
				} else {
					resultado = "FRACASO";
					Log.error("No se puede cancelar, ya se ha realizado "
							+ "el viaje");
					request.setAttribute("mensaje",
							"No se puede realizar la cancelación, el viaje ya "
									+ "se ha realizado");
				}
			} else {
				resultado = "FRACASO";
				Log.error("No se puede cancelar,el usuario es promotor");
				request.setAttribute("mensaje",
						"No se puede realizar la cancelación, usted es "
								+ "el promotor");
			}
		} catch (Exception e) {
			resultado = "FRACASO";
			Log.debug("Se ha producido un error durante la cancelación del "
					+ "usuario [%d] al viaje [%d]", ids[0], ids[1]);
			request.setAttribute("mensaje", "No se ha podido cancelar "
					+ "su viaje");
		} finally {
			new ListarInvolucradoAction().execute(request, response);
		}

		return resultado;
	}

	private void actualizarViaje(Trip trip, SeatDao seatDao, TripDao tripDao) {
		trip.setAvailablePax(trip.getAvailablePax() + 1);
		seatDao.deleteSinPlaza(trip.getId());
		tripDao.update(trip);
	}

	private boolean posibleCancelacionPorFecha(Trip trip) {
		return trip.getStatus().equals(TripStatus.OPEN);
	}

}
