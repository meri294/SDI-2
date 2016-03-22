package com.sdi.persistence.impl;

import java.sql.Connection;
import java.sql.SQLException;

import com.sdi.persistence.Jdbc;

abstract class ImplementadorBase {
	
	protected Connection c;
	
	public ImplementadorBase() {
		try {
			c = Jdbc.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setConnection(Connection c) {
		this.c = c;
		
	}
	
	protected void comprobarConnectionInicializada() {
		if(c == null)
			throw new IllegalStateException("No puedes modificar una tabla sin asignar antes una conexion.");
	}

}
