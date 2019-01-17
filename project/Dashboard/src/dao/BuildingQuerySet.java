package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Building;
import model.Floor;

public class BuildingQuerySet {

	/**
	 * List of all Buiilding's floors
	 * @param IDArea
	 * @return
	 * @throws DatabaseException
	 * @throws InterruptedException 
	 */
	public static LinkedList<Floor> getFloors(int IDBuilding, Building building) throws DatabaseException, InterruptedException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<Floor> floors = new LinkedList<Floor>();
		
		try {
			ps = con.prepareStatement("SELECT * FROM floor WHERE ID_building=?;");
			ps.setInt(1, IDBuilding);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				floors.add(new Floor(rs.getInt("ID"), rs.getInt("number"), rs.getInt("ID_building"), building));
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
		return floors;
	}
	
	

}
