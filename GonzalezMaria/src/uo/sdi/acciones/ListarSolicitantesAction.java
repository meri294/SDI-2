package uo.sdi.acciones;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Application;
import uo.sdi.model.Seat;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class ListarSolicitantesAction implements Accion {
	
	private List<User> pendiente;

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		pendiente=new ArrayList<User>();
		Long idViaje=Long.valueOf(request.getParameter("id"));
		TripDao tripDao=PersistenceFactory.newTripDao();
		ApplicationDao appDao=PersistenceFactory.newApplicationDao();
		try{
			Trip trip=tripDao.findById(idViaje);
			if(!(trip.getStatus().equals(TripStatus.DONE)||
					trip.getStatus().equals(TripStatus.CANCELLED))){
				List<Application> apps=appDao.findByTripId(idViaje);
				obtnerEstados(apps);
				request.setAttribute("viaje", trip);
				request.setAttribute("pendiente", pendiente);
				
			}else{
				resultado="FRACASO";
				Log.error("El viaje ya se ha realizado");
				request.setAttribute("mensaje", "No se permiten"
						+ " modificaciones en las solicitudes en viajes "
						+ "cancelados o que ya se han realizado.");
			}
		}catch(Exception e){
			resultado="FRACASO";
			Log.error("Ha ocurrido un error procesando la lista "
					+ "de solicitudes");
			request.setAttribute("mensaje", "Ha ocurrido un error "
					+ "procesando la lista de solicitudes");
		}
		return resultado;
	}

	private void obtnerEstados(List<Application> apps) {
		SeatDao seatDao= PersistenceFactory.newSeatDao();
		UserDao userDao=PersistenceFactory.newUserDao();
		for(Application app: apps){
			Seat found=seatDao.findByUserAndTrip(app.getUserId(), 
					app.getTripId());
			if(found==null){
				pendiente.add(userDao.findById(app.getUserId()));
			}
		}
		
	}
}
