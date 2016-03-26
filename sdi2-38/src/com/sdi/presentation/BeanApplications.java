package com.sdi.presentation;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import com.sdi.business.ApplicationService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;

@ManagedBean(name = "applicationsController")
public class BeanApplications {
	private FacesContext context = FacesContext.getCurrentInstance();
	@ManagedProperty(value = "#{application}")
	private BeanApplication application;

	private Application[] applications = null;

	@PostConstruct
	public void init() {
		System.out.println("BeanApplications - PostConstruct");
		application = (BeanApplication) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap()
				.get(new String("application"));
		if (application == null) {
			System.out.println("BeanApplications - No existia");
			application = new BeanApplication();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("application", application);
		}
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanApplications - PreDestroy");
	}

	public Application[] getApplications() {
		return (applications);
	}

	public void setApplication(BeanApplication application) {
		this.application = application;
	}

	public BeanApplication getApplication() {
		return application;
	}

	public void setApplications(Application[] applications) {
		this.applications = applications;
	}

	public void iniciaApplication(ActionEvent event) {
		application.setTripId(null);
		application.setUserId(null);
	}

	public String listado() {
		ApplicationService service;
		try {
			service = Factories.services.createApplicationService();
			applications = (Application[]) service.getApplications().toArray(
					new Application[0]);

			return "exito";

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
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
			return "error";
		}

	}

	public String edit() {
		ApplicationService service;
		try {
			service = Factories.services.createApplicationService();
			Long[] ids = { application.getUserId(), application.getTripId() };
			application = (BeanApplication) service.findById(ids);
			return "exito";

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return "error";
		}

	}

	public String salva(Long userId, Long tripId) {
		ApplicationService service;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		try {
			service = Factories.services.createApplicationService();
			if (application.getUserId() == null
					&& application.getTripId() == null) {
				application.setTripId(tripId);
				application.setUserId(userId);
				service.saveApplication(application);
				ELContext elContext = FacesContext.getCurrentInstance().getELContext();
				BeanInvolucrado bean 
				    = (BeanInvolucrado) FacesContext.getCurrentInstance().getApplication()
				    .getELResolver().getValue(elContext, null, "involucrado");
				bean.misViajes(userId);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						bundle.getString("error_solicitudRealizada"),
						bundle.getString("error_solicitudRealizada"));
				throw new ValidatorException(message);
			}
			applications = (Application[]) service.getApplications().toArray(
					new Application[0]);
			//new BeanInvolucrado().misViajes(userId);
			//TODO actualizar BeanInvolucrado antes de que se lance misViajes 
			
			return "exito";

		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));
			return "error";
		}

	}
}
