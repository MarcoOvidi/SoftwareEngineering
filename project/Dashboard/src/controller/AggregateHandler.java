package controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dao.DatabaseException;
import model.*;

public class AggregateHandler {

	
	public static String listAreas() throws DatabaseException{
			String tails="<div class=tail onclick=\"location.href='newAggregate.jsp?aggregateType=area' \"'>New Area</div> ";
			 Cache.getAreas();
			List<Area> areas = Cache.getAreas();
			for(Area curr: areas)
				tails=tails+"<div class=tail onclick=\"location.href='?area="+curr.getId()+"'\"><div class=tailname >"+curr.getName()+"</div></div><br />";
			return tails;
	};
		
	public static String listBuildings(String p_area) throws DatabaseException{	
		String tails="<div class=tail>New Build</div>";
			int area =Integer.parseInt(p_area);

			List<Building> buildings=Cache.getBuildings(area);
			
			for(Building curr: buildings)
					tails=tails+"<div class=tail onclick=\"location.href='?area="+area+"&building="+curr.getId()+"'\"><div class=tailname >"+curr.getStreet()+"  "+curr.getCivicNumber()+"</div></div><br />";
			return tails;
	};	
		
	public static String listFloors(String p_area, String p_building) throws DatabaseException {
		String tails="<div class=tail >New Floor</div>";
			int area =Integer.parseInt(p_area);
			int building =Integer.parseInt(p_building);

			List<Floor> floors = Cache.getFloors(area, building);
			
			for(Floor curr: floors)
				tails=tails+"<div class=tail onclick=\"location.href='?area="+area+"&building="+building+"&floor="+curr.getId()+"'\"><div class=tailname >"+curr.getFloorNumber()+"</div></div><br />";
			return tails;
		};
		
	public static String listRooms(String p_area, String p_building, String p_floor) throws DatabaseException {
		String tails="<div class=tail >New Room</div>";
			int area =Integer.parseInt(p_area);
			int building =Integer.parseInt(p_building);
			int floor =Integer.parseInt(p_floor);
		
			List<Room> rooms = Cache.getRooms(area, building, floor);
			for(Room curr: rooms)
				tails=tails+"<div class=tail onclick=\"location.href='?area="+area+"&building="+building+"&floor="+floor+"&room="+curr.getId()+"'\"><div class=tailname >"+curr.getNumber()+"</div></div><br />";
			return tails;
		}
	
	public static String listSensors(String p_area, String p_building, String p_floor, String p_room) throws DatabaseException, InterruptedException {
		String tails="";

			int area = Integer.parseInt(p_area);
			int building =Integer.parseInt(p_building);
			int floor =Integer.parseInt(p_floor);
			int room = Integer.parseInt(p_room);
		
			Map<Integer,LockedSensor> map = Cache.getSensors(area, building, floor, room); 
			
			
			for (Map.Entry<Integer,LockedSensor> entry : map.entrySet())
			{
				LockedSensor ls = entry.getValue();
				ls.getLock().readLock().lock();
				Sensor curr=ls.getSensor();
				tails=tails+"<div class=tail ><div class=tailname >Id:"+curr.getId()+" Status:"+curr.getStatus()+" Treshold:"+curr.getTreshold()+" Value:"+curr.getValue()+"</div></div><br />";
				ls.getLock().readLock().unlock();
			}
			
			return tails;
		}
	}

