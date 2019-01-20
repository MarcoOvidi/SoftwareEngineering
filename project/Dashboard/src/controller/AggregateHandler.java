package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.DatabaseException;
import model.Aggregate;
import model.Area;
import model.Building;
import model.Floor;
import model.LockedSensor;
import model.Notification;
import model.Problem;
import model.Room;
import model.Sensor;
import model.UserBean;

public class AggregateHandler {
	
	//BUILDINGS MANAGER
	
	
	public static String buildingsManagerNotifications(UserBean currentUser){
		Set<Integer> buildings = currentUser.getBuildingsId();
		String notifications = "";
		//System.out.println(buildings);
		for(Integer id: buildings) {
			Building curr=Cache.getBuilding(id);
			if(curr == null) // user has a building assigned that has no sensors in it
				continue;
			HashMap<Integer,Problem> problems = curr.getProblems();
			List<Notification> notificationArray= new ArrayList<Notification>();
			
			for(Map.Entry<Integer, Problem>  entry : problems.entrySet()) 
				notificationArray.addAll(entry.getValue().getNotifications());
			
			for(Notification current : notificationArray) {
				System.out.println("$$"+current);
				notifications=notifications+"<div class=notification > <b>"+current.getType()+"</b> <br />"+current.getMessage()+"</div>";
			}
			
		}
		return notifications;
	}
	
	public static String buildingsManager(UserBean currentUser) {
		String tails="<div class=tail>My Buildings</div>";
		Set<Integer> buildings = currentUser.getBuildingsId();
		for(Integer id: buildings) {
			Building curr=Cache.getBuilding(id);
//			System.out.println(">"+id);
//			System.out.println(">"+curr);
			if(curr!=null)
			tails=tails+"<div class="+getProblem(curr)+" onclick=\"location.href='?building="+curr.getId()+"'\"><div class=tailname >"+curr.getStreet()+"  "+curr.getCivicNumber()+"</div>"+getProblemDescription(curr)+"</div><br />";
		}
			return tails;
	}
		
	public static String listFloors(String p_building, UserBean currentUser) throws DatabaseException {
		
		String tails="<div class=tail> Error 403 </div>";
		
		if(currentUser.checkBuilding(p_building)){
				tails="<div class=tail >Floors</div>";
//				System.out.println(">>> " + p_building);
				int building =Integer.parseInt(p_building);
				
				
				List<Floor> floors = Cache.getFloors(building);
				
				for(Floor curr: floors)
					tails=tails+"<div class="+getProblem(curr)+" onclick=\"location.href='?building="+building+"&floor="+curr.getId()+"'\"><div class=tailname >"+curr.getFloorNumber()+"</div>"+getProblemDescription(curr)+"</div><br />";
				return tails;
			}
		
		return tails;
		}
		
	public static String listRooms( String p_building, String p_floor, UserBean currentUser) throws DatabaseException {
		String tails="<div class=tail> Error 403 </div>";
		if(currentUser.checkBuilding(p_building)){
				tails="<div class=tail >Rooms</div>";
				int building =Integer.parseInt(p_building);
				int floor =Integer.parseInt(p_floor);
			
				List<Room> rooms = Cache.getRooms(building, floor);
				for(Room curr: rooms)
					tails=tails+"<div class="+getProblem(curr)+" onclick=\"location.href='?building="+building+"&floor="+floor+"&room="+curr.getId()+"'\"><div class=tailname >"+curr.getNumber()+"</div>"+getProblemDescription(curr)+"</div><br />";
				return tails;
			}
		return tails;
		}
	
