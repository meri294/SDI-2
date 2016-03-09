package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Seat;
import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import alb.util.log.Log;

public class MostrarInfoCompletaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		Long id = Long.valueOf(request.getParameter("id"));
		Trip viaje;
		User promoter;
		List<User> participantes;

		try {
			viaje = PersistenceFactory.newTripDao().findById(id);
			promoter = PersistenceFactory.newUserDao().findById(
					viaje.getPromoterId());
			List<Seat> seats = PersistenceFactory.newSeatDao().findByTrip(id);
			participantes = obtenerParticipantes(seats);
			request.setAttribute("promotor", promoter);
			request.setAttribute("participantes", participantes);
			Log.debug("Obtenidos datos del viaje [%d]", id);
		} catch (Exception e) {
			request.setAttribute("mensaje", "Ha habido un problema procesando "
					+ "el viaje seleccionado");
			Log.error("Algo ha ocurrido obteniendo el viaje seleccionado");
			return "FRACASO";
		}
		return "EXITO";
	}

	private List<User> obtenerParticipantes(List<Seat> seats) {
		List<User> users = new ArrayList<User>();
		for (Seat seat : seats) {
			users.add(PersistenceFactory.newUserDao()
					.findById(seat.getUserId()));
		}
		return users;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
