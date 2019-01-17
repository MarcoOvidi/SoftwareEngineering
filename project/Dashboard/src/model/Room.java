package model;


import java.util.Map;

public class Room extends Aggregate<LockedSensor> {
	int number;
	int IdFloor;
	Floor floor;

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
}

