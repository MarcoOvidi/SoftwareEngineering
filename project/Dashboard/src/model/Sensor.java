package model;

import java.time.Instant;

public class Sensor {

	protected int id;
	protected boolean status;
	protected Integer type;
	protected int treshold;
	protected int value;
	protected int ID_Room; //TODO remove, redundant
	protected Room room;
	protected Instant lastValue;
	
	private String color = "";
	
	// private List<Integer> values = new ArrayList<Integer>();

	public Sensor(int id, int value) {
		this.id = id;
		this.value=value;
	}

	public Sensor(int id, boolean status, Integer type, int treshold, int value, int IdRoom, Room room) {
		super();
		this.id = id;
		this.status = status;
		this.type = new Integer (type);
		this.treshold = treshold;
		this.value=value;
		this.ID_Room = IdRoom;
		this.room = room;
	}
	
	public Sensor(Sensor sensor) {
		super();
		this.id = sensor.id;
		this.status = sensor.status;
		this.type = new Integer (sensor.type);
		this.treshold = sensor.treshold;
		this.value=sensor.value;
		this.ID_Room = sensor.ID_Room;
		this.room = sensor.room;
	}

	public int getIdRoom() {
		return this.ID_Room;
	}
	
	public void setIdRoom(int number) {
		this.ID_Room=number;
	}
	
	public int getId() {
		return id;
	}

	public Integer getType() {
		return type;
	}

	public int getValue() {
		return value;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Boolean getStatus() {
		return status;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getTreshold() {
		return treshold;
	}

	public void setTreshold(int treshold) {
		this.treshold = treshold;
	}

	public void setValue(int value, Instant valueInstant) {
		this.value = value;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room=room;
	}

	public Instant getLastValue() {
		return lastValue;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
