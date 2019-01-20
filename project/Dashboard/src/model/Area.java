package model;

import java.util.Map;
	

public class Area extends Aggregate<Building> {
	protected int IdCity;
	protected String name;
	protected City city;
	
	public Area(int id){
		super(id);
	}
	
	public Area(int idArea, String areaName, int IdCity, City city) {
		super(idArea);
		this.IdCity=IdCity;
		this.name=areaName;
		this.city=city;
	}
	
	// shallow copy
	public Area (Area area) {
		super(area.id);
		this.IdCity = area.IdCity;
		this.name= new String (area.name);
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

	public Map<Integer,Building> getBuildings() {
		return this.getSubs();
	};
	
	public City getCity(){
		return city;
	}
	
}
