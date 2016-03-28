package com.sdi.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;

import alb.util.log.Log;

import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.util.MariaDateUtil;

@ManagedBean(name = "tripsController")
public class BeanTrips implements Serializable {
    private static final long serialVersionUID = 55555L;

    @ManagedProperty(value = "#{sesion}")
    private BeanSesion sesion;

    @ManagedProperty(value = "#{trip}")
    private BeanTrip trip;
    
    @ManagedProperty(value = "#{involucrado}")
    private BeanInvolucrado involucrado;

    private Trip[] trips = null;

    private String departureDateConFormato = "";
    private String departureHourConFormato = "";
    private String arrivalDateConFormato = "";
    private String arrivalHourConFormato = "";
    private String closingDateConFormato = "";
    private String closingHourConFormato = "";

    @PostConstruct
    public void init() {
	System.out.println("BeanTrips - PostConstruct");
	trip = (BeanTrip) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get(new String("trip"));
	if (trip == null) {
	    System.out.println("BeanTrips - No existia");
	    trip = new BeanTrip();
	    FacesContext.getCurrentInstance().getExternalContext()
		    .getSessionMap().put("trip", trip);
	}

	sesion = (BeanSesion) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("sesion");

	if (sesion == null) {
	    sesion = new BeanSesion();
	    FacesContext.getCurrentInstance().getExternalContext()
		    .getSessionMap().put("sesion", sesion);
	}
	
	involucrado = (BeanInvolucrado) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("involucrado");

	if (involucrado == null) {
	    involucrado = new BeanInvolucrado();
	    FacesContext.getCurrentInstance().getExternalContext()
		    .getSessionMap().put("involucrado", sesion);
	}
    }

    @PreDestroy
    public void end() {
	System.out.println("BeanTrips - PreDestroy");
    }

    public Trip[] getTrips() {
	return (trips);
    }

    public void setTrip(BeanTrip trip) {
	this.trip = trip;
    }

    public BeanTrip getTrip() {
	return trip;
    }

    public BeanSesion getSesion() {
	return sesion;
    }

    public void setSesion(BeanSesion sesion) {
	this.sesion = sesion;
    }

    public void setTrips(Trip[] trips) {
	this.trips = trips;
    }

    public String getDepartureDateConFormato() {
	return departureDateConFormato;
    }

    public void setDepartureDateConFormato(String departureDateConFormato) {
	this.departureDateConFormato = departureDateConFormato;
    }

    public String getDepartureHourConFormato() {
	return departureHourConFormato;
    }

    public void setDepartureHourConFormato(String departureHourConFormato) {
	this.departureHourConFormato = departureHourConFormato;
    }

    public String getArrivalDateConFormato() {
	return arrivalDateConFormato;
    }

    public void setArrivalDateConFormato(String arrivalDateConFormato) {
	this.arrivalDateConFormato = arrivalDateConFormato;
    }

    public String getArrivalHourConFormato() {
	return arrivalHourConFormato;
    }

    public void setArrivalHourConFormato(String arrivalHourConFormato) {
	this.arrivalHourConFormato = arrivalHourConFormato;
    }

    public String getClosingDateConFormato() {
	return closingDateConFormato;
    }

    public void setClosingDateConFormato(String closingDateConFormato) {
	this.closingDateConFormato = closingDateConFormato;
    }

    public String getClosingHourConFormato() {
	return closingHourConFormato;
    }

    public void setClosingHourConFormato(String closingHourConFormato) {
	this.closingHourConFormato = closingHourConFormato;
    }

    public void iniciaTrip(ActionEvent event) {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	ResourceBundle bundle = facesContext.getApplication()
		.getResourceBundle(facesContext, "msgs");
	
	trip.iniciaTrip(event);

	departureDateConFormato = bundle.getString("default_date");
	departureHourConFormato = bundle.getString("default_hour");
	arrivalDateConFormato = bundle.getString("default_date");
	arrivalHourConFormato = bundle.getString("default_hour");
	closingDateConFormato = bundle.getString("default_date");
	closingHourConFormato = bundle.getString("default_hour");
    }

    public String listado() {
	TripService service;
	try {
	    // Acceso a la implementacion de la capa de negocio
	    // a trav��s de la factor��a
	    service = Factories.services.createTripService();
	    // De esta forma le damos informaci��n a toArray para poder hacer el
	    // casting a Trip[]
	    trips = (Trip[]) service.getTrips().toArray(new Trip[0]);

	    return "exito";

	} catch (Exception e) {
	    e.printStackTrace();
	    return "error"; // Nos vamos la vista de error
	}

    }

