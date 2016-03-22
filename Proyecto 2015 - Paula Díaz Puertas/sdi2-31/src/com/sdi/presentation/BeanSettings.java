package com.sdi.presentation;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "settings")
@SessionScoped
public class BeanSettings implements Serializable {

	private static final long serialVersionUID = 2L;
	private static final Locale ENGLISH = new Locale("en");
	private static final Locale SPANISH = new Locale("es");
	private Locale locale = SPANISH;

	public Locale getLocale() {
		// Aqui habría que cambiar algo de código para coger
		// locale del navegador
		// la primera vez que se accede a getLocale()
		// aunque de momento lo dejamos así.
		return (locale);
	}

	public void setSpanish(ActionEvent event) {
		locale = SPANISH;
		try {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setEnglish(ActionEvent event) {
		locale = ENGLISH;
		try {
			FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
