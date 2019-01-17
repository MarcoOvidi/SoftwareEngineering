package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Room;
import model.Sensor;

public class SensorQuerySet {

	/**
	 * Insert a sensor in DB that is just placed in a room
	 * 
	 * @param sensor
	 * @throws DatabaseException
	 */
	public static void createPlacedSensor(Sensor sensor) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("INSERT INTO sensor(ID_sensor,status,threshold,type,ID_room) value(?,?,?,?,?);");
			ps.setInt(1, sensor.getId());
			ps.setBoolean(2, sensor.getStatus());
			ps.setInt(3, sensor.getTreshold());
			ps.setInt(4, sensor.getType());
			ps.setInt(5, sensor.getIdRoom());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
		}
	}
	
	/**
	 * Insert a sensor not placed 
	 * 
	 * @param sensor
	 * @throws DatabaseException
	 */
	public static void createUnplacedSensor(Sensor sensor) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		try {
			ps = con.prepareStatement("INSERT INTO sensor(ID_sensor,status,threshold,type) value(?,?,?,?,);");
			ps.setInt(1, sensor.getId());
			ps.setBoolean(2, sensor.getStatus());
			ps.setInt(3, sensor.getTreshold());
			ps.setInt(4, sensor.getType());

			ps.executeUpdate();

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
		}
	}
	
	
	/**
	 * Get all sensors
	 * @throws DatabaseException
	 */
	public static LinkedList<Sensor> getSensors(int i, Room room) throws DatabaseException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<Sensor> sensors = new LinkedList<Sensor>();
		
		try {
			
			ps = con.prepareStatement("SELECT * FROM sensor WHERE id>? AND id<?;");
			ps.setInt(1, i*10000);
			ps.setInt(2, (i+1)*10000);

			rs = ps.executeQuery();
			while(rs.next()) {
				sensors.add(new Sensor(rs.getInt("ID_sensor"), rs.getBoolean("status"), rs.getInt("type"), rs.getShort("threshold"),0, rs.getInt("ID_room"), room));
			}

		} catch (SQLException e) {
			throw new DatabaseException("Errore di esecuzione query", e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				DBConnection.logDatabaseException(new DatabaseException("Errore sulle risorse", e));
			}
		}
		return sensors;
	}

}
