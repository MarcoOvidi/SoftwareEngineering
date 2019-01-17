package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import dao.DatabaseException;
import dao.SensorQuerySet;
import model.Alert;
import model.Area;
import model.Building;
import model.City;
import model.Floor;
import model.LockedSensor;
import model.Room;
import model.Sensor;

public class Cache{
	private static City root;
	private static Map<Integer,LockedSensor> map= new HashMap<Integer, LockedSensor>(); 
	private static Map<Integer,Alert> alerts = new HashMap<Integer,Alert>();
	private static Map<Integer,Room> roomMap = new HashMap<Integer,Room>(); 
	
	private static ReentrantReadWriteLock globalLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock treeLock = new ReentrantReadWriteLock();
	public static ReentrantReadWriteLock alertsLock = new ReentrantReadWriteLock();
	
	//return the areas
	public static List<Area> getAreas() {
		List<Area> list;
		treeLock.readLock().lock();
			City city= Cache.getRoot();
			Map<Integer, Area> map = city.getSubs();
			 list= new ArrayList<Area>(map.values());
		treeLock.readLock().unlock();
		return list;
	}
	
	//return the buildings
	public static List<Building> getBuildings(int id_area) {
		List<Building> list;
		treeLock.readLock().lock();
			City city= Cache.getRoot();
			Map<Integer,Area> areas = city.getSubs();
			Map<Integer, Building> buildings = areas.get(id_area).getSubs();
			list = new ArrayList<Building>(buildings.values());
		treeLock.readLock().unlock();
		return list;
	}
	
	//return the floors
	public static List<Floor> getFloors(int id_area, int id_building) {
		List<Floor> list;
		treeLock.readLock().lock();
			City city= Cache.getRoot();
			Map<Integer,Area> areas = city.getSubs();
			Map<Integer, Building> buildings = areas.get(id_area).getSubs();
			Map<Integer, Floor> floors = buildings.get(id_building).getSubs();
			list = new ArrayList<Floor>(floors.values());
		treeLock.readLock().unlock();
		return list;
		
	}
	
	//return the rooms 
	public static List<Room> getRooms(int id_area, int id_building, int id_floor) {
		List<Room> list;
		treeLock.readLock().lock();
			City city= Cache.getRoot();
			Map<Integer,Area> areas = city.getSubs();
			Map<Integer, Building> buildings = areas.get(id_area).getSubs();
			Map<Integer, Floor> floors = buildings.get(id_building).getSubs();
			Map<Integer, Room> rooms = floors.get(id_floor).getSubs();
		    list = new ArrayList<Room>(rooms.values());
		treeLock.readLock().unlock();
		return list;
		
	}
	
	//return the sensors
	public static Map<Integer,LockedSensor> getSensors(int id_area, int id_building, int id_floor, int id_room){
		treeLock.readLock().lock();
			City city= Cache.getRoot();
			Map<Integer, Area> areas = city.getSubs();
			Map<Integer, Building> buildings = areas.get(id_area).getSubs();
			Map<Integer, Floor> floors = buildings.get(id_building).getSubs();
			Map<Integer, Room> rooms = floors.get(id_floor).getSubs();
			Map<Integer, LockedSensor> sensors = rooms.get(id_room).getSubs();
		treeLock.readLock().unlock();
		return sensors;
	}
		
	public static void setRoot(City r) {
		treeLock.writeLock().lock();
		root=r;
		treeLock.writeLock().unlock();
	}
	
	public static City getRoot() {
		treeLock.readLock().lock();
		City r=root;
		treeLock.readLock().unlock();
		return r;
	}
	
