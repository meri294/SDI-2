package com.sdi.presentation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.SeatsService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.User;

@ManagedBean(name = "seatController")
@SessionScoped
public class BeanSeats {
	private FacesContext context = FacesContext.getCurrentInstance();

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

	public String obtenerParticipantes(Long idTrip) {
		SeatsService service;
		try {
			service = Factories.services.createSeatsService();
			Long idPromoter = Factories.services.createTripService()
					.findById(idTrip).getPromoterId();
			List<Seat> seats = service.getParticipantes(idTrip);
			participantes(seats, idPromoter);
			promoter = Factories.services.createUserService().findById(
					idPromoter);
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return "error";
		}
		return "exito";
	}

	private void participantes(List<Seat> seats, Long idPromoter) {
		List<User> part= new ArrayList<User>();
		for (Seat seat : seats) {
			part.add(Factories.services.createUserService().findById(
					seat.getUserId()));
		}
		participantes=(User[]) part.toArray(new User[0]);
	}

	public String excluir(Long userId, Long tripId) {
		// TODO Aceptar participante??
		return null;
	}

	public String aceptar(Long userId, Long tripId) {
		// TODO Aceptar participante??
		return null;
	}
}
