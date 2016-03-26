package com.sdi.presentation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
import com.sdi.util.MariaDateUtil;
import com.sdi.util.MariaModelUtil;

@ManagedBean(name = "involucrado")
@SessionScoped
public class BeanInvolucrado implements Serializable {
	private static final long serialVersionUID = 55555L;
	@ManagedProperty(value = "#{trip}")
	private BeanTrip trip;

	private Trip[] promotor = null;
	private Trip[] excluido=null;
	private Trip[] participante=null;
	private Trip[] enEspera=null;
	private Trip[] sinPlaza=null;
	

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
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		trip.setId(null);
		trip.setArrivalDate(MariaDateUtil.completeFromBundle(
				bundle.getString("default_date")));
		trip.setAvailablePax(Integer.valueOf(
				bundle.getString("default_available_pax")));
		trip.setClosingDate(MariaDateUtil.completeFromBundle(
				bundle.getString("default_date")));
		trip.setComments(bundle.getString("default_trip_comments"));
		trip.setDepartureDate(MariaDateUtil.completeFromBundle(
				bundle.getString("default_date")));
		trip.setDeparture(MariaModelUtil.AddressPointFromString(
				bundle.getString("default_address"),
				bundle.getString("default_city"),
				bundle.getString("default_state"),
				bundle.getString("default_country"),
				bundle.getString("default_cp"),
				bundle.getString("default_coordinates")));
		trip.setDestination(MariaModelUtil.AddressPointFromString(
				bundle.getString("default_address"),
				bundle.getString("default_city"),
				bundle.getString("default_state"),
				bundle.getString("default_country"),
				bundle.getString("default_cp"),
				bundle.getString("default_coordinates")));
		trip.setEstimatedCost(Double.valueOf(bundle.getString
				("default_estimated_cost")));
		trip.setMaxPax(Integer.valueOf(bundle.getString("default_maxPax")));
		trip.setPromoterId(null);
		trip.setStatus(null);
	}

	
	public String misViajes(Long userId){
		TripService service;
		try{
			service=Factories.services.createTripService();
			Map<String, List<Trip>> invol=service.findInvolucrado(userId);
			promotor=(Trip[])invol.get("promotor").toArray(new Trip[0]);
			enEspera=(Trip[])invol.get("enEspera").toArray(new Trip[0]);
			participante=(Trip[])invol.get("participante").toArray(new Trip[0]);
			excluido=(Trip[])invol.get("excluido").toArray(new Trip[0]);
			sinPlaza=(Trip[])invol.get("sinPlaza").toArray(new Trip[0]);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(e.getMessage()));
			return "error";
		}
		return "exito";
	}
}
