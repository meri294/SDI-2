package com.sdi.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.sdi.presentation.BeanSesion;

/**
 * Este listener se encarga de la autorización del sistema.
 * 
 * Comprueba que el usuario tiene permisos de acceso para el recurso que
 * solicita.
 * 
 */
public class LoginListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    @Override
    public PhaseId getPhaseId() {
	return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void afterPhase(PhaseEvent event) {
	FacesContext fc = event.getFacesContext();
	String view = fc.getViewRoot().getViewId();
	// Si es publico solo puede acceder a login, registro, listaViajes y
	// error
	// Si es registrado no puede acceder a login ni a registro.
	BeanSesion sesion = (BeanSesion) FacesContext.getCurrentInstance()
		.getExternalContext().getSessionMap().get("sesion");

	//Si el usuario no está registrado...
	if (sesion == null || sesion.getUsuario() == null) {
	    if (view.contains("login") || view.contains("registro.xhtml")
		    || view.contains("error") || view.contains("listaViajes")) {
		// Es permitido
		return;
	    }
	    NavigationHandler nh = fc.getApplication().getNavigationHandler();
	    nh.handleNavigation(fc, null, "cerrar");
	}

	//Si el usuario está registrado...
	else {
	    if (view.contains("login") || view.contains("registro.xhtml")) {

		NavigationHandler nh = fc.getApplication()
			.getNavigationHandler();
		nh.handleNavigation(fc, null, "/principalRegistrado.xhtml");
	    }
	}
    }

    @Override
    public void beforePhase(PhaseEvent arg0) {

    }

}
