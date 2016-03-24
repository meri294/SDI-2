package com.sdi.presentation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.business.ApplicationService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;

@ManagedBean(name = "applicationsController")
@SessionScoped
public class BeanApplications implements Serializable {
	private static final long serialVersionUID = 55555L;
	@ManagedProperty(value = "#{application}")
	private BeanApplication application;

	private Application[] applications = null;

	@PostConstruct
	public void init() {
		System.out.println("BeanApplications - PostConstruct");
		application = (BeanApplication) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("application"));
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
			applications = (Application[]) service.getApplications().toArray(new Application[0]);

			return "exito"; 

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	public String baja(Application application) {
		ApplicationService service;
		try {
			service = Factories.services.createApplicationService();
			Long [] ids= {application.getUserId(), application.getTripId()};
			service.deleteApplication(ids);
			applications = (Application[]) service.getApplications().toArray(new Application[0]);
			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; 
		}

	}

	public String edit() {
		ApplicationService service;
		try {
			service = Factories.services.createApplicationService();
			Long [] ids = {application.getUserId(), application.getTripId()};
			application = (BeanApplication)service.findById(ids);
			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

	}

	public String salva() {
		ApplicationService service;
		try {
			service = Factories.services.createApplicationService();
			if (application.getUserId() == null 
					&& application.getTripId()==null) {
				service.saveApplication(application);
			} else {
				service.updateApplication(application);
			}
			applications = (Application[]) service.getApplications().toArray(new Application[0]);
			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; 
		}

	}
}
