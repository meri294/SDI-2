package uo.sdi.acciones;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uo.sdi.model.AddressPoint;
import uo.sdi.model.Seat;
import uo.sdi.model.SeatStatus;
import uo.sdi.model.Trip;
import uo.sdi.model.TripStatus;
import uo.sdi.model.User;
import uo.sdi.model.Waypoint;
import uo.sdi.persistence.PersistenceFactory;
import uo.sdi.persistence.SeatDao;
import uo.sdi.persistence.TripDao;
import uo.sdi.util.MariaDateUtil;
import alb.util.log.Log;

public class AlmacenarViajeAction implements Accion {

	private final static String LAS = "Fecha de llegada anterior a salida";
	private final static String SAC = "Fecha de salida anterior a cierre";
	private final static String OK = "Fechas correctas";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		String resultado = "EXITO";
		Long userId = ((User) session.getAttribute("user")).getId();
		AddressPoint salida = crearAddress(
				request.getParameter("departureAddress"),
				request.getParameter("departureCity"),
				request.getParameter("departureState"),
				request.getParameter("departureCountry"),
				request.getParameter("departureCP"),
				request.getParameter("departureLatitude"),
				request.getParameter("departureLatitude"));
		AddressPoint llegada = crearAddress(
				request.getParameter("arrivalAddress"),
				request.getParameter("arrivalCity"),
				request.getParameter("arrivalState"),
				request.getParameter("arrivalCountry"),
				request.getParameter("arrivalCP"),
				request.getParameter("arrivalLatitude"),
				request.getParameter("arrivalLatitude"));
		Date fechaSalida = MariaDateUtil.completeFromString(
				request.getParameter("departureDate"),
				request.getParameter("departureHour"));
		Date fechaLlegada = MariaDateUtil.completeFromString(
				request.getParameter("arrivalDate"),
				request.getParameter("arrivalHour"));
		Date fechaCierre = MariaDateUtil.completeFromString(
				request.getParameter("closingDate"),
				request.getParameter("closingHour"));
		int maxPax = Integer.valueOf(request.getParameter("maxPas"));
		double estimatedCost = Double.valueOf(request
				.getParameter("estimatedCost"));
		String comentarios = request.getParameter("comments");
		String isFechaValida = fechasValidas(fechaSalida, fechaLlegada,
				fechaCierre);
		if (!isFechaValida.equals(OK)) {
			resultado = "FRACASO";
			request.setAttribute("mensaje", isFechaValida);
			Log.error(isFechaValida);
		} else {
			Trip viaje = crearViaje(salida, llegada, fechaSalida, fechaLlegada,
					fechaCierre, maxPax, estimatedCost, comentarios, userId);
			try {
				TripDao tripDao = PersistenceFactory.newTripDao();
				tripDao.save(viaje);
				Log.debug("Viaje registrado correctamente");
				Trip creado = tripDao.findByPromoterIdAndArrivalDate(userId,
						fechaLlegada);
				SeatDao seatDao = PersistenceFactory.newSeatDao();
				seatDao.save(crearSeat(userId, creado.getId()));
				new ListarInvolucradoAction().execute(request, response);
			} catch (Exception e) {
				resultado = "FRACASO";
				Log.error("Se ha producido un error registrando el viaje");
				request.setAttribute("mensaje",
						"Se ha producido un error registrando el viaje");
			}
		}

		return resultado;
	}

	private Seat crearSeat(Long userId, Long id) {
		Seat seat = new Seat();
		seat.setTripId(id);
		seat.setUserId(userId);
		seat.setStatus(SeatStatus.ACCEPTED);
		return seat;
	}

	private Trip crearViaje(AddressPoint salida, AddressPoint llegada,
			Date fechaSalida, Date fechaLlegada, Date fechaCierre, int maxPax,
			double estimatedCost, String comentarios, Long userId) {
		Trip trip = new Trip();
		trip.setArrivalDate(fechaLlegada);
		trip.setAvailablePax(maxPax - 1);
		trip.setClosingDate(fechaCierre);
		trip.setComments(comentarios);
		trip.setDeparture(salida);
		trip.setDepartureDate(fechaSalida);
		trip.setDestination(llegada);
		trip.setEstimatedCost(estimatedCost);
		trip.setMaxPax(maxPax);
		trip.setPromoterId(userId);
		trip.setStatus(TripStatus.OPEN);

		return trip;
	}

	private AddressPoint crearAddress(String address, String city,
			String state, String country, String cp, String lat, String lon) {
		AddressPoint addressPoint = new AddressPoint(address, city, state,
				country, cp, new Waypoint(Double.valueOf(lat),
						Double.valueOf(lon)));
		return addressPoint;
	}

	private String fechasValidas(Date salida, Date llegada, Date cierre) {
		String resultado = OK;
		if (MariaDateUtil.isBefore(salida, cierre))
			resultado = SAC;
		else if (MariaDateUtil.isBefore(llegada, salida))
			resultado = LAS;
		return resultado;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

}
