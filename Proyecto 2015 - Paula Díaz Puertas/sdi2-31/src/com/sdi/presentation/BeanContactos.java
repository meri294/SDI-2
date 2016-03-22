package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Contacto;
import com.sdi.model.Rol;
import com.sdi.model.Usuario;
import com.sdi.persistence.exception.AlreadyPersistedException;

@ManagedBean(name = "contactos")
@SessionScoped
public class BeanContactos implements Serializable {

	// Este bean tiene que ser reiniciado cada vez que se cambie de usuario

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;

	private List<Contacto> contactosPublicos = new ArrayList<Contacto>();
	private List<Contacto> contactosPrivados = new ArrayList<Contacto>();
	
	private Contacto contacto = new Contacto();

	public List<Contacto> getContactosPublicos() {
		return contactosPublicos;
	}

	public void setContactosPublicos(List<Contacto> contactosPublicos) {
		this.contactosPublicos = contactosPublicos;
	}

	public List<Contacto> getContactosPrivados() {
		return contactosPrivados;
	}

	public void setContactosPrivados(List<Contacto> contactosPrivados) {
		this.contactosPrivados = contactosPrivados;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	/**
	 * Reinicia los datos del bean segun el usuario que este en sesion.
	 * Si el usuario es publico, deja los contactos privados y publicos como
	 * listas vacias. Si no, recoge los contactos pertinentes de la base de 
	 * datos.
	 * 
	 * @param u : Usuario en posesion de la sesion.
	 */
	public void reiniciar(Usuario u) {
		contacto = new Contacto();
		usuario = u;
		if (u == null) {
			setContactosPublicos(new ArrayList<Contacto>());
			setContactosPrivados(new ArrayList<Contacto>());
		}

		else {
			recogerContactosPublicos();

			if (u.getRol() == Rol.cliente)
				contactosPrivados = Factories.persistence.createContactoDao()
						.getLoginContactos(u.getLogin());
		}
	}
	
	/**
	 * Persiste el contacto almacenado en el bean y lo reinicia.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String guardarContacto() {
		
		contacto.setAgenda_Usuario(usuario.getLogin());
		
		try {
			Factories.persistence.createContactoDao().save(contacto);
		} catch (AlreadyPersistedException e) {
			Log.error("Se ha intentado crear un contacto ya existente "
					+ "con correo [%s] en la agenda de [%s]", 
					contacto.getEmail(), usuario.getLogin());
			//TODO request.setAttribute("mensaje", "El contacto ya existe en tu agenda");
			return Resultado.fracaso.name();
		}
		Log.debug("El usuario [%s] crea un nuevo contacto con email [%s]"
				+ ", nombre [%s], apellidos [%s], direccion [%s] y ciudad [%s]",
				usuario.getLogin(), contacto.getEmail(), contacto.getNombre(), 
				contacto.getApellidos(), contacto.getDireccion(), 
				contacto.getCiudad());
		
		recogerContactosPublicos();
		
		if(usuario.getRol().equals(Rol.cliente))
			contactosPrivados.add(contacto);
		
		contacto = new Contacto();
		
		return Resultado.exito.name();
		
	}
	
	/**
	 * Recoge de la base de datos los contactos publicos existentes y los 
	 * almacena en el bean.
	 */
	private void recogerContactosPublicos() {
		contactosPublicos = Factories.persistence.createContactoDao()
				.getAdminContactos();
	}

}
