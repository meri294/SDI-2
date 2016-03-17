package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;


@ManagedBean(name = "cambio")
@SessionScoped
public class BeanCambio implements Serializable {

	private static final long serialVersionUID = 1L;

	private User usuario = new User();

	private String nombre = "";
	private String apellidos = "";
	private String email = "";
	private String passAntigua = "";
	private String passNueva = "";
	private String repitePassNueva = "";

	public String getNombre() {
		if (usuario != null)
			return usuario.getName();

		return "";
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		if (usuario != null)
			return usuario.getSurname();

		return "";
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		if (usuario != null)
			return usuario.getEmail();

		return "";
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassAntigua() {
		return "";
	}

	public void setPassAntigua(String passAntigua) {
		this.passAntigua = passAntigua;
	}

	public String getPassNueva() {
		return "";
	}

	public void setPassNueva(String passNueva) {
		this.passNueva = passNueva;
	}

	public String getRepitePassNueva() {
		return "";
	}

	public void setRepitePassNueva(String repitePassNueva) {
		this.repitePassNueva = repitePassNueva;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public String cambiarDatos() {
		User resultado = Factories.services.createCambioUsuarioService().modificarDatos(
				usuario, nombre, apellidos, email);
		reiniciar();
		if(resultado != null)
			return Resultado.exito.name();

		return Resultado.error.name();
	}

	public String cambiarPass() {
		if (!passNueva.equals(repitePassNueva)) { // Si las contraseñas no
													// coinciden...
			// TODO request.setAttribute("mensaje",
			// "Las contraseñas no coinciden");
			return Resultado.fracaso.name();
		}
		if (!passAntigua.isEmpty() && !passAntigua.equals(usuario.getPassword())) { // Si no es la
														// contraseña
														// correcta...
			// TODO request.setAttribute("mensaje",
			// "La contraseña antigua introducida es inválida");
			return Resultado.fracaso.name();
		}
		
		User resultado = Factories.services.createCambioUsuarioService().modificarPass(
				usuario, passNueva);
		reiniciar();
		
		if (resultado == null)
			return Resultado.error.name();
		return Resultado.exito.name();
	}
	
	private void reiniciar() {
		setUsuario(new User());
		setNombre("");
		setApellidos("");
		setEmail("");
		setPassAntigua("");
		setPassNueva("");
		setRepitePassNueva("");
	}

}
