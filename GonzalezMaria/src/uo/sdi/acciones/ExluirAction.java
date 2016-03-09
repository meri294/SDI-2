package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class ExluirAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		Long idUser = Long.valueOf(request.getParameter("user"));
		Long idTrip = Long.valueOf(request.getParameter("id"));
		SeatDao seatDao = PersistenceFactory.newSeatDao();
		TripDao tripDao = PersistenceFactory.newTripDao();
		try {
			Trip trip = tripDao.findById(idTrip);
			if (!trip.getStatus().equals(TripStatus.OPEN)) {
				Log.error("El periodo de inscripción ha terminado");
				request.setAttribute("mensaje", "No se puede excluir tras"
						+ " el cierre del periodo de inscripción");
				resultado = "FRACASO";
			} else {
				Seat seat = new Seat();
				seat.setStatus(SeatStatus.EXCLUDED);
				seat.setTripId(idTrip);
				seat.setUserId(idUser);
				seatDao.save(seat);
				request.setAttribute("mensaje", "Exclusión realizada "
						+ "correctamente");
			}
		} catch (Exception e) {
			Log.error("Se ha producido un error procesando la exclusión");
			request.setAttribute("mensaje", "Ha habido un error "
					+ "procesando la exclusión");
			resultado = "FRACASO";
		}
		return resultado;
	}

}
