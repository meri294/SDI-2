package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.User;
import alb.util.log.Log;

public class CerrarSesionAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String usuario = ((User) session.getAttribute("user")).getName();
		request.setAttribute("mensaje", "El usuario " + usuario
				+ " ha cerrado sesión");
		Log.info("El usuario %s ha cerrado sesión", usuario);
		session.invalidate();
		return "EXITO";
	}

}
