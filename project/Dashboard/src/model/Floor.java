package model;

import java.util.Map;

public class Floor extends Aggregate<Room> {
	int floorNumber;
	int IdBuilding;
	Building building;

	public Floor(int id) {
		super(id);

	};

	public Floor(int idFloor, int floorNumber, int IdBuilding, Building building){
		super(idFloor);
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

	public Map<Integer,Room> getRooms() {
		return this.getSubs();
	};
	
	public Building getBuilding() {
		return building;
	}
}

