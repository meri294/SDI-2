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
 * @author Paula Díaz Puertas
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
		// Si es publico solo puede acceder a login, registro y error
		// Si no es admin, no puede acceder a listaUsuarios,
		// adminModificarPassUsuario, adminModificarUsuario y reiniciarBBDD
		BeanSesion sesion = (BeanSesion) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("sesion");

		if (sesion == null || sesion.getUsuario() == null
				|| sesion.getUsuario().getRol() == null) {
			if (view.contains("login") || view.contains("registro")
					|| view.contains("error")) {
				// Es permitido
				return;
			}
			NavigationHandler nh = fc.getApplication().getNavigationHandler();
			nh.handleNavigation(fc, null, "cerrar");
		}

		else {
			switch (sesion.getUsuario().getRol()) {
			case cliente:
				if (view.contains("dmin") || view.contains("listaUsuarios")
						|| view.contains("reiniciarBBDD")) {
					NavigationHandler nh = fc.getApplication()
							.getNavigationHandler();
					nh.handleNavigation(fc, null, "principal");
				}
				break;
			case administrador:
				if (view.contains("dmin2")) {
					NavigationHandler nh = fc.getApplication()
							.getNavigationHandler();
					nh.handleNavigation(fc, null, "principal");
				}
				break;
			case admin2:
				if (!view.contains("dmin2") && !view.contains("error")) {
					NavigationHandler nh = fc.getApplication()
							.getNavigationHandler();
					nh.handleNavigation(fc, null, "admin2");
				}
				break;
			default:
				NavigationHandler nh = fc.getApplication()
						.getNavigationHandler();
				nh.handleNavigation(fc, null, "error");
				break;

			}
		}

		/*
		 * else if (sesion.getUsuario().getRol().equals(Rol.cliente)) { if
		 * (view.contains("admin") || view.contains("listaUsuarios") ||
		 * view.contains("reiniciarBBDD")) { NavigationHandler nh =
		 * fc.getApplication() .getNavigationHandler(); nh.handleNavigation(fc,
		 * null, "principal"); } else return; }
		 * 
		 * else if(sesion.)
		 */
		// Meter autorizacion para admin2
		// NADIE MAS PUEDE ACCEDER A LA PANTALLA PRINCIPAL DE ADMIN2.

	}

	@Override
	public void beforePhase(PhaseEvent arg0) {

	}

}
