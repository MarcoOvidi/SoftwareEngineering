package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Area;
import model.Building;

public class AreaQuerySet {
	
	/**
	 * List of all buildings of an area
	 * @param IDArea
	 * @return
	 * @throws DatabaseException
	 * @throws InterruptedException 
	 */
	public static LinkedList<Building> getBuildings(int IDArea, Area area) throws DatabaseException, InterruptedException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<Building> buildings = new LinkedList<Building>();
		
		try {
			ps = con.prepareStatement("SELECT * FROM building WHERE ID_area =?;");
			ps.setInt(1, IDArea);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				buildings.add(new Building(rs.getInt("ID"), rs.getString("street"), rs.getInt("number"), rs.getInt("ID_Area"), area));
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
		return buildings;
	}

}
