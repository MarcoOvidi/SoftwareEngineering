package model;


import java.util.ArrayList;
import java.util.List;

import controller.Cache;
import controller.Cache2;
import dao.DatabaseException;
import dao.RoomQuerySet;

public class Room extends Aggregate<LockedSensor> {
	int number;
	int IdFloor;
	Floor floor;

	public Room(int id) throws DatabaseException, InterruptedException {
		super(id);
		getSensors();
	}

	public Room(int idRoom, int roomNumber, int IdFloor, Floor floor) throws DatabaseException, InterruptedException {
		super(idRoom);
		this.number = roomNumber;
		this.IdFloor = IdFloor;
		this.floor = floor;
		getSensors();
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

	public void getSensors() throws DatabaseException, InterruptedException {
		System.out.println("connecting sensors");
		subs = Cache.getSensorsByRoom(this);
	};
	
	public Floor getFloor() {
		return floor;
	}
}

