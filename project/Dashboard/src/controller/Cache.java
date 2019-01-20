package controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import dao.DatabaseException;
import dao.TreeQuerySet;
import model.Area;
import model.Building;
import model.City;
import model.Floor;
import model.LockedSensor;
import model.Room;
import model.Sensor;

public class Cache {
	private static boolean alreadyStarted = false;

	private static City root;

	private static Map<Integer, Integer> sensorIdMap = new HashMap<Integer, Integer>();
	private static LockedSensor[] sensorMap = new LockedSensor[10000000];
	private static Map<Integer, Building> buildingMap = new HashMap<Integer, Building>();
	private static int lastInsertedSensorPosition = 0;

	private static Map<Integer, Room> roomMap;

	private static ReentrantReadWriteLock sensorMapLock = new ReentrantReadWriteLock();
	private static ReentrantReadWriteLock treeLock = new ReentrantReadWriteLock();

	public static void init() {
		if (alreadyStarted)
			return;

		try {
			TreeQuerySet.getTree();
			System.out.println(sensorsNumber());
			System.out.println(roomMap.size());
			alreadyStarted = true;
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// for (LockedSensor ls : sensorMap) {
		// if (ls != null)
		// System.out.println(ls.getSensor().getId());
		// }

	}

	public static void controlSensorValueDelays() {
		for (LockedSensor ls : sensorMap) {
			if (ls != null) {
				Instant lastTime;
				ls.readLock();
				lastTime = ls.getSensor().getLastValue();
				if (lastTime == null)
					continue;
				ls.readUnlock();
				if (Duration.between(lastTime, Instant.now()).getSeconds() > 120) {
					Cache.setMissing(ls);
				}
			}
		}
	}

	// -----------------------------------------------------User
	// methods---------------------------------------------------------------------//
	// return the areas to the user
	public static List<Area> getAreas() {
		List<Area> list;
		treeLock.readLock().lock();
		City city = Cache.getRoot();
		Map<Integer, Area> map = city.getSubs();
		list = new ArrayList<Area>();
		for (Area area : map.values())
			list.add(area);
		treeLock.readLock().unlock();
		return list;
	}

	public static Map<Integer,Area> getAreasMap() {
		treeLock.readLock().lock();
		City city = Cache.getRoot();
		Map<Integer, Area> map = city.getSubs();
		treeLock.readLock().unlock();
		return map;
	}
	
	
	// return the buildings to the user
	public static List<Building> getBuildings(int id_area) {
		List<Building> list;
		treeLock.readLock().lock();
		City city = Cache.getRoot();
		Map<Integer, Area> areas = city.getSubs();
		Map<Integer, Building> buildings = areas.get(id_area).getSubs();
		list = new ArrayList<Building>();
		for (Building building : buildings.values())
			list.add(building);
		treeLock.readLock().unlock();
		return list;
	}

	// return the floors buildings to the user
	public static List<Floor> getFloors(int id_area, int id_building) {
		List<Floor> list;
		treeLock.readLock().lock();
		City city = Cache.getRoot();
		Map<Integer, Area> areas = city.getSubs();
		Map<Integer, Building> buildings = areas.get(id_area).getSubs();
		Map<Integer, Floor> floors = buildings.get(id_building).getSubs();
		list = new ArrayList<Floor>();
		for (Floor floor : floors.values())
			list.add(floor);
		treeLock.readLock().unlock();
		return list;

	}

	
	//////////////////////////////////////////////////////////////////-------------------------------------------------------
	
	public static List<Floor> getFloors(int id_building) {
		List<Floor> list;
		treeLock.readLock().lock();
		Map<Integer, Floor> floors = buildingMap.get(id_building).getSubs();
		list = new ArrayList<Floor>(floors.values());
		treeLock.readLock().unlock();
		return list;

	}
	
	
	// return the rooms buildings to the user
	public static List<Room> getRooms( int id_building, int id_floor) {
		List<Room> list;
		treeLock.readLock().lock();
		Map<Integer, Floor> floors = buildingMap.get(id_building).getSubs();
		Map<Integer, Room> rooms = floors.get(id_floor).getSubs();
		list = new ArrayList<Room>(rooms.values());
		treeLock.readLock().unlock();
		return list;
	}
	//////////////////////////////////////////////////////////////////////--------------------------------------------
	
	// return the rooms buildings to the user
	public static List<Room> getRooms(int id_area, int id_building, int id_floor) {
		List<Room> list;
		treeLock.readLock().lock();
		City city = Cache.getRoot();
		Map<Integer, Area> areas = city.getSubs();
		Map<Integer, Building> buildings = areas.get(id_area).getSubs();
		Map<Integer, Floor> floors = buildings.get(id_building).getSubs();
		Map<Integer, Room> rooms = floors.get(id_floor).getSubs();
		list = new ArrayList<Room>(rooms.values());
		treeLock.readLock().unlock();
		return list;

	}

	
	
	
	// --------------------------------------------------------------------------------------------------------//

	/*
	 * Sets the room map
	 */
	public static void setRoomMap(Map<Integer, Room> rmap) {
		treeLock.writeLock().lock();
		roomMap = rmap;
		treeLock.writeLock().unlock();
	}

	// //set room map
	// public static void setSensorMap(Map<Integer,LockedSensor> smap){
	// sensorMapLock.writeLock().lock();
	// //sensorMap=smap;
	// sensorMapLock.writeLock().lock();
	// }

	/*
	 * Returns all the sensors
	 */
	public static Map<Integer, LockedSensor> getSensors(int id_area, int id_building, int id_floor, int id_room) {
		treeLock.readLock().lock();
		City city = Cache.getRoot();
		Map<Integer, Area> areas = city.getSubs();
		Map<Integer, Building> buildings = areas.get(id_area).getSubs();
		Map<Integer, Floor> floors = buildings.get(id_building).getSubs();
		Map<Integer, Room> rooms = floors.get(id_floor).getSubs();
		Map<Integer, LockedSensor> sensors = rooms.get(id_room).getSubs();
		treeLock.readLock().unlock();
		return sensors;
	}

	public static void setRoot(City c) {
		treeLock.writeLock().lock();
		root = c;
		treeLock.writeLock().unlock();
	}

	public static City getRoot() {
		treeLock.readLock().lock();
		City r = root;
		treeLock.readLock().unlock();
		return r;
	}

	/*
	 * public static void init() { System.out.println("Downloading sensors"); try {
	 * List<Sensor> list=new ArrayList<Sensor>(); // 15 requests of 10000 sensors
	 * for(int i=0; i<15; i++) { list.addAll(SensorQuerySet.getSensors(i,null));
	 * System.out.println("Package "+(i+1)+"/15"); } // insert sensors in the map
	 * for(Sensor curr: list) { insertSensor(curr);
	 * System.out.println(curr.getId()); } } catch (DatabaseException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (InterruptedException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); } }
	 */

	// /////FIXME ??????? ------------ W T F ------------ ??????? FIXME
	// public static void setSensor(Sensor s,Instant valueInstant) throws
	// InterruptedException {
	// LockedSensor ls = getLockedSensor(s.getId());
	// ls.writeLock();
	// ls.getSensor().setValue(s.getValue(), valueInstant);
	// ls.writeUnlock();
	// }

	/*
	 * Sets sensor's value at the given instant and gives back the threshold
	 */
	public static int setSensorValue(int id, int value, Instant valueInstant) {
		LockedSensor ls = getLockedSensor(id);

		ls.writeLock();

		ls.getSensor().setValue(value, valueInstant);
		int threshold = ls.getSensor().getTreshold();

		ls.writeUnlock();

		return threshold;
	}

	public static void insertSensor(int id, boolean status, int type, int treshold, int value, int roomID)
			throws InterruptedException {
		Sensor s = new Sensor(id, status, type, treshold, value, roomID, roomMap.get(roomID));
		insertSensor(s);
	}

	public static void insertSensor(Sensor s) throws InterruptedException {

		int i = lastInsertedSensorPosition;

		sensorMapLock.writeLock().lock();
		++lastInsertedSensorPosition;
		sensorIdMap.put(s.getId(), i);
		sensorMapLock.writeLock().unlock();

		sensorMap[i] = new LockedSensor(s);
	}

	public static Sensor getSensor(int id) throws InterruptedException {
		// if (treeLock.getReadLockCount() > 20000) //if too many requests, wait. Ugly
		// hack to handle request peaks
		// treeLock.wait(10);
		// treeLock.readLock().lock(); // inefficient but avoids deadlocks
		// sensorMapLock.readLock().lock();
		Sensor s = null;
		LockedSensor ls = getLockedSensor(id);

		ls.readLock();

		// not-fake shallow copy of sensor //TODO make more elegant
		s = new Sensor(ls.getSensor().getId(), ls.getSensor().getStatus(), ls.getSensor().getType(),
				ls.getSensor().getTreshold(), ls.getSensor().getValue(), ls.getSensor().getIdRoom(), null);

		ls.readUnlock();
		// sensorMapLock.readLock().unlock();
		// treeLock.readLock().unlock();
		// treeLock.notify();
		return s;
	}

	public static LockedSensor getLockedSensor(int id) {
		//sensorMapLock.readLock().lock();
		LockedSensor ls = sensorMap[sensorIdMap.get(id)];
		//sensorMapLock.readLock().unlock();
		return ls;
	}

	public static void removeSensor(int id) {

		sensorMapLock.readLock().lock();
		sensorMap[sensorIdMap.get(id)] = null; // remove
		sensorMapLock.readLock().unlock();

		sensorMapLock.writeLock().lock();
		sensorIdMap.remove(id); // invalidate entry
		sensorMapLock.writeLock().unlock();

	}

	/*
	 * public static void removeAlert(Sensor s) { Room r = s.getIdRoom(); Floor f =
	 * r.getIdFloor(); Building b = f.getIdBuilding(); Area area = b.getArea(); City
	 * city = area.getArea(); }
	 * 
	 * public static void addWarning(Warning w) { warnings.add(w); }
	 * 
	 * public static List<Warning> getWarnings(){ return warnings;
	 */

	public static void insertRoom(Floor floor, int newRoomID, int newRoomNumber) {
		treeLock.writeLock().lock();
		floor.getSubs().putIfAbsent(newRoomID, new Room(newRoomID, newRoomNumber, floor.getId(), floor));
		roomMap.putIfAbsent(newRoomID, floor.getRooms().get(newRoomID));
		treeLock.writeLock().unlock();
	}

	public static void insertFloor(Building building, int newFloorID, int floorNumber) {
		treeLock.writeLock().lock();
		building.getSubs().putIfAbsent(newFloorID, new Floor(newFloorID, floorNumber, building.getId(), building));
		treeLock.writeLock().unlock();
	}

	public static void insertBuilding(Area area, int newBuildingID, String street, int civicNumber) {
		treeLock.writeLock().lock();
		area.getSubs().putIfAbsent(newBuildingID, new Building(newBuildingID, street, civicNumber, area.getId(), area));
		treeLock.writeLock().unlock();
	}

	public static void insertArea(int newAreaID, String name) {
		treeLock.writeLock().lock();
		root.getSubs().putIfAbsent(newAreaID, new Area(newAreaID, name, root.getId(), root));
		treeLock.writeLock().unlock();
	}

	public static Map<Integer, LockedSensor> getSensorsByRoom(Room room) throws InterruptedException {
		return room.getSubs();
	}

	public static Map<Integer, LockedSensor> getLockedSensorsByRoom(int roomID) throws InterruptedException {
		treeLock.readLock().lock();
		Map<Integer, LockedSensor> res = roomMap.get(roomID).getSubs();
		treeLock.readLock().unlock();
		return res;
	}

	/////// FIXME BRUTTO BRUTTO BRUTTO BRUTTO ///////
	public static Map<Integer, Sensor> getSensorsByRoom(int roomID) throws InterruptedException { // FIXME should do
																									// shallow copies
		treeLock.readLock().lock();
		sensorMapLock.writeLock().lock();
		Map<Integer, Sensor> res = new HashMap<Integer, Sensor>();
		for (LockedSensor ls : roomMap.get(roomID).getSubs().values()) {
			ls.readLock();
			Sensor s = new Sensor(ls.getSensor());
			ls.readUnlock();
			res.put(s.getId(), s);
		}
		sensorMapLock.writeLock().unlock();
		treeLock.readLock().unlock();
		return res;
	}

	/*
	 * Gives the number of sensors in the system
	 */
	public static int sensorsNumber() {
		sensorMapLock.readLock().lock();
		int length = sensorIdMap.size();
		sensorMapLock.readLock().unlock();
		return length;
	}

	/*
	 * Adds an above-threshold problem in the sensor's room
	 * 
	 * @param sensor id
	 */
	public static void addProblem(int id) {
		LockedSensor ls = getLockedSensor(id);
		addProblem(ls);
	}

	/*
	 * Adds an above-threshold problem in the sensor's room
	 * 
	 * @param LockedSensor
	 */
	private static void addProblem(LockedSensor ls) {
		ls.readLock();
		ls.getSensor().getRoom().addProblem(ls);
		ls.readUnlock();
	}

	/*
	 * Adds a missing-sensor problem in the sensor's room
	 */
	private static void setMissing(LockedSensor ls) {
		ls.readLock();
		ls.getSensor().getRoom().setMissing(ls);
		ls.readUnlock();
	}

	public static void setBuildingMap(Map<Integer, Building> buildings) {
		treeLock.writeLock().lock();
		buildingMap = buildings;
//		for (Map.Entry<Integer,Building> e : buildingMap.entrySet()) {
//			System.out.println(e.getValue().getId() +" - " + e.getValue().getProblems());
//		}
		treeLock.writeLock().unlock();
	}
	
	public static Building getBuilding(Integer id) {
		treeLock.readLock().lock();
		Building b= buildingMap.get(id);
		treeLock.readLock().unlock();
		return b;
	}
}
