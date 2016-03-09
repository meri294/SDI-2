package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Trip;
import uo.sdi.persistence.PersistenceFactory;
import alb.util.log.Log;

public class MostrarViajeAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		Long id = Long.valueOf(request.getParameter("id"));
		Trip viaje;

		try {
			viaje = PersistenceFactory.newTripDao().findById(id);
			request.setAttribute("viaje", viaje);
			Log.debug("Obtenido viaje con identificador [%d]", id);
		} catch (Exception e) {
			request.setAttribute("mensaje",
					"Ha habido un problema procesando el viaje seleccionado");
			Log.error("Algo ha ocurrido obteniendo el viaje seleccionado");
			return "FRACASO";
		}
		return "EXITO";
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
