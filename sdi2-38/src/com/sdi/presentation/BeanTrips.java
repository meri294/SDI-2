package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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

@ManagedBean(name = "tripsController")
@SessionScoped
public class BeanTrips implements Serializable {
	private static final long serialVersionUID = 55555L;
	@ManagedProperty(value = "#{trip}")
	private BeanTrip trip;

	private Trip[] trips = null;

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

	public Trip[] getTrips() {
		return (trips);
	}

	public void setTrip(BeanTrip trip) {
		this.trip = trip;
	}

	public BeanTrip getTrip() {
		return trip;
	}

	public void setTrips(Trip[] trips) {
		this.trips = trips;
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
			trip = (BeanTrip)service.findById(trip.getId());
			return "exito"; // Nos vamos a la vista de Edición.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}

	public String salva() {
		TripService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.createTripService();
			// Salvamos o actualizamos el trip segun sea una operacion de alta
			// o de edici��n
			if (trip.getId() == null) {
				service.saveTrip(trip);
			} else {
				service.updateTrip(trip);
			}
			// Actualizamos el javabean de trips inyectado en la tabla
			trips = (Trip[]) service.getTrips().toArray(new Trip[0]);
			return "exito"; // Nos vamos a la vista de listado.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}
}
