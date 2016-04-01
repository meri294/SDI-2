package com.sdi.presentation;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.sdi.model.User;

@ManagedBean(name = "sesion")
@SessionScoped
public class BeanSesion implements Serializable {

	private User usuario;

	private static final long serialVersionUID = 1L;
	
	public BeanSesion() {
		init();
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String cerrar() {

		setUsuario(null);

		return "cerrar";
	}

	@PostConstruct
	public void init() {
	}

}
