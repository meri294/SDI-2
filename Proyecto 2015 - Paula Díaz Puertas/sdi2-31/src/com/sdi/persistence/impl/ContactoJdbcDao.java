package com.sdi.persistence.impl;

import java.sql.*;
import java.util.*;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Contacto;
import com.sdi.model.Rol;
import com.sdi.model.Usuario;
import com.sdi.persistence.ContactoDao;
import com.sdi.persistence.Jdbc;
import com.sdi.persistence.exception.*;

public class ContactoJdbcDao extends ImplementadorBase implements ContactoDao {
	
	public ContactoJdbcDao() {
		super();
	}
	
	@Override
	public List<Contacto> getContactos() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Contacto> contactos = new ArrayList<Contacto>();

		try {
			ps = c.prepareStatement("select * from public.contactos");
			rs = ps.executeQuery();

			while (rs.next()) {
				Contacto contacto= new Contacto();
				contacto.setId(rs.getInt("ID"));
				contacto.setEmail(rs.getString("EMAIL"));
				contacto.setNombre(rs.getString("NOMBRE"));
				contacto.setApellidos(rs.getString("APELLIDOS"));
				contacto.setDireccion(rs.getString("DIRECCION"));
				contacto.setCiudad(rs.getString("CIUDAD"));
				contacto.setAgenda_Usuario(rs.getString("AGENDA_USUARIO"));
			
						
				contactos.add(contacto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally  {
			Jdbc.close(rs, ps, c);
		}
		
		return contactos;
	}
	
	@Override
	public List<Contacto> getLoginContactos(String login) {
		comprobarConnectionInicializada();
	
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Contacto> contactos = new ArrayList<Contacto>();

		try {
			ps = c.prepareStatement("select * from public.contactos where(AGENDA_USUARIO='"+login+"')");
			rs = ps.executeQuery();

			while (rs.next()) {
				Contacto contacto= new Contacto();
				contacto.setId(rs.getInt("ID"));
				contacto.setEmail(rs.getString("EMAIL"));
				contacto.setNombre(rs.getString("NOMBRE"));
				contacto.setApellidos(rs.getString("APELLIDOS"));
				contacto.setDireccion(rs.getString("DIRECCION"));
				contacto.setCiudad(rs.getString("CIUDAD"));
				contacto.setAgenda_Usuario(rs.getString("AGENDA_USUARIO"));
			
						
				contactos.add(contacto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally  {
			Jdbc.close(rs, ps, c);
		}
		
		return contactos;
	}
	
	@Override
	public void save(Contacto a) throws AlreadyPersistedException {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		int rows = 0;
		int id_insertado = 0;
		
		try {
			ps = c.prepareStatement(
					"insert into contactos (email, nombre, apellidos, direccion, ciudad, agenda_usuario) " +
					"values (?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, a.getEmail());
			ps.setString(2, a.getNombre());
			ps.setString(3, a.getApellidos());
			ps.setString(4, a.getDireccion());
			ps.setString(5, a.getCiudad());
			ps.setString(6, a.getAgenda_Usuario());

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new AlreadyPersistedException("Contacto " + a + " already persisted");
			} 
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				id_insertado = generatedKeys.getInt(1);
			}
			
			a.setId(id_insertado);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
		
	}
	
	@Override
	public void update(Contacto a) throws NotPersistedException {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		int rows = 0;
		
		try {
			ps = c.prepareStatement(
					"update contactos " +
					"set email = ?, nombre = ?, apellidos = ?, direccion = ?, "
					+ "ciudad = ?, agenda_usuario = ?" +
					" where id = ?");
			
			ps.setString(1, a.getEmail());
			ps.setString(2, a.getNombre());
			ps.setString(3, a.getApellidos());
			ps.setString(4, a.getDireccion());
			ps.setString(5, a.getCiudad());
			ps.setString(6, a.getAgenda_Usuario());
			ps.setLong(7, a.getId());

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new NotPersistedException("Contacto " + a + " not found");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
	}
	
	@Override
	public void delete(int id) throws NotPersistedException {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		int rows = 0;
		
		try {
			ps = c.prepareStatement("delete from contactos where id = ?");
			
			ps.setLong(1, id);

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new NotPersistedException("Contacto " + id + " not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
		
	}

	@Override
	public void deleteContactos(){
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		
		try {
			ps = c.prepareStatement("delete from contactos");

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
	}
	
	@Override
	public void reiniciaID(){
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		
		try {
			ps = c.prepareStatement("ALTER TABLE PUBLIC.CONTACTOS ALTER COLUMN 'ID' RESTART WITH 1");

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
	}
	
	
	
	@Override
	public void deleteContactosAdmin(){
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		
		try {
			ps = c.prepareStatement("delete from contactos where agenda_usuario = 'admin'");

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
	}
	
	@Override
	public List<Contacto> getAdminContactos() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Contacto> contactos = new ArrayList<Contacto>();

		try {
			/*SELECT * FROM "PUBLIC"."CONTACTOS" inner join  
"PUBLIC"."USUARIOS" on "CONTACTOS"."AGENDA_USUARIO" = "USUARIOS"."LOGIN" where "USUARIOS"."ROL" = 'Administrador'*/
			
			
			ps = c.prepareStatement("SELECT * FROM \"PUBLIC\".\"CONTACTOS\" "
					+ "inner join \"PUBLIC\".\"USUARIOS\" on \"CONTACTOS\"."
					+ "\"AGENDA_USUARIO\" = \"USUARIOS\".\"LOGIN\" where "
					+ "\"USUARIOS\".\"ROL\" = '" + Rol.administrador + "'");
			rs = ps.executeQuery();

			while (rs.next()) {
				Contacto contacto= new Contacto();
				contacto.setId(rs.getInt("ID"));
				contacto.setEmail(rs.getString("EMAIL"));
				contacto.setNombre(rs.getString("NOMBRE"));
				contacto.setApellidos(rs.getString("APELLIDOS"));
				contacto.setDireccion(rs.getString("DIRECCION"));
				contacto.setCiudad(rs.getString("CIUDAD"));
				contacto.setAgenda_Usuario(rs.getString("AGENDA_USUARIO"));
			
						
				contactos.add(contacto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally  {
			Jdbc.close(rs, ps, c);
		}
		
		return contactos;
	}

	@SuppressWarnings("resource")
	@Override
	public Contacto getContacto(int idContacto, Usuario usuario) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Contacto contacto = null;

		try {
			
			//Recojo el contacto por medio de su id
			
			ps = c.prepareStatement("select * from contactos where id = ?");
			ps.setInt(1, idContacto);
			rs = ps.executeQuery();

			while (rs.next()) {
				//Compruebo si la agenda del contacto pertenece al usuario pasado
				//Si no pertenece, compruebo que al usuario al que pertenezca sea un administrador
				//Si no lo es, devuelvo null
				String agenda_usuario = rs.getString("AGENDA_USUARIO");
				if(!usuario.getLogin().equals(agenda_usuario)) {
					Usuario usuarioAgenda = Factories.persistence.createUsuarioDao().findByLogin(agenda_usuario);
					if(usuarioAgenda.getRol() != Rol.administrador) {
						Jdbc.close(rs, ps, c);
						return null;
					}
				}
				contacto= new Contacto();
				contacto.setId(rs.getInt("ID"));
				contacto.setEmail(rs.getString("EMAIL"));
				contacto.setNombre(rs.getString("NOMBRE"));
				contacto.setApellidos(rs.getString("APELLIDOS"));
				contacto.setDireccion(rs.getString("DIRECCION"));
				contacto.setCiudad(rs.getString("CIUDAD"));
				contacto.setAgenda_Usuario(agenda_usuario);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally  {
			Jdbc.close(rs, ps, c);
		}
		
		return contacto;
	}

	@Override
	public void deleteAll() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		
		try {
			ps = c.prepareStatement("delete from contactos");
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
		
	}
}
