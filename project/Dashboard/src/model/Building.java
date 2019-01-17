package model;

import java.util.Map;

public class Building extends Aggregate<Floor> {
	String street;
	int civicNumber;
	int IDArea;
	Area area;

	public Building(int id) {
		super(id);
	};

	public Building(int idBuilding, String street, int civicNumber, int IDArea, Area area) {
		super(idBuilding);
		this.street = street;
		this.civicNumber = civicNumber;
		this.IDArea = IDArea;
		this.area = area;
	};

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getCivicNumber() {
		return civicNumber;
	}

	public void setCivicNumber(int civicNumber) {
		this.civicNumber = civicNumber;
	}

	public int getIDArea() {
		return IDArea;
	}

	public void setIDArea(int iDArea) {
		IDArea = iDArea;
	}
	
	public Map<Integer, Floor> getFloors() {
		return this.getSubs();
	};
	
	public Area getArea() {
		return area;
	}

	

}

