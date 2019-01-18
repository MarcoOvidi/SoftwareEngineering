package model;

import java.time.Instant;
import java.util.TreeMap;

public class Alert{

	private int type;
	private Room room;
	private TreeMap<Sensor,Instant> sensors;
	
	public Alert(Sensor sensor){
		sensors = new TreeMap<Sensor,Instant>();
		sensors.put(sensor,Instant.now());
		type = sensor.getType();
		room = sensor.getRoom();
	}
	
	public void addSensor(Sensor sensor) {
		if (sensor.getType() != getType())
			return;
		if (sensor.getRoom() != getRoom())
			return;
		
		sensors.put(sensor,Instant.now());
	}
	
	public int getType() {
		return type;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public int getCount() {
		return sensors.size();
	}

}
