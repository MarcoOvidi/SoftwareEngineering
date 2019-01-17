package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Floor;
import model.Room;

public class FloorQuerySet {

	/**
	 * List of all Buiilding's floors
	 * @param IDArea
	 * @return
	 * @throws DatabaseException
	 * @throws InterruptedException 
	 */
	public static LinkedList<Room> getRooms(int IDFloor, Floor floor) throws DatabaseException, InterruptedException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<Room> rooms = new LinkedList<Room>();
		
		try {
			ps = con.prepareStatement("SELECT * FROM room WHERE ID_floor=?;");
			ps.setInt(1, IDFloor);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				rooms.add(new Room(rs.getInt("ID"), rs.getInt("number"), rs.getInt("ID_floor"), floor));
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
		return rooms;
	}
	
}
