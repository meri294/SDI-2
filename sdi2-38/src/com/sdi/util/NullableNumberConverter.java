package com.sdi.util;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("NullableNumberConverter")
public class NullableNumberConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	if(value.isEmpty())
	    return null;
	
	try {
	Double numero = Double.parseDouble(value);

	return numero;
	
	} catch(NumberFormatException e) {
	    
	    ResourceBundle bundle = context.getApplication()
			.getResourceBundle(context, "msgs");
	    
	    throw new ConverterException(new FacesMessage(bundle.getString("mensaje_valorInvalido")));
	}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
	if(value == null)
	    return "";
	
	return value.toString();
    }

}
