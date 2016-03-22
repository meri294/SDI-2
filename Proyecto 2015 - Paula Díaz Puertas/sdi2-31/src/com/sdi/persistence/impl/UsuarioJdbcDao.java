package com.sdi.persistence.impl;

import java.sql.*;
import java.util.*;

import com.sdi.model.Rol;
import com.sdi.model.Usuario;
import com.sdi.persistence.Jdbc;
import com.sdi.persistence.UsuarioDao;
import com.sdi.persistence.exception.*;


/**
 * Implementaci��n de la interfaz de fachada al servicio de persistencia para
 * Alumnos. En este caso es Jdbc pero podr��a ser cualquier otra tecnologia 
 * de persistencia, por ejemplo, la que veremos m��s adelante JPA 
 * (mapeador de objetos a relacional)
 * 
 * @author Enrique
 *
 */
public class UsuarioJdbcDao extends ImplementadorBase implements UsuarioDao {
	
	public UsuarioJdbcDao() {
		super();
	}

	@Override
	public List<Usuario> getUsuarios() throws PersistenceException{
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			ps = c.prepareStatement("select * from public.usuarios");
			rs = ps.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("LOGIN"));
				usuario.setPasswd(rs.getString("PASSWD"));
				usuario.setRol(Rol.valueOf(rs.getString("ROL")));
				usuario.setActivo(rs.getBoolean("ACTIVO"));
				usuario.setNombre(rs.getString("NOMBRE"));
				usuario.setApellidos(rs.getString("APELLIDOS"));
				usuario.setEmail(rs.getString("EMAIL"));
						
				usuarios.add(usuario);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally  {
			Jdbc.close(rs, ps, c);
		}
		
		return usuarios;
	}

	@Override
	public void save(Usuario a) throws AlreadyPersistedException, PersistenceException {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		int rows = 0;
		
		try {
			
			ps = c.prepareStatement(
					"insert into public.usuarios (login, passwd, rol, activo, "
					+ "nombre, apellidos, email) " +
					"values (?, ?, ?, ?, ?, ?, ?)");
			
			ps.setString(1, a.getLogin());
			ps.setString(2, a.getPasswd());
			ps.setString(3, a.getRol().name());
			ps.setBoolean(4, a.isActivo());
			ps.setString(5, a.getNombre());
			ps.setString(6, a.getApellidos());
			ps.setString(7, a.getEmail());
				
			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new AlreadyPersistedException("Usuarioo " + a + " already persisted");
			} 
			
			 ps.close();

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
	public void update(Usuario a) throws NotPersistedException, PersistenceException {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		int rows = 0;
		
		try {
			ps = c.prepareStatement("update public.usuarios set passwd = ?, "
					+ "activo = ?, nombre = ?, apellidos = ?, email = ?" +
					"where login = ?");
			
			ps.setString(1, a.getPasswd());
			ps.setBoolean(2, a.isActivo());
			ps.setString(3, a.getNombre());
			ps.setString(4, a.getApellidos());
			ps.setString(5, a.getEmail());
			ps.setString(6, a.getLogin());
			

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new NotPersistedException("Usuarios " + a + " not found");
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
	public void delete(String login) throws NotPersistedException, PersistenceException {
		comprobarConnectionInicializada();

		PreparedStatement ps = null;
		int rows = 0;
		
		try {
			ps = c.prepareStatement("delete from usuarios where login = ?");
			
			ps.setString(1, login);

			rows = ps.executeUpdate();
			if (rows != 1) {
				throw new NotPersistedException("Usuario " + login + " not found");
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
	public Usuario findByLogin(String login) throws PersistenceException{
		comprobarConnectionInicializada();

		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Usuario usuario = null;
		
		try {
			ps = c.prepareStatement("select * from public.usuarios where login = ?");
			ps.setString(1, login);
			
			rs = ps.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				
				usuario.setLogin(rs.getString("LOGIN"));
				usuario.setPasswd(rs.getString("PASSWD"));
				usuario.setRol(Rol.valueOf(rs.getString("ROL")));
				usuario.setActivo(rs.getBoolean("ACTIVO"));
				usuario.setNombre(rs.getString("NOMBRE"));
				usuario.setApellidos(rs.getString("APELLIDOS"));
				usuario.setEmail(rs.getString("EMAIL"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(rs, ps, c);
		}
		
		return usuario;
	}
	
	@Override
	public void deleteUsuarios(){
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		
		try {
			ps = c.prepareStatement("delete from usuarios where rol != ?");
			ps.setString(1, Rol.administrador.name());
			
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
	public List<Usuario> getUsuariosInactiveFirst() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			ps = c.prepareStatement("select * from public.usuarios order by activo");
			rs = ps.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("LOGIN"));
				usuario.setPasswd(rs.getString("PASSWD"));
				usuario.setRol(Rol.valueOf(rs.getString("ROL")));
				usuario.setActivo(rs.getBoolean("ACTIVO"));
				usuario.setNombre(rs.getString("NOMBRE"));
				usuario.setApellidos(rs.getString("APELLIDOS"));
				usuario.setEmail(rs.getString("EMAIL"));
						
				usuarios.add(usuario);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally  {
			Jdbc.close(rs, ps, c);
		}
		
		return usuarios;
	}

	@Override
	public boolean getActivoByLogin(String login) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		boolean activo = false;
		
		try {
			ps = c.prepareStatement("select activo from public.usuarios where login = ?");
			ps.setString(1, login);
			
			rs = ps.executeQuery();
			if (rs.next())
				activo = rs.getBoolean("ACTIVO");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		}
		finally  {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
		
		return activo;
	}

	@Override
	public void deleteAll() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		
		try {
			ps = c.prepareStatement("delete from usuarios");
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
	public List<Usuario> getClientesInactiveFirst() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			ps = c.prepareStatement("select * from public.usuarios where rol = '"
					+ Rol.cliente + "' order by activo");
			rs = ps.executeQuery();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setLogin(rs.getString("LOGIN"));
				usuario.setPasswd(rs.getString("PASSWD"));
				usuario.setRol(Rol.valueOf(rs.getString("ROL")));
				usuario.setActivo(rs.getBoolean("ACTIVO"));
				usuario.setNombre(rs.getString("NOMBRE"));
				usuario.setApellidos(rs.getString("APELLIDOS"));
				usuario.setEmail(rs.getString("EMAIL"));
						
				usuarios.add(usuario);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally  {
			Jdbc.close(rs, ps, c);
		}
		
		return usuarios;
	}

}