	public static String listSensors(String p_building, String p_room, UserBean currentUser) throws DatabaseException, InterruptedException {
		String tails="<div class=tail> Error 403 </div>";
		if(currentUser.checkBuilding(p_building)){
				tails="<div class=tail >Sensors</div>";
	
//				System.out.println(">>>> " + p_room + "   AAAAAH");
				int room = Integer.parseInt(p_room);
			
				Map<Integer,LockedSensor> map = Cache.getLockedSensorsByRoom(room);
				
				for (Map.Entry<Integer,LockedSensor> entry : map.entrySet())
				{
					LockedSensor ls = entry.getValue();
					ls.readLock();
					Sensor curr=ls.getSensor();
					tails=tails+"<div class="+getSensorColor(curr)+" ><div class=tailname >Id:"+curr.getId()+" Status:"+curr.getStatus()+" Threshold:"+curr.getTreshold()+" Value:"+curr.getValue()+"</div></div><br />";
					ls.readUnlock();
				}
				return tails;
			}
		return tails;
		}
	
	
	//AREAS MANAGER
	public static String areasManager(UserBean currentUser) {
		String tails="<div class=tail>My Areas</div>";
		Set<Integer> user_areas = currentUser.getAreasId();
		Map<Integer,Area> list = Cache.getAreasMap();
		
		for(Integer id: user_areas) {
			Area curr=list.get(id);
			if(curr!=null)
				tails=tails+"<div class="+getProblem(curr)+" onclick=\"location.href='?area="+curr.getId()+"'\"><div class=tailname >"+curr.getName()+"</div>"+getProblemDescription(curr)+"</div><br />";		}
			return tails;
	}
	
	public static String listBuildings(String p_area, UserBean currentUser) throws DatabaseException{	
		String tails="<div class=tail>Buildings</div>";
			int area =Integer.parseInt(p_area);
	
			List<Building> buildings=Cache.getBuildings(area);
			for(Building curr: buildings)
				tails=tails+"<div class="+getProblem(curr)+" onclick=\"location.href='?area="+area+"&building="+curr.getId()+"'\"><div class=tailname >"+curr.getStreet()+"  "+curr.getCivicNumber()+"</div>"+getProblemDescription(curr)+"</div><br />";
			return tails;
	};	
	
	
	
	
	//CITY MANAGER
	
	public static String cityManager(UserBean currentUser) throws DatabaseException{
		String tails="<div class=tail onclick=\"location.href='newAggregate.jsp?aggregateType=area' \"'>Areas of the city</div> ";
		List<Area> areas = Cache.getAreas();
		for(Area curr: areas)
			tails=tails+"<div class="+getProblem(curr)+" '\"><div class=tailname >"+curr.getName()+"</div>"+getProblemDescription(curr)+"</div><br />";
		return tails;
	};
	
	public static String getSensorColor(Sensor ls) {
		
		String color = ls.getColor();
		
		if (color == "") {
			return "tail ";
		}
		else if (color == "warning") {
			color = "warningTail ";
		}
		else if (color == "alert") {
			color = "sensorAlertTail ";
		}
		else if (color == "notWorking") {
			color = "notWorkingTail ";
		}
		
		return color;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getProblem(Aggregate aggregate) {

		
		HashMap<Integer,Problem> map = aggregate.getProblems();
		
		int a=0, w=0, d=0, n=0;
		for (Map.Entry<Integer,Problem> entry : map.entrySet())
		{
			Problem p = entry.getValue();
			if(p.isAlert()) a++;
			if(p.isWarning()) w++;
			if(p.isDanger()) d++;
			if(p.isNotWorking()) n++;
		}
		
		if(d>0)	
			return "dangerTail ";
		
		if(w>0)
			return "warningTail ";
		
		if(a>0)
			return "alertTail ";
		
		if(n>0)
			return "notWorkingTail ";
		
		return "tail";
	}
	
	@SuppressWarnings("rawtypes")
	public static String getProblemDescription(Aggregate aggregate) {
		String descr =aggregate.problemsDescription();
		String div="";
		if(descr!="") {
			System.out.println(div);
			div="<div>"+descr+"</div>";
		}
		
		return div;
	}

	}

