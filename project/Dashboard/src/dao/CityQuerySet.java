package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import model.Area;
import model.City;

public class CityQuerySet {

	public static LinkedList<Area> getAreas(int IDCity, City city) throws DatabaseException, InterruptedException {
		Connection con = null;

		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<Area> areas = new LinkedList<Area>();

		try {
			ps = con.prepareStatement("SELECT * FROM area WHERE ID_city =?;");
			ps.setInt(1, IDCity);
			rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs);
				areas.add(new Area(rs.getInt("ID"), rs.getInt("ID_city"), rs.getString("name"), city));
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
		return areas;
	}
	
	
}