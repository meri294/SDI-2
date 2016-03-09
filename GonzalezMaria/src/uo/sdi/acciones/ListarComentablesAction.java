package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.Seat;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class ListarComentablesAction implements Accion {
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		SeatDao seatDao = PersistenceFactory.newSeatDao();
		List<Seat> participante = new ArrayList<Seat>();
		TripDao tripDao = PersistenceFactory.newTripDao();
		List<Trip> viajes= new ArrayList<Trip>();
		try {
			participante=seatDao.findAcceptedByUserId(user.getId());
			for(Seat seat: participante){
				Trip trip=tripDao.findById(seat.getTripId());
				if (trip.getStatus().equals(TripStatus.DONE))
					viajes.add(trip);
			}
			Log.debug("Obtenida lista de viajes comentables");
			request.setAttribute("viajes", viajes);
		} catch (Exception e) {
			request.setAttribute("mensaje",
					"Ha habido un problema procesando la lista de viajes");
			Log.error("Algo ha ocurrido obteniendo lista de viajes");
			resultado= "FRACASO";
		}
		return resultado;
	}


	@Override
	public String toString() {
		return getClass().getName();
	}

}
