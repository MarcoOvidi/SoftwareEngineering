package model;


import java.util.Map;

public class Room extends Aggregate<LockedSensor> {
	protected int number;
	protected int IdFloor;
	protected Floor floor;
	
	public Room(int id) {
		super(id);
		getSensors();
	}

	public Room(int idRoom, int roomNumber, int IdFloor, Floor floor) {
		super(idRoom);
		this.number = roomNumber;
		this.IdFloor = IdFloor;
		this.floor = floor;
	}
	
	public Room(Room room) {
		super(room.id);
		this.number = room.number;
		this.IdFloor = room.IdFloor;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getIdFloor() {
		return IdFloor;
	}

	public void setIdFloor(int idFloor) {
		IdFloor = idFloor;
	}

	public Map<Integer,LockedSensor> getSensors() {
		return this.getSubs();
	};
	
	public Floor getFloor() {
		return floor;
	}
	
	public void addProblem(LockedSensor ls) {
		Sensor s = ls.getSensor(); //FIXME missing lock
		if(problems.containsKey(s.getIdRoom())) {
			problems.get(s.getIdRoom()).addSensor(ls);
		}
		else {
			problems.put(s.getIdRoom(), new Problem(ls));
		}
	}

	public void setMissing(LockedSensor ls) {
		Sensor s = ls.getSensor(); //FIXME missing lock
		if(problems.containsKey(s.getIdRoom())) {
			problems.get(s.getIdRoom()).addMissingSensor(ls);
		}
		else {
			problems.put(s.getIdRoom(), new Problem(ls, true));
		}
	}
	
}

