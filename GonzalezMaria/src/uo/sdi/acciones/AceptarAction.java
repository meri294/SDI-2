package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class AceptarAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		Long idUser=Long.valueOf(request.getParameter("user"));
		Long idTrip=Long.valueOf(request.getParameter("id"));
		SeatDao seatDao=PersistenceFactory.newSeatDao();
		TripDao tripDao=PersistenceFactory.newTripDao();
		try{
			Trip trip=tripDao.findById(idTrip);
			if(trip.getAvailablePax()==0){
				resultado="FRACASO";
				Log.error("No quedan plazas disponibles");
				request.setAttribute("mensaje", "No quedan plazas disponibles");
			}
			else{
				Seat seat= new Seat();
				seat.setStatus(SeatStatus.ACCEPTED);
				seat.setTripId(idTrip);
				seat.setUserId(idUser);
				seatDao.save(seat);
				trip.setAvailablePax(trip.getAvailablePax()-1);
				if(trip.getAvailablePax()==0)
					activarSinPlaza(idTrip);
				tripDao.update(trip);
				request.setAttribute("mensaje", "Usuario "+idUser+" aceptado"
						+ " con éxito");
				Log.debug("El usuario se ha aceptado correctamente");
			}
		}catch(Exception e){
			resultado="FRACASO";
			Log.error("Ha habido un error procesando la solicitud");
			request.setAttribute("mensaje", "No se ha podido aceptar"
					+ " al usuario");
		}finally{
			new ListarSolicitantesAction().execute(request, response);
		}
		return resultado;
	}

	private void activarSinPlaza(Long idTrip) {
		List<Application> solicitudes=PersistenceFactory.newApplicationDao()
				.findByTripId(idTrip);
		SeatDao seatDao= PersistenceFactory.newSeatDao();
		for(Application app: solicitudes){
			Seat found=seatDao.findByUserAndTrip(app.getUserId(), 
					app.getTripId());
			if(found==null){
				Seat sinPlaza= new Seat();
				sinPlaza.setStatus(SeatStatus.SIN_PLAZA);
				sinPlaza.setTripId(idTrip);
				sinPlaza.setUserId(app.getUserId());
				seatDao.save(sinPlaza);
			}
		}
	}

}
