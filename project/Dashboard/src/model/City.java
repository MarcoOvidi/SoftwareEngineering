package model;

import java.util.ArrayList;
import java.util.List;

import dao.AreaQuerySet;
import dao.BuildingQuerySet;
import dao.CityQuerySet;
import dao.DatabaseException;

public class City extends Aggregate<Area> {

	int IdCity;
	String name;
	
	public City(int id) throws DatabaseException, InterruptedException {
		super(id);
		getAreas();
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

	public void getAreas() throws DatabaseException, InterruptedException{
		List<Area> list =  CityQuerySet.getAreas(getId(), this);
		for(Area b : list)
			subs.put(b.getId(), b);
	};

}



