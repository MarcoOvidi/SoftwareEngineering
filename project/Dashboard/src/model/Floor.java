package model;

import java.util.ArrayList;
import java.util.List;

import dao.BuildingQuerySet;
import dao.DatabaseException;
import dao.FloorQuerySet;

public class Floor extends Aggregate<Room> {
	int floorNumber;
	int IdBuilding;
	Building building;

	public Floor(int id) throws DatabaseException {
		super(id);

	};

	public Floor(int idFloor, int floorNumber, int IdBuilding, Building building) throws DatabaseException, InterruptedException {
		super(idFloor);
		getRooms();
		this.floorNumber = floorNumber;
		this.IdBuilding = IdBuilding;
		this.building = building;
	};

	public int getIdBuilding() {
		return IdBuilding;
	}

	public void setIdBuilding(int idBuilding) {
		IdBuilding = idBuilding;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public void getRooms() throws DatabaseException, InterruptedException{
		List<Room> list =  FloorQuerySet.getRooms(getId(), this);
		for(Room r : list)
			subs.put(r.getId(), r);
	};
	
	public Building getBuilding() {
		return building;
	}
}

