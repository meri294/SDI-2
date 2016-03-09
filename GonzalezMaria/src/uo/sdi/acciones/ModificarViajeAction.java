package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import alb.util.log.Log;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.persistence.PersistenceFactory;

public class ModificarViajeAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado="EXITO";
		Long idViaje=Long.valueOf(request.getParameter("id"));
		try{
			Trip viaje=PersistenceFactory.newTripDao().findById(idViaje);
			if(viaje.getStatus()==TripStatus.CANCELLED
					&& viaje.getStatus()!=TripStatus.DONE){
				request.setAttribute("viaje", viaje);
				Log.debug("Obtenido viaje [%d]", idViaje);
			}
			else{
				resultado="FRACASO";
				Log.error("El viaje ha sido cancelado o ya se ha realizado");
				request.setAttribute("mensaje", "El viaje ha sido cancelado "
						+ "o ya se ha realizado");
				new ListarInvolucradoAction().execute(request, response);
			}
		}catch(Exception e){
			resultado="FRACASO";
			Log.error("Ha habido un error obteniendo el viaje [%]",idViaje);
			request.setAttribute("mensaje", "Ha pcurrido un error obteniendo"
					+ "los datos del viaje");
			new ListarInvolucradoAction().execute(request, response);
		}
		return resultado;
	}

}
