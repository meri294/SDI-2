package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.model.Trip;

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
		/*setIduser(bundle.getString("valorDefectoUserId"));
		setNombre(bundle.getString("valorDefectoNombre"));
		setApellidos(bundle.getString("valorDefectoApellidos"));
		setEmail(bundle.getString("valorDefectoCorreo"));*/
	}
}