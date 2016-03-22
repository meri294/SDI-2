package com.sdi.presentation;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Carpeta;
import com.sdi.model.Contacto;
import com.sdi.model.Correo;
import com.sdi.model.Usuario;

@ManagedBean(name = "correos")
@SessionScoped
public class BeanCorreos implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Correo> borradores = new ArrayList<Correo>();
	private List<Correo> enviados = new ArrayList<Correo>();
	private List<Correo> eliminados = new ArrayList<Correo>();
	
	private Correo correo = new Correo();
	
	@SuppressWarnings("unused")
	private boolean borrador = false;
	
	private String idDestinatarios = "";

	private Usuario usuario = new Usuario();

	public List<Correo> getBorradores() {
		return borradores;
	}

	public void setBorradores(List<Correo> borradores) {
		this.borradores = borradores;
	}

	public List<Correo> getEnviados() {
		return enviados;
	}

	public void setEnviados(List<Correo> enviados) {
		this.enviados = enviados;
	}

	public List<Correo> getEliminados() {
		return eliminados;
	}

	public void setEliminados(List<Correo> eliminados) {
		this.eliminados = eliminados;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		this.correo = correo;
		if(correo != null && correo.getId()!= null) {
			if(correo.getCarpeta().equals(Carpeta.Borradores))
				setBorrador(true);
			else
				setBorrador(false);
			
			List<Contacto> contactosCorreo = correo.getDestinatarios();
			if(contactosCorreo.isEmpty())
				setIdDestinatarios("");
			else {
				String ids = contactosCorreo.get(0).getId().toString();
				for(int i = 1; i < contactosCorreo.size(); i++)
					ids += "," + contactosCorreo.get(i).getId();
				
				setIdDestinatarios(ids);
				
			}
		}
		else {
			setBorrador(false);
			setIdDestinatarios("");
		}
	}

	public String getIdDestinatarios() {
		return idDestinatarios;
	}

	public void setIdDestinatarios(String idDestinatarios) {
		this.idDestinatarios = idDestinatarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setBorrador(boolean borrador) {
		this.borrador = borrador;
	}

	/**
	 * Reinicia los datos del bean en funcion del usuario en sesion.
	 * 
	 * @param u : Usuario en control de la sesion.
	 */
	public void reiniciar(Usuario u) {
		usuario = u;
		reiniciarCorreo();
		if (u != null) {
			recogerBorradores();
			recogerEnviados();
			recogerEliminados();
		}
	}

	private void recogerEliminados() {
		eliminados = Factories.persistence.createCorreoDao()
				.getLoginCarpetaCorreos(usuario.getLogin(), Carpeta.Eliminados);

	}

	private void recogerEnviados() {
		enviados = Factories.persistence.createCorreoDao()
				.getLoginCarpetaCorreos(usuario.getLogin(), Carpeta.Enviados);

	}

	private void recogerBorradores() {
		borradores = Factories.persistence.createCorreoDao()
				.getLoginCarpetaCorreos(usuario.getLogin(), Carpeta.Borradores);

	}
	
	/**
	 * Persiste el correo almacenado en el bean en la carpeta de borradores
	 * del usuario y reinicia el correo.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String guardarBorrador() {
		String resultado = guardarCorreo(Carpeta.Borradores);
		borradores.add(correo);
		reiniciarCorreo();
		return resultado;
	}
	
	/**
	 * Persiste el correo almacenado en el bean en la carpeta de enviados
	 * del usuario y reinicia el correo.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String enviar() {
		String resultado = guardarCorreo(Carpeta.Enviados);
		enviados.add(correo);
		reiniciarCorreo();
		return resultado;
	}

	/**
	 * Persiste el correo almacenado en el bean en la carpeta pasada.
	 * 
	 * @param carpeta : Carpeta en la que se quiere guardar el correo.
	 * @return Devuelve el resultado de la accion.
	 */
	private String guardarCorreo(Carpeta carpeta) {
		
		List<Contacto> contactosCorreo = sacarContactosDeIds();
		
		if(contactosCorreo == null)
			return Resultado.fracaso.name();
		
		correo.setLogin_Usuario(usuario.getLogin());
		correo.setFechahora(new Date(System.currentTimeMillis()));
		correo.setDestinatarios(contactosCorreo);
		correo.setCarpeta(carpeta);
		
		Factories.persistence.createCorreoDao().save(correo);
		
		Log.debug("Se ha guardado un nuevo correo por el usuario [%s] "
				+ "en la carpeta [%s]", usuario.getLogin(), carpeta);
		
		return Resultado.exito.name();
	}

	/**
	 * Saca una lista de contactos en base al string con ids almacenado.
	 * 
	 * @return Devuelve la lista de contactos. Si esta el id de algun contacto
	 * que no pertenezca al usuario, se devuelve null.
	 */
	private List<Contacto> sacarContactosDeIds() {
		String[] idContactos = idDestinatarios.split(",");
		
		List<Contacto> contactosCorreo = new ArrayList<Contacto>();
		
		for(String id : idContactos) {
			Contacto contactoPivote = Factories.persistence.createContactoDao()
					.getContacto(Integer.valueOf(id.trim()), usuario);
			if(contactoPivote == null) {
				Log.error("El usuario [%s] ha intentado enviar un correo "
						+ "a un contacto que no existe en ninguna de "
						+ "sus agendas, de id [%s]", usuario.getLogin(), 
						id.trim());
				//TODO request.setAttribute("mensaje", "No existe ningún contacto con id " + id.trim() + " en su agenda.");
				return null;
			}
			contactosCorreo.add(contactoPivote);
		}
		return contactosCorreo;
	}
	
	public void reiniciarCorreo() {
		setCorreo(new Correo());
	}
	
	public String getEmailDestinatarios() {
		List<Contacto> destinatarios = correo.getDestinatarios();
		if(destinatarios.isEmpty())
			return "";
		String emailDestinatarios = destinatarios.get(0).getEmail();
		for(int i = 1; i < destinatarios.size(); i++)
			emailDestinatarios += ", " + destinatarios.get(i).getEmail();
		
		return emailDestinatarios;
	}
	
	public boolean isBorrador() {
		return correo.getCarpeta().equals(Carpeta.Borradores);
	}
	
	/**
	 * Se persiste las modificaciones que se hayan hecho al correo, guardado
	 * como borrador.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String editarBorrador() {
		
		List<Contacto> contactos = sacarContactosDeIds();
		if(contactos == null)
			return Resultado.fracaso.name();
		
		correo.setDestinatarios(contactos);
		correo.setFechahora(new Date(System.currentTimeMillis()));
		
		Factories.persistence.createCorreoDao().update(correo);
		Log.debug("Se ha editado el correo [%s] y se ha guardado en "
				+ "la carpeta [%s]", correo.getId(), Carpeta.Borradores);
		
		reiniciarCorreo();
		
		return Resultado.exito.name();
		
	}
	
	/**
	 * Se crea una copia del correo almacenado en el bean y se guarda en la
	 * carpeta de enviados del usuario.
	 * 
	 * @return Devuelve el resultado de la accion.
	 */
	public String enviarBorrador() {
		
		List<Contacto> contactos = sacarContactosDeIds();
		if(contactos == null)
			return Resultado.fracaso.name();
		
		Correo correoEnviado = new Correo();
		correoEnviado.setAsunto(correo.getAsunto());
		correoEnviado.setDestinatarios(contactos);
		correoEnviado.setCarpeta(Carpeta.Enviados);
		correoEnviado.setCuerpo(correo.getCuerpo());
		correoEnviado.setFechahora(new Date(System.currentTimeMillis()));
		correoEnviado.setLogin_Usuario(correo.getLogin_Usuario());
		
		Factories.persistence.createCorreoDao().save(correoEnviado);
		Log.debug("Se ha enviado el borrador [%s]", correo.getId());
		
		enviados.add(correoEnviado);
		
		reiniciarCorreo();
		
		return Resultado.exito.name();
		
	}
}
