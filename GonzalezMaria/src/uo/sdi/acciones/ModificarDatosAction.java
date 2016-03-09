package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.User;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class ModificarDatosAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		String nuevoEmail = request.getParameter("email");
		String nuevoNombre = request.getParameter("name");
		String nuevosApellidos = request.getParameter("surname");
		String antiguaCont = request.getParameter("oldPassword");
		String nuevaCont = request.getParameter("newPassword");
		String repitCont = request.getParameter("repPassword");
		HttpSession session = request.getSession();
		User usuario = ((User) session.getAttribute("user"));
		String correctoPassword = comprobarContraseñas(antiguaCont, nuevaCont,
				repitCont, usuario);

		if (correctoPassword == null) {
			usuario.setEmail(nuevoEmail);
			usuario.setName(nuevoNombre);
			usuario.setSurname(nuevosApellidos);
			try {
				UserDao dao = PersistenceFactory.newUserDao();
				dao.update(usuario);
				request.setAttribute("mensaje",
						"Datos modificados correctamente");
				Log.debug("Modificados los datos del usuario [%s]",
						usuario.getLogin());
			} catch (Exception e) {
				Log.error("Algo ha ocurrido actualizando el email de [%s]",
						usuario.getLogin());
				request.setAttribute("mensaje",
						"No se han podido actualizar los datos");
				return "FRACASO";
			}
			return "EXITO";
		} else {
			Log.error(correctoPassword);
			request.setAttribute("mensaje", correctoPassword);
			return "FRACASO";
		}
	}

	private String comprobarContraseñas(String antiguaCont, String nuevaCont,
			String repitCont, User user) {
		if (antiguaCont.equals(user.getPassword())) {
			if (nuevaCont.equals(repitCont))
				return null;
			else
				return "Las contraseñas nuevas no coinciden";
		} else
			return "La contraseña actual no es correcta";

	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
