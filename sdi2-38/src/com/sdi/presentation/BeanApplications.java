package com.sdi.presentation;

import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.business.ApplicationService;
import com.sdi.business.SeatsService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Trip;

@ManagedBean(name = "applicationsController")
@SessionScoped
public class BeanApplications {

	private FacesContext context = FacesContext.getCurrentInstance();

	@ManagedProperty(value = "#{tripsController}")
	private BeanTrips beanTrips;

	@ManagedProperty(value = "#{sesion}")
	private BeanSesion sesion;

	private Application[] applications = null;

	@PostConstruct
	public void init() {
		
		System.out.println("BeanApplications - PostConstruct");

		beanTrips = (BeanTrips) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("tripsController");

		if (beanTrips == null) {
			beanTrips = new BeanTrips();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("tripsController", beanTrips);
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
		System.out.println("BeanApplications - PreDestroy");
	}

	public Application[] getApplications() {
		return (applications);
	}

	public void setApplications(Application[] applications) {
		this.applications = applications;
	}

	public String listado() {
		
		ApplicationService service;
		try {
			service = Factories.services.createApplicationService();
			applications = (Application[]) service.getApplications().toArray(
					new Application[0]);

			Log.debug("Se ha obtenido la lista de solicitudes");
			return "exito";

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			e.printStackTrace();
			Log.error ("Ha habido un error procesando el listado de "
					+ "solicitudes");
			return "error";
		}

	}

	public String baja(Application application) {
		
		ApplicationService service;
		try {
			service = Factories.services.createApplicationService();
			Long[] ids = { application.getUserId(), application.getTripId() };
			service.deleteApplication(ids);
			applications = (Application[]) service.getApplications().toArray(
					new Application[0]);
			return "exito";

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			e.printStackTrace();
			return "error";
		}

	}

	public String salva(Long tripId) {
		
		ApplicationService service;

		try {
			service = Factories.services.createApplicationService();

			Application application = new Application();
			application.setTripId(tripId);
			application.setUserId(sesion.getUsuario().getId());
			service.saveApplication(application);

			Log.debug("Creada solicitud del usuario [%d] para el viaje [%d]",
					application.getUserId(), tripId);

			beanTrips.sacarMisViajes();

			applications = (Application[]) service.getApplications().toArray(
					new Application[0]);

			return "exito";

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			e.printStackTrace();
			Log.error("No se ha podido guardar la solicitud");
			return "error";
		}

	}

	public String aceptar(Application application) {

		try {
			// Antes que nada se ha de comprobar si hay sitio para aceptar al
			// pasajero!
			Trip trip = Factories.services.createTripService().findById(
					application.getTripId());

			if (trip.getAvailablePax() < 1) {

				FacesContext facesContext = FacesContext.getCurrentInstance();
				ResourceBundle bundle = facesContext.getApplication()
						.getResourceBundle(facesContext, "msgs");

				context.addMessage(
						null,
						new FacesMessage(bundle
								.getString("mensaje_solicitanteNoAceptado")));
				Log.error("No hay plazas disponibles");
				return Resultado.fracaso.name();
			}

			// Crear/aceptar seat a partir de la application
			Factories.services.createSeatsService().aceptarPlaza(
					application.getUserId(), application.getTripId());
			if (Factories.services.createTripService().
					findById(application.getTripId())
					.getAvailablePax()==0){
				pasarAsinPlaza(application.getTripId());
			}
			// Vuelvo a sacar el listado de solicitudes
			
			return sacarSolicitudes(application.getTripId());

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			e.printStackTrace();
			return Resultado.error.name();
		}

	}

	private void pasarAsinPlaza(Long tripId) {
		List<Application> listApp=Factories.services
				.createApplicationService().getApplicationsWithoutSeatFor(
						tripId);
		SeatsService service = Factories.services.createSeatsService();
		for (Application app: listApp){
			service.crearSinPlaza(app.getUserId(), app.getTripId());
		}
		
	}

	public String excluir(Application application) {

		// Crear y excluir plaza a partir de la application
		Factories.services.createSeatsService().excluirPlaza(
				application.getUserId(), application.getTripId());

		// Vuelvo a sacar el listado de solicitudes
		return sacarSolicitudes(application.getTripId());
	}

	public String sacarSolicitudes(Long tripId) {

		// Recoger las SOLICITUDES (o sea, sin seat creado) del viaje pasado
		List<Application> listApp = Factories.services
				.createApplicationService().getApplicationsWithoutSeatFor(
						tripId);

		// Guardarlas en applications
		applications = (Application[]) listApp.toArray(new Application[0]);

		return Resultado.exito.name();
	}
}
