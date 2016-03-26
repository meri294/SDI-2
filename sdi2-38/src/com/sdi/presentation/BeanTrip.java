package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.model.Trip;
import com.sdi.util.MariaDateUtil;
import com.sdi.util.MariaModelUtil;

@ManagedBean(name = "trip")
@SessionScoped
public class BeanTrip extends Trip implements Serializable {
	private static final long serialVersionUID = 55556L;

	public BeanTrip() {
		iniciaTrip(null);
	}

	// Este método es necesario para copiar el trip a editar cuando
	// se pincha el enlace Editar en la vista listado.xhtml. Podría sustituirse
	// por un método editar en BeanTrips.
	public void setTrip(Trip trip) {
		setId(trip.getId());
		setArrivalDate(trip.getArrivalDate());
		setAvailablePax(trip.getAvailablePax());
		setClosingDate(trip.getClosingDate());
		setComments(trip.getComments());
		setDeparture(trip.getDeparture());
		setDepartureDate(trip.getDepartureDate());
		setDestination(trip.getDestination());
		setEstimatedCost(trip.getEstimatedCost());
		setId(trip.getId());
		setMaxPax(trip.getMaxPax());
		setPromoterId(trip.getPromoterId());
		setStatus(trip.getStatus());
	}

	// Iniciamos los datos del trip con los valores por defecto
	// extraídos del archivo de propiedades correspondiente
	public void iniciaTrip(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		setId(null);
		setArrivalDate(MariaDateUtil.completeFromBundle(
				bundle.getString("default_date")));
		setAvailablePax(Integer.valueOf(
				bundle.getString("default_available_pax")));
		setClosingDate(MariaDateUtil.completeFromBundle(
				bundle.getString("default_date")));
		setComments(bundle.getString("default_trip_comments"));
		setDepartureDate(MariaDateUtil.completeFromBundle(
				bundle.getString("default_date")));
		setDeparture(MariaModelUtil.AddressPointFromString(
				bundle.getString("default_address"),
				bundle.getString("default_city"),
				bundle.getString("default_state"),
				bundle.getString("default_country"),
				bundle.getString("default_cp"),
				bundle.getString("default_coordinates")));
		setDestination(MariaModelUtil.AddressPointFromString(
				bundle.getString("default_address"),
				bundle.getString("default_city"),
				bundle.getString("default_state"),
				bundle.getString("default_country"),
				bundle.getString("default_cp"),
				bundle.getString("default_coordinates")));
		setEstimatedCost(Double.valueOf(bundle.getString
				("default_estimated_cost")));
		setMaxPax(Integer.valueOf(bundle.getString("default_maxPax")));
		setPromoterId(null);
		setStatus(null);
		
	}
}