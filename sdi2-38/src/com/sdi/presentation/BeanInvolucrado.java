package com.sdi.presentation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

@ManagedBean(name = "involucrado")
@SessionScoped
public class BeanInvolucrado implements Serializable {
    private static final long serialVersionUID = 55555L;

    @ManagedProperty(value = "#{sesion}")
    private BeanSesion sesion;

    @ManagedProperty(value = "#{trip}")
    private BeanTrip trip;

    private Trip[] promotor = null;
    private Trip[] excluido = null;
    private Trip[] participante = null;
    private Trip[] enEspera = null;
    private Trip[] sinPlaza = null;

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

    public void setTrip(BeanTrip trip) {
	this.trip = trip;
    }

    public BeanTrip getTrip() {
	return trip;
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

    public void iniciaTrip(ActionEvent event) {
	trip.iniciaTrip(event);
    }

    public String misViajes() {
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
