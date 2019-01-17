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
	static City city= null;
	
	public static City getTree() throws DatabaseException, InterruptedException {
		Connection con = null;
		
		try {
			con = DBConnection.connect();
		} catch (DatabaseException ex) {
			throw new DatabaseException("Errore di connessione", ex);
		}

		PreparedStatement ps = null;

		
		
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
		
		Room r=null;
		
		try {
			if(rooms.containsKey(rs.getInt("r.ID")))
				return rooms.get(rs.getInt("r.ID"));
			r = new Room(rs.getInt("r.ID"), rs.getInt("r.number"), rs.getInt("r.ID_floor"), getFloor());
			rooms.put(rs.getInt("r.ID"), r);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return r;
	}
	
	
	public static Floor getFloor(){
		Floor f=null;
		try {
			if(floors.containsKey(rs.getInt("f.ID")))
				return floors.get(rs.getInt("f.ID"));
			f = new Floor(rs.getInt("f.ID"), rs.getInt("f.number"), rs.getInt("f.ID_building"), getBuilding());
			floors.put(rs.getInt("f.ID"), f);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}
	
	public static Building getBuilding()
	{
		Building b=null;
		try {
			if(buildings.containsKey(rs.getInt("b.ID")))
				return buildings.get(rs.getInt("b.ID"));
			b = new Building(rs.getInt("b.ID"), rs.getString("b.street"), rs.getInt("b.number"), rs.getInt("b.ID_Area"), getArea());
			buildings.put(rs.getInt("b.ID"), b);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public static Area getArea()
	{
		Area a=null;
		try {
			if(areas.containsKey(rs.getInt("a.ID")))
				return areas.get(rs.getInt("a.ID"));
			
			a = new Area(rs.getInt("a.ID"), rs.getInt("a.ID_city"), rs.getString("a.name"), getCity());
			areas.put(rs.getInt("a.ID"), a);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}	
	
	public static City getCity()
	{
		
		if(city!=null)
		return city;
		
		City c=null;
		try {
			c = new City(1);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}		
	
	


}


