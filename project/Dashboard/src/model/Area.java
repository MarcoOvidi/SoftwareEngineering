package model;

import java.util.Map;
	

public class Area extends Aggregate<Building> {
	private int IdCity;
	private String name;
	private City city;
	
	public Area(int id){
		super(id);
	}
	
	public Area(int idArea, String areaName, int IdCity, City city) {
		super(idArea);
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

	public Map<Integer,Building> getBuildings() {
		return this.getSubs();
	};
	
	public City getCity(){
		return city;
	}
	
}
