package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.model.UserStatus;
import com.sdi.util.CifradoMD5;

@ManagedBean(name = "registro")
@ViewScoped
//Tiene que ser view scope porque utiliza ajax
public class BeanRegistro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String login = "";
	private String nombre = "";
	private String apellidos = "";
	private String email = "";
	private String pass = "";
	private String repitePass = "";
	
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellidos() {
		return apellidos;
	}
	
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public String getEmail() {
	    return email;
	}

	public void setEmail(String email) {
	    this.email = email;
	}

	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getRepitePass() {
		return repitePass;
	}
	
	public void setRepitePass(String repitePass) {
		this.repitePass = repitePass;
	}
	
	public String registrar() {
		
		User u = Factories.persistence.createUserDao().findByLogin(login);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		
		u = new User();
		u.setLogin(login);
		u.setName(nombre);
		u.setSurname(apellidos);
		u.setPassword(CifradoMD5.getStringMessageDigest(pass));
		u.setEmail(email);
		u.setStatus(UserStatus.ACTIVE);
		
		try {
			Factories.persistence.createUserDao().save(u);
		} catch (Exception e) {
			Log.error("Ha ocurrido un error intentando persistir al usuario [%s]: %s", login);
			FacesMessage message= new FacesMessage(FacesMessage.SEVERITY_ERROR,
					bundle.getString("error_guardar"),
					bundle.getString("error_guardar"));
			facesContext.addMessage(null, message);
			e.printStackTrace();
			return Resultado.error.name();
		}
		Log.info("Se ha creado un nuevo usuario cliente con login [%s]", login);
		FacesMessage message=new FacesMessage(FacesMessage.SEVERITY_INFO,
				bundle.getString("info_registroCorrecto"),
				bundle.getString("info_registroCorrecto"));
		facesContext.addMessage(null, message);
		return Resultado.exito.name();
	}
	
}
