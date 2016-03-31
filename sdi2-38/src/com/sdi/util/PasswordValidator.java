package com.sdi.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("PasswordValidator")
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component,
	    Object value) throws ValidatorException {

	String password = value.toString();

	UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
		.get("repitePass");
	String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
		.toString();

	if (!password.equals(confirmPassword)) {
	    uiInputConfirmPassword.setValid(false);
	    
	    ResourceBundle bundle = context.getApplication()
			.getResourceBundle(context, "msgs");
	    
	    throw new ValidatorException(new FacesMessage(
		    bundle.getString("error_contraDistinta")));
	}

    }

}
