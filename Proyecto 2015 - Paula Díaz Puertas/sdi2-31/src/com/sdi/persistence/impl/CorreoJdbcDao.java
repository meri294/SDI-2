package com.sdi.persistence.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sdi.model.Carpeta;
import com.sdi.model.Contacto;
import com.sdi.model.Correo;
import com.sdi.persistence.CorreoDao;
import com.sdi.persistence.Jdbc;
import com.sdi.persistence.exception.PersistenceException;

public class CorreoJdbcDao extends ImplementadorBase implements CorreoDao {
	
	public CorreoJdbcDao() {
		super();
	}

	@Override
	public List<Correo> getCorreos() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Correo> correos = new ArrayList<Correo>();

		try {
			ps = c.prepareStatement("select * from public.correos");
			rs = ps.executeQuery();

			while (rs.next()) {
				Correo correo = new Correo();
				int id = rs.getInt("ID");
				correo.setId(id);
				correo.setFechahora(rs.getDate("FECHAHORA"));
				correo.setAsunto(rs.getString("ASUNTO"));
				correo.setCuerpo(rs.getString("CUERPO"));
				correo.setCarpeta(Carpeta.valueOf(rs.getString("CARPETA")));
				correo.setLogin_Usuario(rs.getString("LOGIN_USUARIO"));

				List<Contacto> contactos = getDestinatariosCorreo(id, false);

				correo.setDestinatarios(contactos);

				correos.add(correo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(rs, ps, c);
		}

		return correos;
	}
	
	/**
	 * Devuelve una lista con los destinatarios del correo.
	 * Cierra la conexion si asi se le dice.
	 * 
	 * @param idCorreo : Id del correo del que se quiere obtener 
	 * los destinatarios.
	 * @param cerrarConnection : Decide si se cierra o no la conexion.
	 * @return Si no existe ningun correo con el id pasado, se devuelve una
	 * lista vacia.
	 */
	public List<Contacto> getDestinatariosCorreo(int idCorreo, boolean cerrarConnection) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Contacto> contactos = new ArrayList<Contacto>();

		try {
			ps = c.prepareStatement("SELECT * FROM \"PUBLIC\".\"CONTACTOS\" INNER JOIN \"PUBLIC\".\"DESTINATARIOS\" on CONTACTOS.ID = DESTINATARIOS.ID_CONTACTO where DESTINATARIOS.ID_CORREO = ?");
			ps.setInt(1, idCorreo);
			rs = ps.executeQuery();

			while (rs.next()) {
				Contacto contacto = new Contacto();
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
		} finally {
			Jdbc.close(rs, ps);
			if(cerrarConnection)
				Jdbc.close(c);
		}

		return contactos;
	}

	@Override
	public List<Contacto> getDestinatariosCorreo(int idCorreo) {
		return getDestinatariosCorreo(idCorreo, true);
	}

	@Override
	public List<Correo> getLoginCorreos(String login) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Correo> correos = new ArrayList<Correo>();

		try {
			ps = c.prepareStatement("select * from public.correos where(LOGIN_USUARIO= ?)");
			ps.setString(1, login);
			rs = ps.executeQuery();

			while (rs.next()) {
				Correo correo = new Correo();
				int id = rs.getInt("ID");
				correo.setId(id);
				correo.setFechahora(rs.getDate("FECHAHORA"));
				correo.setAsunto(rs.getString("ASUNTO"));
				correo.setCuerpo(rs.getString("CUERPO"));
				correo.setCarpeta(Carpeta.valueOf(rs.getString("CARPETA")));
				correo.setLogin_Usuario(rs.getString("LOGIN_USUARIO"));

				List<Contacto> destinatarios = getDestinatariosCorreo(id, false);
				correo.setDestinatarios(destinatarios);

				correos.add(correo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(rs, ps, c);
		}

		return correos;
	}

	@Override
	public List<Correo> getLoginCarpetaCorreos(String login, Carpeta carpeta) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<Correo> correos = new ArrayList<Correo>();

		try {
			ps = c.prepareStatement("select * from public.correos where(LOGIN_USUARIO= ? AND CARPETA= ?)");
			ps.setString(1, login);
			ps.setString(2, carpeta.name());
			rs = ps.executeQuery();

			while (rs.next()) {
				Correo correo = new Correo();
				int id = rs.getInt("ID");
				correo.setId(id);
				correo.setFechahora(rs.getDate("FECHAHORA"));
				correo.setAsunto(rs.getString("ASUNTO"));
				correo.setCuerpo(rs.getString("CUERPO"));
				correo.setCarpeta(Carpeta.valueOf(rs.getString("CARPETA")));
				correo.setLogin_Usuario(rs.getString("LOGIN_USUARIO"));

				List<Contacto> destinatarios = getDestinatariosCorreo(id, false);
				correo.setDestinatarios(destinatarios);

				correos.add(correo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(rs, ps, c);
		}

		return correos;
	}

	@Override
	public int save(Correo a) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		int id_insertado = 0;
		try {

			ps = c.prepareStatement("insert into correos (fechahora, asunto, "
					+ "cuerpo, carpeta, login_usuario) "
							+ "values (?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);

			ps.setDate(1, a.getFechahora());
			ps.setString(2, a.getAsunto());
			ps.setString(3, a.getCuerpo());
			ps.setString(4, a.getCarpeta().name());
			ps.setString(5, a.getLogin_Usuario());

			ps.executeUpdate();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				id_insertado = generatedKeys.getInt(1);
			}
			
			a.setId(id_insertado);

			// Al salvarse por primera vez el correo, todos los destinatarios
			// son nuevos

			List<Contacto> destinatarios = a.getDestinatarios();

			for (Contacto c : destinatarios)
				saveDestinatario(c, a);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
		return id_insertado;

	}

	@Override
	public void update(Correo a) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;

		try {
			ps = c.prepareStatement("update correos "
					+ "set fechahora = ?, asunto = ?, cuerpo = ?, carpeta = ?"
					+ " where id=?");

			ps.setDate(1, a.getFechahora());
			ps.setString(2, a.getAsunto());
			ps.setString(3, a.getCuerpo());
			ps.setString(4, a.getCarpeta().name());
			ps.setLong(5, a.getId());

			ps.executeUpdate();

			// Hay que comprobar los destinatarios que se han añadido y los que
			// se han eliminado...

			List<Contacto> destinatariosNuevos = a.getDestinatarios();
			List<Contacto> destinatariosAntiguos = getDestinatariosCorreo(
					a.getId(), false);

			List<Contacto> destinatariosAñadidos = new ArrayList<Contacto>();
			List<Contacto> destinatariosBorrados = new ArrayList<Contacto>();

			// Si un contacto esta en destinatariosNuevos pero no en
			// destinatariosAntiguos, significa que ese destinatario acaba
			// de ser añadido
			for (Contacto c : destinatariosNuevos) {
				if (!destinatariosAntiguos.contains(c))
					destinatariosAñadidos.add(c);
			}

			// Si un contacto esta en destinatariosAntiguos pero no en
			// destinatariosNuevos, significa que ese destinatario acaba
			// de ser borrado
			for (Contacto c : destinatariosAntiguos) {
				if (!destinatariosNuevos.contains(c))
					destinatariosBorrados.add(c);
			}

			for (Contacto c : destinatariosAñadidos)
				saveDestinatario(c, a);

			for (Contacto c : destinatariosBorrados)
				deleteDestinatario(c, a);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
	}

	@Override
	public void delete(int id) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;

		try {
			ps = c.prepareStatement("delete from correos where id = ?");

			ps.setLong(1, id);

			ps.executeUpdate();

			deleteDestinatarioByCorreo(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
			Jdbc.close(c);
		}

	}

	@Override
	public void deleteCorreosLogin(String login) {
		comprobarConnectionInicializada();

		PreparedStatement ps = null;

		try {

			List<Correo> correos = this.getLoginCorreos(login);

			for (Correo c : correos)
				deleteDestinatarioByCorreo(c.getId());

			ps = c.prepareStatement("delete from correos where login_usuario = ?");

			ps.setString(1, login);

			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
			Jdbc.close(c);
		}

	}

	@Override
	public void deleteCorreos() {
		comprobarConnectionInicializada();

		PreparedStatement ps = null;

		try {
			ps = c.prepareStatement("delete from correos");

			ps.executeUpdate();

			deleteAllDestinatarios();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
			Jdbc.close(c);
		}

	}

	@Override
	public void reiniciaID() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;

		try {
			ps = c.prepareStatement("ALTER TABLE PUBLIC.CORREOS ALTER COLUMN 'ID' RESTART WITH 1");

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
			Jdbc.close(c);
		}
	}

	/**
	 * Persiste el destinatario
	 * 
	 * IMPORTANTE: NO CIERRA LA CONEXION.
	 * 
	 * @param contacto : Destinatario a persistir.
	 * @param correo : Correo con el que esta relaccionado.
	 */
	private void saveDestinatario(Contacto contacto, Correo correo) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;

		try {
			ps = c.prepareStatement("insert into destinatarios (id_correo, id_contacto)"
					+ "values (?, ?);");

			ps.setInt(1, correo.getId());
			ps.setInt(2, contacto.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema"
					+ ps.toString(), e);
		} finally {
			Jdbc.close(ps);
		}

	}

	/**
	 * Elimina el destinatario.
	 * 
	 * IMPORTANTE: NO CIERRA LA CONEXION.
	 * 
	 * @param contacto : Destinatario a eliminar.
	 * @param correo : Correo con el que esta relacionado.
	 */
	private void deleteDestinatario(Contacto contacto, Correo correo) {
		comprobarConnectionInicializada();

		PreparedStatement ps = null;

		try {
			ps = c.prepareStatement("delete from destinatarios where id_correo = ? and id_contacto = ?");

			ps.setInt(1, correo.getId());
			ps.setInt(2, contacto.getId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
		}

	}

	/**
	 * Elimina todos los destinatarios del correo.
	 * 
	 * IMPORTANTE: NO CIERRA LA CONEXION
	 * 
	 * @param idCorreo : Correo del que se quieren eliminar los destinatarios.
	 */
	private void deleteDestinatarioByCorreo(int idCorreo) {
		comprobarConnectionInicializada();

		PreparedStatement ps = null;

		try {
			ps = c.prepareStatement("delete from destinatarios where id_correo = ?");

			ps.setInt(1, idCorreo);

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
			Jdbc.close(c);
		}

	}

	/**
	 * Elimina todos los destinatarios.
	 * 
	 * IMPORTANTE: NO CIERRA LA CONEXION
	 */
	private void deleteAllDestinatarios() {
		comprobarConnectionInicializada();

		PreparedStatement ps = null;

		try {
			ps = c.prepareStatement("delete from destinatarios");

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(ps);
		}

	}

	@Override
	public Correo getCorreo(int idCorreo) {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		Correo correo = null;

		try {
			ps = c.prepareStatement("select * from public.correos where id = ?");
			ps.setInt(1, idCorreo);
			rs = ps.executeQuery();

			while (rs.next()) {
				correo = new Correo();
				correo.setId(idCorreo);
				correo.setFechahora(rs.getDate("FECHAHORA"));
				correo.setAsunto(rs.getString("ASUNTO"));
				correo.setCuerpo(rs.getString("CUERPO"));
				correo.setCarpeta(Carpeta.valueOf(rs.getString("CARPETA")));
				correo.setLogin_Usuario(rs.getString("LOGIN_USUARIO"));

				List<Contacto> contactos = getDestinatariosCorreo(idCorreo, false);

				correo.setDestinatarios(contactos);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new PersistenceException("Invalid SQL or database schema", e);
		} finally {
			Jdbc.close(rs, ps, c);
		}

		return correo;
	}

	@Override
	public void deleteAll() {
		comprobarConnectionInicializada();
		
		PreparedStatement ps = null;
		
		try {
			ps = c.prepareStatement("delete from correos");
			ps.executeUpdate();
			ps.close();
			ps = c.prepareStatement("delete from destinatarios");
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
