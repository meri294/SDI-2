package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.Seat;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import alb.util.log.Log;

public class AbrirComentariosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado="EXITO";
		HttpSession session=request.getSession();
		Long idUser=((User)session.getAttribute("user")).getId();
		Long idTrip = Long.valueOf(request.getParameter("id"));
		List<User> participantes;

		try {
			List<Seat> seats = PersistenceFactory.newSeatDao()
					.findByTrip(idTrip);
			participantes = obtenerParticipantes(seats, idUser);
			request.setAttribute("participantes", participantes);
			request.setAttribute("viaje", idTrip);
		} catch (Exception e) {
			request.setAttribute("mensaje", "Ha habido un problema procesando "
					+ "el viaje seleccionado");
			Log.error("Algo ha ocurrido obteniendo el viaje seleccionado");
			new ListarComentablesAction().execute(request, response);
			resultado = "FRACASO";
		}
		return resultado;
	}

	private List<User> obtenerParticipantes(List<Seat> seats, Long idUser) {
		List<User> users = new ArrayList<User>();
		for (Seat seat : seats) {
			User user=PersistenceFactory.newUserDao()
					.findById(seat.getUserId());
			if(!user.getId().equals(idUser))
				users.add(user);
		}
		return users;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
