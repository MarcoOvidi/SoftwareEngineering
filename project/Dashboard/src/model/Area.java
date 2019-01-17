package model;

import java.util.ArrayList;
import java.util.List;

import dao.AreaQuerySet;
import dao.DatabaseException;
	

public class Area extends Aggregate<Building> {
	private int IdCity;
	private String name;
	private City city;
	
	public Area(int id){
		super(id);
	}
	
	public Area(int idArea, int IdCity, String areaName, City city) throws DatabaseException, InterruptedException {
		super(idArea);
		getBuildings();
		this.IdCity=IdCity;
		this.name=areaName;
		this.city=city;
	}
	
	public int getIdCity() {
		return IdCity;
	}

	public void setIdCity(int idCity) {
		IdCity = idCity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void getBuildings() throws DatabaseException, InterruptedException{
		List<Building> list =  AreaQuerySet.getBuildings(getId(), this);
		for(Building b : list)
			subs.put(b.getId(), b);
	};
	
	public City getCity(){
		return city;
	}
	
}
