package model;

import java.util.HashMap;
import java.util.Map;

abstract class Aggregate<T>{
	private int id;
	private int sensorsNumber;
	private String name;
	private char status; 
	protected Map<Integer,T> subs = new HashMap<Integer,T>();
	
	public Aggregate(int id) {
		this.id=id;
	};
	
	public int getId() {
		return id;
	};
	
	public String getName() {
		return name;
	};
	
	public char getStatus(){
		return status;
	};
	
	public int getSensorsNumber() {
		return sensorsNumber;
	};
	
	public Map<Integer,T> getSubs(){
		return subs;
	};
	
	public boolean hasSub(int id) {
		return subs.containsKey(id);
	}
}
