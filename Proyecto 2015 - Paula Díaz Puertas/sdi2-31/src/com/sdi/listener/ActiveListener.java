package com.sdi.listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.sdi.infrastructure.Factories;
import com.sdi.presentation.BeanSesion;

/**
 * Este listener se encarga de comprobar que los usuarios que ya tengan
 * la sesión iniciada sigan teniendo la cuenta activa.
 * 
 * Si su cuenta ha sido desactivado, se les cierra la sesión y se les redirige
 * a la página de login
 * 
 * @author Paula Díaz Puertas
 *
 */
public class ActiveListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent arg0) {

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		BeanSesion sesion = (BeanSesion) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("sesion");
		
		if (sesion != null && sesion.getUsuario() != null) {
			//Si el usuario esta iniciado...
			boolean activo = Factories.persistence.createUsuarioDao()
					.getActivoByLogin(sesion.getUsuario().getLogin());
			
			if (!activo) {
				//Y ha sido desactivado, se cierra su sesion y se le redirige
				//a la pagina de login
				FacesContext fc = event.getFacesContext();
				NavigationHandler nh = fc.getApplication()
						.getNavigationHandler();
				nh.handleNavigation(fc, null, sesion.cerrar());

			}
		}

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.INVOKE_APPLICATION;
	}

}
