package com.sdi.presentation;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Usuario;

@ManagedBean(name = "cambio")
@SessionScoped
public class BeanCambio implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	private String nombre = "";
	private String apellidos = "";
	private String email = "";
	private String passAntigua = "";
	private String passNueva = "";
	private String repitePassNueva = "";

	public String getNombre() {
		if (usuario != null)
			return usuario.getNombre();

		return "";
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		if (usuario != null)
			return usuario.getApellidos();

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Modifica los datos del usuario y reinicia los datos almacenados en el 
	 * bean.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String cambiarDatos() {
		Usuario resultado = Factories.services.createCambioUsuarioService()
				.modificarDatos(usuario, nombre, apellidos, email);
		reiniciar();
		if(resultado != null)
			return Resultado.exito.name();

		return Resultado.error.name();
	}

	/**
	 * Comprueba que passNueva y repitePassNueva sean iguales.
	 * Comprueba que passAntigua coincide con la contraseña del usuario.
	 * 
	 * Si todo es correcto, guarda la nueva contraseña del usuario y reinicia
	 * los datos del bean.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String cambiarPass() {
		if (!passNueva.equals(repitePassNueva)) { // Si las contraseñas no
													// coinciden...
			// TODO request.setAttribute("mensaje",
			// "Las contraseñas no coinciden");
			return Resultado.fracaso.name();
		}
		if (!passAntigua.isEmpty() && 
				!passAntigua.equals(usuario.getPasswd())) { // Si no es la
														// contraseña
														// correcta...
			// TODO request.setAttribute("mensaje",
			// "La contraseña antigua introducida es inválida");
			return Resultado.fracaso.name();
		}
		
		Usuario resultado = Factories.services.createCambioUsuarioService()
				.modificarPass(usuario, passNueva);
		reiniciar();
		
		if (resultado == null)
			return Resultado.error.name();
		return Resultado.exito.name();
	}
	
	/**
	 * Reinicia los datos del bean.
	 */
	private void reiniciar() {
		setUsuario(new Usuario());
		setNombre("");
		setApellidos("");
		setEmail("");
		setPassAntigua("");
		setPassNueva("");
		setRepitePassNueva("");
	}

}
