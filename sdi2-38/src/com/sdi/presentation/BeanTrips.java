package com.sdi.presentation;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.model.TripStatus;

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

    private List<Trip> tripsDisponibles = null;
    private Trip[] promotor = null;
    private Trip[] excluido = null;
    private Trip[] participante = null;
    private Trip[] enEspera = null;
    private Trip[] sinPlaza = null;

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
    }

    @PreDestroy
    public void end() {
	System.out.println("BeanTrips - PreDestroy");
    }

    public List<Trip> getTripsDisponibles() {
	return (tripsDisponibles);
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

    public void setTripsDisponibles(List<Trip> trips) {
	this.tripsDisponibles = trips;
    }
    
    public Trip[] getPromotor() {
	return promotor;
    }

    public void setPromotor(Trip[] promotor) {
	this.promotor = promotor;
    }

    public Trip[] getExcluido() {
	return excluido;
    }

    public void setExcluido(Trip[] excluido) {
	this.excluido = excluido;
    }

    public Trip[] getParticipante() {
	return participante;
    }

    public void setParticipante(Trip[] participante) {
	this.participante = participante;
    }

    public Trip[] getEnEspera() {
	return enEspera;
    }

    public void setEnEspera(Trip[] enEspera) {
	this.enEspera = enEspera;
    }

    public Trip[] getSinPlaza() {
	return sinPlaza;
    }

    public void setSinPlaza(Trip[] sinPlaza) {
	this.sinPlaza = sinPlaza;
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
	    tripsDisponibles = service.getTrips();

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
	    tripsDisponibles = service.getTrips();
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

    public String salva(Long userId) {

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
	trip.setPromoterId(userId);
	trip.setStatus(TripStatus.OPEN);

	TripService service;
	try {
	    // Acceso a la implementacion de la capa de negocio
	    // a trav��s de la factor��a
	    service = Factories.services.createTripService();
	    // Salvamos o actualizamos el trip segun sea una operacion de alta
	    // o de edici��n
	    if (trip.getId() == null) {
		trip.setAvailablePax(trip.getMaxPax());
		service.saveTrip(trip);
		Log.debug("Viaje registrado correctamente");

		Factories.services.createSeatsService().aceptarPlaza(sesion.getUsuario().getId(), trip.getId());
	    } else {

		/* TODO Tiene sentido mirar el estado del viaje?
		Trip tripEnBBDD = service.findById(trip.getId());
		if (tripEnBBDD.getStatus() == TripStatus.CANCELLED
			|| tripEnBBDD.getStatus() == TripStatus.DONE) {
		    Log.error("El viaje ha sido cancelado o ya se ha realizado");
		    FacesContext.getCurrentInstance().addMessage(
			    null,
			    new FacesMessage(bundle
				    .getString("error_tratandoViaje")));
		    // request.setAttribute("mensaje",
		    // "El viaje ha sido cancelado "
		    // + "o ya se ha realizado");

		    return Resultado.error.name();
		}
		*/

		service.updateTrip(trip);
	    }
	    
	    // Actualizamos el javabean de trips inyectado en la tabla
	    tripsDisponibles = service.getTrips();

	    return sacarMisViajes(); // Nos vamos a la vista de listado.

	} catch (Exception e) {
	    e.printStackTrace();
	    Log.error("Se ha producido un error tratando el viaje");
	    FacesContext.getCurrentInstance()
		    .addMessage(
			    null,
			    new FacesMessage(bundle
				    .getString("error_tratandoViaje")));

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
    
    public String sacarMisViajes() {
	TripService service;
	try {
	    service = Factories.services.createTripService();
	    Map<String, List<Trip>> invol = service.findInvolucrado(sesion.getUsuario().getId());
	    promotor = (Trip[]) invol.get("promotor").toArray(new Trip[0]);
	    enEspera = (Trip[]) invol.get("enEspera").toArray(new Trip[0]);
	    participante = (Trip[]) invol.get("participante").toArray(
		    new Trip[0]);
	    excluido = (Trip[]) invol.get("excluido").toArray(new Trip[0]);
	    sinPlaza = (Trip[]) invol.get("sinPlaza").toArray(new Trip[0]);
	} catch (Exception e) {
	    FacesContext.getCurrentInstance().addMessage(null,
		    new FacesMessage(e.getMessage()));
	    return "error";
	}
	return "exito";
    }
}
