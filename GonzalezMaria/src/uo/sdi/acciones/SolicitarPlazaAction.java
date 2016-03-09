package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uo.sdi.model.Application;
import uo.sdi.model.User;
import uo.sdi.persistence.ApplicationDao;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.TripDao;
import alb.util.log.Log;

public class SolicitarPlazaAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String resultado = "EXITO";
		User user = (User) request.getSession().getAttribute("user");
		Long idViaje = Long.valueOf(request.getParameter("id"));
		ApplicationDao appDao = PersistenceFactory.newApplicationDao();
		TripDao tripDao = PersistenceFactory.newTripDao();
		try {
			if (tripDao.findById(idViaje).getPromoterId().equals(user.getId()))
			{
				resultado = "FRACASO";
				Log.error("El solicitante de la plaza es el promotor");
				request.setAttribute("mensaje", "No puede solicitar la plaza, "
						+ "es el promotor del viaje");

			} else {
				Application app = new Application();
				app.setTripId(idViaje);
				app.setUserId(user.getId());
				appDao.save(app);
				Log.debug("Creada solicitud de plaza para usuario [%d] "
						+ "en viaje [%d]", user.getId(), idViaje);
				request.setAttribute("mensaje", "Su reserva de plaza se ha "
						+ "realizado correctamente");
				new ListarInvolucradoAction().execute(request, response);
			}
		} catch (Exception e) {
			appDao.delete(new Long[] { user.getId(), idViaje });
			Log.error("Se ha producido un error realizando la solicitud");
			request.setAttribute("mensaje", "No se ha podido realizar "
					+ "la solicitud");
		}

		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
