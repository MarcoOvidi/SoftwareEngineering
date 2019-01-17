package model;

import java.util.Map;

public class City extends Aggregate<Area> {

	int IdCity;
	String name;
	
	public City(int id) {
		super(id);
	}
	
	public City(int idCity, int IdCity, String name) {
		super(idCity);
		this.IdCity=IdCity;
		this.name=name;
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

	public Map<Integer,Area> getAreas() {
		return this.getSubs();
	}

}