    public String baja(Trip trip) {
	TripService service;
	try {
	    // Acceso a la implementacion de la capa de negocio
	    // a trav��s de la factor��a
	    service = Factories.services.createTripService();
	    // Aliminamos el trip seleccionado en la tabla
	    service.deleteTrip(trip.getId());
	    // Actualizamos el javabean de trips inyectado en la tabla.
	    trips = (Trip[]) service.getTrips().toArray(new Trip[0]);
	    return "exito"; // Nos vamos a la vista de listado.

	} catch (Exception e) {
	    e.printStackTrace();
	    return "error"; // Nos vamos a la vista de error
	}

    }

    public String edit() {
	TripService service;
	try {
	    // Acceso a la implementacion de la capa de negocio
	    // a trav��s de la factor��a
	    service = Factories.services.createTripService();
	    // Recargamos el trip seleccionado en la tabla de la base de datos
	    // por si hubiera cambios.
	    trip = (BeanTrip) service.findById(trip.getId());
	    return "exito"; // Nos vamos a la vista de Edición.

	} catch (Exception e) {
	    e.printStackTrace();
	    return "error"; // Nos vamos a la vista de error.
	}

    }

    public String salva() {

	FacesContext facesContext = FacesContext.getCurrentInstance();
	ResourceBundle bundle = facesContext.getApplication()
		.getResourceBundle(facesContext, "msgs");

	Date fechaSalida = MariaDateUtil.completeFromString(
		departureDateConFormato, departureHourConFormato);
	Date fechaLlegada = MariaDateUtil.completeFromString(
		arrivalDateConFormato, arrivalHourConFormato);
	Date fechaCierre = MariaDateUtil.completeFromString(
		closingDateConFormato, closingHourConFormato);
	ComprobacionFechaValida isFechaValida = fechasValidas(fechaSalida,
		fechaLlegada, fechaCierre);

	if (!isFechaValida.equals(ComprobacionFechaValida.OK)) {
	    FacesContext.getCurrentInstance().addMessage(
		    "viaje:departureDate",
		    new FacesMessage(bundle.getString(isFechaValida
			    .getIdMensaje())));
	    Log.error(bundle.getString(isFechaValida.getIdMensaje()));

	    return Resultado.fracaso.name();
	}

	trip.setDepartureDate(fechaSalida);
	trip.setArrivalDate(fechaLlegada);
	trip.setClosingDate(fechaCierre);

	TripService service;
	try {
	    // Acceso a la implementacion de la capa de negocio
	    // a trav��s de la factor��a
	    service = Factories.services.createTripService();
	    // Salvamos o actualizamos el trip segun sea una operacion de alta
	    // o de edici��n
	    if (trip.getId() == null) {
		service.saveTrip(trip);
		Log.debug("Viaje registrado correctamente");

		Factories.services.createSeatsService().save(
			crearSeat(sesion.getUsuario().getId(), trip.getId()));
	    } else {
		service.updateTrip(trip);
	    }
	    // Actualizamos el javabean de trips inyectado en la tabla
	    trips = (Trip[]) service.getTrips().toArray(new Trip[0]);
	    
	    return involucrado.misViajes(); // Nos vamos a la vista de listado.

	} catch (Exception e) {
	    e.printStackTrace();
	    Log.error("Se ha producido un error registrando el viaje");
	    FacesContext.getCurrentInstance()
		    .addMessage(
			    null,
			    new FacesMessage(bundle
				    .getString("error_registrandoViaje")));

	    return Resultado.error.name(); // Nos vamos a la vista de error.
	}

    }

    private ComprobacionFechaValida fechasValidas(Date salida, Date llegada,
	    Date cierre) {
	ComprobacionFechaValida resultado = ComprobacionFechaValida.OK;
	if (MariaDateUtil.isBefore(salida, cierre))
	    resultado = ComprobacionFechaValida.SAC;
	else if (MariaDateUtil.isBefore(llegada, salida))
	    resultado = ComprobacionFechaValida.LAS;
	return resultado;
    }

    private Seat crearSeat(Long userId, Long id) {
	Seat seat = new Seat();
	seat.setTripId(id);
	seat.setUserId(userId);
	seat.setStatus(SeatStatus.ACCEPTED);
	return seat;
    }
}
