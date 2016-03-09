package uo.sdi.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;
import uo.sdi.acciones.*;

public class Controlador extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, Map<String, Accion>> mapaDeAcciones; // <rol, <opcion,
																// objeto
																// Accion>>
	private Map<String, Map<String, Map<String, String>>> mapaDeNavegacion; 
	/*<rol,<opcion<resultadoJSP>>>*/

	public void init() throws ServletException {
		crearMapaAcciones();
		crearMapaDeJSP();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		String opcion, resultado, jspSiguiente;
		Accion accion;
		String rolAntes, rolDespues;

		try {
			opcion = req.getServletPath().replace("/", "");

			rolAntes = obtenerRolDeSesion(req);

			accion = buscarAccionParaOpcion(rolAntes, opcion);

			resultado = accion.execute(req, res);

			rolDespues = obtenerRolDeSesion(req);

			jspSiguiente = buscarJSPSegun(rolDespues, opcion, resultado);

			req.setAttribute("jspSiguiente", jspSiguiente);

		} catch (Exception e) {

			req.getSession().invalidate();

			Log.error("Se ha producido alguna excepción no manejada [%s]", e);

			req.setAttribute("mensaje",
					"No tiene permisos para acceder al recurso solicitado");

			jspSiguiente = "/login.jsp";
		}

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher(jspSiguiente);
		dispatcher.forward(req, res);
	}

	private String obtenerRolDeSesion(HttpServletRequest req) {
		HttpSession sesion = req.getSession();
		if (sesion.getAttribute("user") == null)
			return "PUBLICO";
		else
			return "REGISTRADO";
	}

	// Obtiene un objeto accion en funci�n de la opci�n
	// enviada desde el navegador
	private Accion buscarAccionParaOpcion(String rol, String opcion) {

		Accion accion = mapaDeAcciones.get(rol).get(opcion);
		Log.debug("Elegida acción [%s] para opción [%s] y rol [%s]", accion,
				opcion, rol);
		return accion;
	}

	// Obtiene la p�gina JSP a la que habr� que entregar el
	// control el funci�n de la opci�n enviada desde el navegador
	// y el resultado de la ejecuci�n de la acci�n asociada
	private String buscarJSPSegun(String rol, String opcion, String resultado) {

		String jspSiguiente = mapaDeNavegacion.get(rol).get(opcion)
				.get(resultado);
		Log.debug(
				"Elegida página siguiente [%s] para el resultado [%s] "
				+ "tras realizar [%s] con rol [%s]",
				jspSiguiente, resultado, opcion, rol);
		return jspSiguiente;
	}

	private void crearMapaAcciones() {

		mapaDeAcciones = new HashMap<String, Map<String, Accion>>();

		Map<String, Accion> mapaPublico = new HashMap<String, Accion>();
		mapaPublico.put("validarse", new ValidarseAction());
		mapaPublico.put("listarViajes", new ListarViajesAction());
		// Registrarse en la aplicación
		mapaPublico.put("registrarse", new RegistrarseAction());
		// Abrir ventana de registro
		mapaPublico.put("abrirRegistro", new AbrirAction());
		mapaPublico.put("mostrarViaje", new MostrarViajeAction());
		mapaPublico.put("irAInicio", new AbrirAction());
		mapaDeAcciones.put("PUBLICO", mapaPublico);

		Map<String, Accion> mapaRegistrado = new HashMap<String, Accion>();
		mapaRegistrado.put("modificarDatos", new ModificarDatosAction());
		mapaRegistrado.put("cerrarSesion", new CerrarSesionAction());
		mapaRegistrado.put("abrirModificacion", new AbrirAction());
		mapaRegistrado.put("listarViajes", new ListarViajesAction());
		mapaRegistrado.put("mostrarViaje", new MostrarViajeAction());
		mapaRegistrado.put("masInfo", new MostrarInfoCompletaAction());
		mapaRegistrado.put("infoUsuario", new InformacionUsuarioAction());
		mapaRegistrado.put("solicitarPlaza", new SolicitarPlazaAction());
		mapaRegistrado.put("registrarViaje", new AbrirAction());
		mapaRegistrado.put("guardarViaje", new AlmacenarViajeAction());
		mapaRegistrado.put("misViajes", new ListarInvolucradoAction());
		mapaRegistrado.put("cancelarSolicitud", new CancelarSolicitudAction());
		mapaRegistrado.put("cancelarViaje", new CancelarViajeAction());
		mapaRegistrado.put("modificarViaje", new ModificarViajeAction());
		mapaRegistrado.put("guardarViajeModificado", new GuardarModifViajeAction());
		mapaRegistrado.put("verSolicitantes", new ListarSolicitantesAction());
		mapaRegistrado.put("aceptar", new AceptarAction());
		mapaRegistrado.put("excluir", new ExluirAction());
		mapaRegistrado.put("irAInicio", new AbrirAction());
		mapaRegistrado.put("viajesAComentar", new ListarComentablesAction());
		mapaRegistrado.put("comentar", new AbrirComentariosAction());
		mapaRegistrado.put("guardarComentario", new GuardarComentarioAction());
		mapaDeAcciones.put("REGISTRADO", mapaRegistrado);
	}

	private void crearMapaDeJSP() {

		mapaDeNavegacion = new HashMap<String, 
				Map<String, Map<String, String>>>();

		// Crear mapas auxiliares vacíos
		Map<String, Map<String, String>> opcionResJSP = 
				new HashMap<String, Map<String, String>>();
		Map<String, String> resJSP = new HashMap<String, String>();

		// Mapa de navegación del público
		resJSP.put("FRACASO", "/login.jsp");
		opcionResJSP.put("validarse", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/listaViajes.jsp");
		resJSP.put("FRACASO", "/login.jsp");
		opcionResJSP.put("listarViajes", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/login.jsp");
		resJSP.put("FRACASO", "/registro.jsp");
		opcionResJSP.put("registrarse", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/registro.jsp");
		opcionResJSP.put("abrirRegistro", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/login.jsp");
		opcionResJSP.put("cerrarSesion", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/muestraViaje.jsp");
		resJSP.put("FRACASO", "/login.jsp");
		opcionResJSP.put("mostrarViaje", resJSP);
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/login.jsp");
		opcionResJSP.put("irAInicio", resJSP);
		mapaDeNavegacion.put("PUBLICO", opcionResJSP);

		// Crear mapas auxiliares vacíos
		opcionResJSP = new HashMap<String, Map<String, String>>();
		resJSP = new HashMap<String, String>();

		// Mapa de navegación de usuarios registrados
		resJSP.put("EXITO", "/principalRegistrado.jsp");
		opcionResJSP.put("validarse", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/principalRegistrado.jsp");
		resJSP.put("FRACASO", "/modificar.jsp");
		opcionResJSP.put("modificarDatos", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/modificar.jsp");
		opcionResJSP.put("abrirModificacion", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/listaViajes.jsp");
		resJSP.put("FRACASO", "/principalRegistrado.jsp");
		opcionResJSP.put("listarViajes", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/muestraViaje.jsp");
		resJSP.put("FRACASO", "/principalRegistrado.jsp");
		opcionResJSP.put("mostrarViaje", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/infoCompleta.jsp");
		resJSP.put("FRACASO", "/muestraViaje.jsp");
		opcionResJSP.put("masInfo", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/infoUsuario.jsp");
		resJSP.put("FRACASO", "/infoCompleta.jsp");
		opcionResJSP.put("infoUsuario", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/viajesInvolucrado.jsp");
		resJSP.put("FRACASO", "/principalRegistrado.jsp");
		opcionResJSP.put("solicitarPlaza", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/registroViaje.jsp");
		opcionResJSP.put("registrarViaje", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/viajesInvolucrado.jsp");
		resJSP.put("FRACASO", "/registroViaje.jsp");
		opcionResJSP.put("guardarViaje", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/viajesInvolucrado.jsp");
		resJSP.put("FRACASO", "/principalRegistrado.jsp");
		opcionResJSP.put("misViajes", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/viajesInvolucrado.jsp");
		resJSP.put("FRACASO", "/viajesInvolucrado.jsp");
		opcionResJSP.put("cancelarSolicitud", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/viajesInvolucrado.jsp");
		resJSP.put("FRACASO", "/viajesInvolucrado.jsp");
		opcionResJSP.put("cancelarViaje", resJSP);
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO", "/modificarViaje.jsp");
		resJSP.put("FRACASO", "/viajesInvolucrado.jsp");
		opcionResJSP.put("modificarViaje", resJSP);
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO", "/viajesInvolucrado.jsp");
		resJSP.put("FRACASO", "/modificarViaje.jsp");
		opcionResJSP.put("guardarViajeModificado", resJSP);
		resJSP = new HashMap<String, String>();
		resJSP.put("EXITO", "/listarSolicitantes.jsp");
		resJSP.put("FRACASO", "/viajesInvolucrado.jsp");
		opcionResJSP.put("verSolicitantes", resJSP);
		resJSP= new HashMap<String, String>();
		resJSP.put("EXITO", "/listarSolicitantes.jsp");
		resJSP.put("FRACASO", "/listarSolicitantes.jsp");
		opcionResJSP.put("aceptar", resJSP);
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO", "/listarSolicitantes.jsp");
		resJSP.put("FRACASO", "/listarSolicitantes.jsp");
		opcionResJSP.put("excluir", resJSP);
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principalRegistrado.jsp");
		opcionResJSP.put("irAInicio", resJSP);
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO", "/listaComentables.jsp");
		resJSP.put("FRACASO", "/principalRegistrado.jsp");
		opcionResJSP.put("viajesAComentar", resJSP);
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO", "/comentarViaje.jsp");
		resJSP.put("FRACASO", "/listaComentables.jsp");
		opcionResJSP.put("comentar", resJSP);
		resJSP=new HashMap<String, String>();
		resJSP.put("EXITO","/principalRegistrado.jsp");
		resJSP.put("FRACASO","/comentarViaje.jsp");
		opcionResJSP.put("guardarComentario", resJSP);
		mapaDeNavegacion.put("REGISTRADO", opcionResJSP);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		doGet(req, res);
	}

}