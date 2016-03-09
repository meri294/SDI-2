package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class CancelarViajeAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		Long id = Long.valueOf(request.getParameter("id"));
		TripDao tripDao = PersistenceFactory.newTripDao();
		Trip trip;
		try {
			trip = tripDao.findById(id);
			if (trip.getStatus().equals(TripStatus.DONE)) {
				resultado = "FRACASO";
				Log.error("No se puede cancelar, ya se ha realizado el viaje");
				request.setAttribute("mensaje",
						"No se puede realizar la cancelación, el viaje ya "
								+ "se ha realizado");
			} else {
				trip.setStatus(TripStatus.CANCELLED);
				tripDao.update(trip);
				request.setAttribute("mensaje", "La cancelación del viaje "
						+ "se ha realizado correctamente");
			}
		} catch (Exception e) {
			resultado = "FRACASO";
			Log.error("Se ha producido un error al cancelar el viaje [%d]", id);
			request.setAttribute("mensaje", "Se ha producido un error "
					+ "cancelando el viaje");
		} finally {
			new ListarInvolucradoAction().execute(request, response);
		}

		return resultado;
	}

}
