package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import controller.Cache;


import model.*;



public class TreeQuerySet {
	private static ResultSet rs = null;
	private static Map<Integer, Room> rooms= new HashMap<Integer, Room>();
	private static Map<Integer, Floor> floors = new HashMap<Integer, Floor>();
	private static Map<Integer, Building> buildings= new HashMap<Integer, Building>();
	private static Map<Integer, Area> areas= new HashMap<Integer, Area>();
	
	public static City getTree() throws DatabaseException, InterruptedException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		City city= null;
		
		try {
			ps = con.prepareStatement("select * from sensor as s join room as r join floor as f join building as b join area as a join city as c on s.ID_room=r.ID and r.ID_floor=f.ID and f.ID_building=b.ID and b.ID_area=a.ID and a.ID_city=1;");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getInt("s.ID_sensor")+" "+rs.getBoolean("s.status")+" "+rs.getInt("s.type")+" "+rs.getShort("s.threshold")+rs.getInt("s.ID_room"));
				getSensors();
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
		return city;
	}
	
	public static void getSensors() {
		try {
			Sensor s = new Sensor(rs.getInt("s.ID_sensor"), rs.getBoolean("s.status"),rs.getInt("s.type"), rs.getShort("s.threshold"), 0, rs.getInt("s.ID_room"), getRoom());
			Cache.insertSensor(s);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Room getRoom() {
		try {
			//Sensor s = new Room(rs.getInt("s.ID_sensor"), rs.getBoolean("s.status"),rs.getInt("s.type"), rs.getShort("s.threshold"), 0, rs.getInt("s.ID_room"), getRoom());
			//Cache.insertSensor(s);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


