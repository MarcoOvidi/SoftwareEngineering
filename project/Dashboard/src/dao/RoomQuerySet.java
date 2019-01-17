package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Room;
import model.Sensor;

public class RoomQuerySet {

	public static LinkedList<Sensor> getRoomSensors(int IdRoom, Room room) throws DatabaseException {
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
			ps = con.prepareStatement("SELECT * FROM sensor WHERE ID_room=?;");
			ps.setInt(1, IdRoom);

			rs = ps.executeQuery();

			while (rs.next()) {
				sensors.add(new Sensor(rs.getInt("ID_sensor"), rs.getBoolean("status"), rs.getInt("type"), rs.getShort("threshold"), 0,rs.getInt("ID_room"), room));
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
