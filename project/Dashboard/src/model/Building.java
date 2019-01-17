package model;

import java.util.ArrayList;
import java.util.List;

import dao.AreaQuerySet;
import dao.BuildingQuerySet;
import dao.DatabaseException;

public class Building extends Aggregate<Floor> {
	String street;
	int civicNumber;
	int IDArea;
	Area area;

	public Building(int id) throws DatabaseException {
		super(id);
	};

	public Building(int idBuilding, String street, int civicNumber, int IDArea, Area area) throws DatabaseException, InterruptedException {
		super(idBuilding);
		getFloors();
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
	
	public void getFloors() throws DatabaseException, InterruptedException{
		List<Floor> list =  BuildingQuerySet.getFloors(getId(), this);
		for(Floor b : list)
			subs.put(b.getId(), b);
	};
	
	public Area getArea() {
		return area;
	}

	

}

