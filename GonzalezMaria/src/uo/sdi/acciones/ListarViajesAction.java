package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;
import uo.sdi.util.MariaDateUtil;
import alb.util.log.Log;

public class ListarViajesAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		List<Trip> viajes;

		try {
			actualizarViajes();
			viajes = PersistenceFactory.newTripDao().findAll();
			viajes = obtenerActivos(viajes);
			request.setAttribute("listaViajes", viajes);
			Log.debug("Obtenida lista de viajes conteniendo [%d] viajes",
					viajes.size());
		} catch (Exception e) {
			request.setAttribute("mensaje",
					"Ha habido un problema procesando la lista de viajes");
			Log.error("Algo ha ocurrido obteniendo lista de viajes");
			return "FRACASO";
		}
		return "EXITO";
	}

	private List<Trip> obtenerActivos(List<Trip> viajes) {
		List<Trip> activos = new ArrayList<Trip>();
		for (Trip trip : viajes) {
			if (trip.getStatus().equals(TripStatus.OPEN)
					&& trip.getAvailablePax() > 0)
				activos.add(trip);
		}
		return activos;
	}

	private void actualizarViajes() {
		TripDao tripDao = PersistenceFactory.newTripDao();
		List<Trip> viajes = tripDao.findAll();
		try {
			for (Trip trip : viajes) {
				if (MariaDateUtil.isAfter(MariaDateUtil.now(),
						trip.getClosingDate())) {
					if (MariaDateUtil.isAfter(MariaDateUtil.now(),
							trip.getDepartureDate()))
						trip.setStatus(TripStatus.DONE);
					else
						trip.setStatus(TripStatus.CLOSED);
				} else
					trip.setStatus(TripStatus.OPEN);
				tripDao.update(trip);
			}
		} catch (Exception e) {
			Log.error("No se ha podido actualizar la lista de viajes");
		}
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