	public static void init() {
		System.out.println("Downloading sensors");
		try {
			List<Sensor> list=new ArrayList<Sensor>();
			// 15 requests of 10000 sensors
			for(int i=0; i<15; i++) {
				list.addAll(SensorQuerySet.getSensors(i,null));
				System.out.println("Package "+(i+1)+"/15");
			}
			// insert sensors in the map
			for(Sensor curr: list) {
				insertSensor(curr);
				System.out.println(curr.getId());	
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setSensor(Sensor s) throws InterruptedException {
		globalLock.readLock().lock();
		LockedSensor ls = map.get(s.getId());
		ls.getLock().writeLock().lock();
		ls.getSensor().setValue(s.getValue());
		ls.getLock().writeLock().unlock();
		globalLock.readLock().unlock();
	}
	
	public static void insertSensor(Sensor s) throws InterruptedException {
		globalLock.writeLock().lock();
		map.put(s.getId(), new LockedSensor(s));
		globalLock.writeLock().unlock();

	}

	public static Sensor getSensor(int id) throws InterruptedException {
		globalLock.readLock().lock();
		Sensor s=null;
		LockedSensor ls = map.get(id);
		ls.getLock().readLock().lock();
		treeLock.readLock().lock();
		s=new Sensor(ls.getSensor().getId(), ls.getSensor().getStatus(), ls.getSensor().getType(), ls.getSensor().getTreshold(), ls.getSensor().getValue(), ls.getSensor().getIdRoom(), ls.getSensor().getRoom());
		treeLock.readLock().unlock();
		ls.getLock().readLock().unlock();
		globalLock.readLock().unlock();
		return s;
	}
	
	public static void removeSensor(int id) {
		globalLock.writeLock().lock();
		map.remove(id);
		globalLock.writeLock().unlock();
	}
	
	public static void addAlert(Sensor s) {
		treeLock.readLock().lock();
		Room r = s.getRoom();
		Floor f = r.getFloor();
		Building b = f.getBuilding();
		Area a = b.getArea();
		City c = a.getCity();
		
		alertsLock.writeLock().lock();
		Alert alert=new Alert(s.getId(),r.getId(),f.getId(),b.getId(),a.getId(),c.getId());
		alerts.put(alert.getId(),alert);
		alertsLock.writeLock().unlock();
		
		treeLock.readLock().unlock();
	}
	
	/*
	public static void removeAlert(Sensor s) {
		Room r = s.getIdRoom();
		Floor f = r.getIdFloor();
		Building b = f.getIdBuilding();
		Area area = b.getArea();
		City city = area.getArea();
	}
	
	public static void addWarning(Warning w) {
		warnings.add(w);
	}

	public static List<Warning> getWarnings(){
		return warnings;
	*/

	public static void insertRoom(Floor floor, int newRoomID,int newRoomNumber) {
		treeLock.writeLock().lock();
		floor.getSubs().putIfAbsent(newRoomID, new Room(newRoomID,newRoomNumber,floor.getId(),floor));
		roomMap.putIfAbsent(newRoomID, floor.getRooms().get(newRoomID));
		treeLock.writeLock().unlock();
	}
	public static void insertFloor(Building building, int newFloorID, int floorNumber) {
		treeLock.writeLock().lock();
		building.getSubs().putIfAbsent(newFloorID, new Floor(newFloorID,floorNumber,building.getId(),building));
		treeLock.writeLock().unlock();
	}
	public static void insertBuilding(Area area, int newBuildingID,String street,int civicNumber) {
		treeLock.writeLock().lock();
		area.getSubs().putIfAbsent(newBuildingID, new Building(newBuildingID,street,civicNumber,area.getId(),area));
		treeLock.writeLock().unlock();
	}
	public static void insertArea(int newAreaID,String name) {
		treeLock.writeLock().lock();
		root.getSubs().putIfAbsent(newAreaID, new Area(newAreaID,name,root.getId(),root));
		treeLock.writeLock().unlock();
	}
	
	public static Map<Integer, LockedSensor> getSensorsByRoom(Room room) throws InterruptedException{
		return room.getSubs(); 
	}
	
	public static Map<Integer, LockedSensor> getSensorsByRoom(int roomID) throws InterruptedException{
		return getRoomByID(roomID).getSubs(); 
	}

	public static Room getRoomByID(int roomID) {
		// TODO Auto-generated method stub
		treeLock.readLock().lock();
		Room room = roomMap.get(roomID);
		treeLock.readLock().unlock();
		return room;
	}
}
