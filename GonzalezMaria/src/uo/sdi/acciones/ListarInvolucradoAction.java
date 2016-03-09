package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class ListarInvolucradoAction implements Accion {
	private List<Trip> viajesPromocionados;
	private List<Trip> viajesExcluido;
	private List<Trip> viajesParticipante;
	private List<Trip> viajesSolicitados;
	private List<Trip> viajesSinPlaza;
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		viajesPromocionados = new ArrayList<Trip>();
		viajesSolicitados = new ArrayList<Trip>();
		viajesExcluido = new ArrayList<Trip>();
		viajesParticipante = new ArrayList<Trip>();
		viajesSinPlaza = new ArrayList<Trip>();
		TripDao tripDao = PersistenceFactory.newTripDao();

		try {
			viajesPromocionados = tripDao.findByPromoterId(user.getId());
			obtenerViajesInteresado(user.getId(),tripDao);
			obtenerViajesEnEspera(user.getId());
			request.setAttribute("promocionados", viajesPromocionados);
			request.setAttribute("enEspera", viajesSolicitados);
			request.setAttribute("participante", viajesParticipante);
			request.setAttribute("excluido",viajesExcluido);
			request.setAttribute("sinPlaza", viajesSinPlaza);
		} catch (Exception e) {
			request.setAttribute("mensaje",
					"Ha habido un problema procesando la lista de viajes");
			Log.error("Algo ha ocurrido obteniendo lista de viajes");
			resultado = "FRACASO";
		}
		return resultado;
	}

	private void obtenerViajesInteresado(Long id, TripDao tripDao) {
		SeatDao seatDao = PersistenceFactory.newSeatDao();
		List<Seat> appls = seatDao.findByUserId(id);
		for (Seat seat : appls) {
			Trip t = tripDao.findById(seat.getTripId());
			if (t != null && !esPromotor(t.getPromoterId())){
				if(seat.getStatus()==SeatStatus.ACCEPTED)
					viajesParticipante.add(t);
				else if(seat.getStatus()==SeatStatus.EXCLUDED)
					viajesExcluido.add(t);
				else viajesSinPlaza.add(t);
			}
		}
	}

	private void obtenerViajesEnEspera(Long id) {	
		SeatDao seatDao=PersistenceFactory.newSeatDao();
		TripDao tripDao=PersistenceFactory.newTripDao();
		List<Application> solicitudes=PersistenceFactory.newApplicationDao().
				findByUserId(id);
		for (Application app: solicitudes){
			if(seatDao.findByUserAndTrip(id, app.getTripId())==null)
				viajesSolicitados.add(tripDao.findById(app.getTripId()));
		}
	}
	
	private boolean esPromotor(Long id){
		for (Trip t: viajesPromocionados){
			if(t.getPromoterId().equals(id))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
