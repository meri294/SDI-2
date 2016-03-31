package com.sdi.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.sdi.infrastructure.Factories;
import com.sdi.persistence.UserDao;

@FacesValidator("LoginValidator")
public class LoginValidator implements Validator{

    private UserDao dao = Factories.persistence.createUserDao();
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value)
	    throws ValidatorException {
	
	if(dao.findByLogin(value.toString()) != null){
		ResourceBundle bundle = context.getApplication()
				.getResourceBundle(context, "msgs");
		FacesMessage msg = 
			new FacesMessage(bundle.getString("error_loginExistente"));
		
		throw new ValidatorException(msg);

	}
	
    }

}
