package model;

import java.util.concurrent.atomic.AtomicInteger;

public class Sensor {

	private int id;
	private boolean status;
	private Integer type;
	private int treshold;
	private int value;
	private int ID_Room;
	private Room room;
	
	// private List<Integer> values = new ArrayList<Integer>();

	public Sensor(int id, int value) {
		this.id = id;
		this.value=value;
	}

	public Sensor(int id, boolean status, Integer type, int treshold, int value, int IdRoom, Room room) {
		super();
		this.id = id;
		this.status = status;
		this.type = type;
		this.treshold = treshold;
		this.value=value;
		this.ID_Room = IdRoom;
		this.room = room;
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

	public void setValue(int value) {
		this.value = value;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setRoom(Room room) {
		this.room=room;
	}
}
