package uo.sdi.acciones;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.User;
import uo.sdi.model.UserStatus;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.UserDao;
import alb.util.log.Log;

public class RegistrarseAction implements Accion {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		String resultado = "EXITO";
		String identificador = request.getParameter("idUsuario");
		String nombreUsuario = request.getParameter("nombreUsuario");
		String apellido = request.getParameter("apellidosUsuario");
		String email = request.getParameter("emailUsuario");
		String password = request.getParameter("passwordUsuario");
		String repPassword = request.getParameter("repetedPassword");
		UserDao userDao = PersistenceFactory.newUserDao();
		if (userDao.findByLogin(identificador) == null) {
			if (password.trim().length() != 0 
					&& password.trim().length() != 0) {
				if (password.equals(repPassword)) {
					try {
						userDao.save(createUser(identificador, nombreUsuario,
								apellido, email, password));
						request.setAttribute("mensaje", "Usuario "
								+ identificador + " creado correctamente");
					} catch (Exception e) {
						resultado = "FRACASO";
						request.setAttribute("mensaje",
								"Se ha producido un error al crear el usuario");
					}
				} else {
					session.invalidate();
					Log.info("Las contraseñas no coinciden");
					request.setAttribute("mensaje",
							"Las contraseñas proporcionadas no coinciden");
					resultado = "FRACASO";
				}
			} else {
				session.invalidate();
				Log.info("Contraseñas vacías");
				request.setAttribute("mensaje",
						"Los campos de contraseña son obligatorios");
				resultado = "FRACASO";
			}
		}else{
			session.invalidate();
			Log.info("Ya existe un usuario [%s] en la base de datos",
					nombreUsuario);
			request.setAttribute("mensaje",
					"Nombre de usuario ya existente");
			resultado = "FRACASO";
		}

		return resultado;
	}

	private User createUser(String identificador, String nombreUsuario,
			String apellido, String email, String password) {
		User newUser = new User();
		newUser.setLogin(identificador);
		newUser.setName(nombreUsuario);
		newUser.setSurname(apellido);
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setStatus(UserStatus.ACTIVE);
		return newUser;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
