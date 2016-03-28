package com.sdi.presentation;

public enum ComprobacionFechaValida {

    OK("mensaje_fechasCorrectas"), SAC("mensaje_fechaSalidaAnteriorCierre"), LAS("mensaje_fechaLlegadaAnteriorSalida");
    
    private String idMensaje;

    private ComprobacionFechaValida(String idMensaje) {
	this.idMensaje = idMensaje;
    }
    
    public String getIdMensaje() {
	return idMensaje;
    }
    

}
