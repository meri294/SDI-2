package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Rating;
import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.RatingDao;
import alb.util.log.Log;

public class GuardarComentarioAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String resultado = "EXITO";
		Long idUserFrom = ((User) request.getSession().getAttribute("user"))
				.getId();
		Long idUsuarioSobre = Long.valueOf(request
				.getParameter("userComentado"));
		Long idViaje = Long.valueOf(request.getParameter("id"));
		int nota = Integer.valueOf(request.getParameter("points"));
		String comentario = request.getParameter("comment");
		if (comentario == null || comentario.trim().length() == 0) {
			resultado = "FRACASO";
			Log.error("Datos in rellenar", idUserFrom, idUsuarioSobre, idViaje);
			request.setAttribute("mensaje", "Se deben cubrir todos "
					+ "los campos");
			new AbrirComentariosAction().execute(request, response);

		} else {
			RatingDao ratDao = PersistenceFactory.newRatingDao();
			try {
				if (ratDao.findByAboutFrom(idUsuarioSobre, idViaje, idUserFrom,
						idViaje) != null) {
					resultado = "FRACASO";
					Log.error("Ya existe una valoración del usuario [%d]"
							+ " para el usuario [%d] en el viaje [%d]",
							idUserFrom, idUsuarioSobre, idViaje);
					request.setAttribute("mensaje", "Ya hay una valoración"
							+ " para ese usuario en el viaje seleccionado");
					new AbrirComentariosAction().execute(request, response);
				} else {
					Rating rating = new Rating();
					rating.setComment(comentario);
					rating.setSeatAboutTripId(idViaje);
					rating.setSeatAboutUserId(idUsuarioSobre);
					rating.setSeatFromTripId(idViaje);
					rating.setSeatFromUserId(idUserFrom);
					rating.setValue(nota);
					ratDao.save(rating);
					request.setAttribute("mensaje", "Comentario guardado"
							+ " correctamente");
				}
			} catch (Exception e) {
				resultado = "FRACASO";
				Log.error("Ha habido un error guardando el comentario",
						idUserFrom, idUsuarioSobre, idViaje);
				request.setAttribute("mensaje", "Ha habido un error guardando "
						+ "el comentario");
				new AbrirComentariosAction().execute(request, response);
			}
		}
		return resultado;
	}

}
