package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.business.SeatsService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.User;

@ManagedBean(name = "seatController")
@SessionScoped
public class BeanSeats {

	private FacesContext context = FacesContext.getCurrentInstance();

	private Trip trip;
	private User promoter;
	private User[] participantes = null;

	@PostConstruct
	public void init() {
		System.out.println("BeanApplications - PostConstruct");
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanApplications - PreDestroy");
	}

	public User getPromoter() {
		return promoter;
	}

	public void setPromoter(User promoter) {
		this.promoter = promoter;
	}

	public User[] getParticipantes() {
		return participantes;
	}

	public void setParticipantes(User[] participantes) {
		this.participantes = participantes;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public String obtenerParticipantes(Long idTrip) {
		
		SeatsService service;
		try {
			service = Factories.services.createSeatsService();
			trip = Factories.services.createTripService().findById(idTrip);
			List<Seat> seats = service.getParticipantes(idTrip);
			participantes(seats, trip.getPromoterId());
			promoter = Factories.services.createUserService().findById(
					trip.getPromoterId());
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			e.printStackTrace();
			return "error";
		}
		Log.debug("Se han obtenido los participantes del viaje [%s]", idTrip);
		return "exito";
	}

	private void participantes(List<Seat> seats, Long idPromoter) {
		
		List<User> part = new ArrayList<User>();
		for (Seat seat : seats) {
			part.add(Factories.services.createUserService().findById(
					seat.getUserId()));
		}
		participantes = (User[]) part.toArray(new User[0]);
	}

	public String excluir(User usuario) {

		if (usuario.getId().equals(trip.getId())) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ResourceBundle bundle = facesContext.getApplication()
					.getResourceBundle(facesContext, "msgs");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(bundle
							.getString("mensaje_excluirPromotor")));

			return Resultado.fracaso.name();
		}

		SeatsService sService = Factories.services.createSeatsService();

		try {
			sService.excluirPlaza(sService.findByUserAndTrip(usuario.getId(),
					trip.getId()));
			Log.debug("Se ha excluído al usuario [%s] del viaje [%s]",
					usuario.getId(), trip.getId());
			return obtenerParticipantes(trip.getId());
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			Log.error("Se ha producido un error excluyendo al usuario [%s]",
					usuario.getId());
			return Resultado.error.name();
		}
	}
}
