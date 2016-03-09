package uo.sdi.acciones;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Rating;
import uo.sdi.persistence.PersistenceFactory;
import alb.util.log.Log;

public class InformacionUsuarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		Long id = Long.valueOf(request.getParameter("id"));
		List<Rating> valoraciones;

		try {
			valoraciones = PersistenceFactory.newRatingDao().findByAbout(id);
			request.setAttribute("valoraciones", valoraciones);
			Log.debug("Obtenidos valoraciones del usuario [%d]", id);
		} catch (Exception e) {
			request.setAttribute("mensaje", "Ha habido un problema procesando "
					+ "las valoraciones del usuario");
			Log.error("Algo ha ocurrido obteniendo las "
					+ "valoraciones del usuario");
			return "FRACASO";
		}
		return "EXITO";
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
