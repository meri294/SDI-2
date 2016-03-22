package com.sdi.model;

import java.sql.Date;
import java.util.List;

public class Correo {
	
	private Integer id;
	private Date fechahora;
	private String asunto;
	private String cuerpo;
	private Carpeta carpeta;
	private String login_Usuario;
	
	private List<Contacto> destinatarios;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getFechahora() {
		return fechahora;
	}
	public void setFechahora(Date d) {
		this.fechahora = d;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public Carpeta getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(Carpeta carpeta) {
		this.carpeta = carpeta;
	}
	public String getLogin_Usuario() {
		return login_Usuario;
	}
	public void setLogin_Usuario(String login_Usuario) {
		this.login_Usuario = login_Usuario;
	}

	public List<Contacto> getDestinatarios() {
		return destinatarios;
	}
	
	public void setDestinatarios(List<Contacto> destinatarios) {
		if(destinatarios.isEmpty())
			throw new IllegalArgumentException("Todos los correos tienen que tener al menos un destinatario");
		
		this.destinatarios = destinatarios;
	}

	public Contacto getPrimerDestinatario() {
		return destinatarios.get(0);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Correo other = (Correo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
