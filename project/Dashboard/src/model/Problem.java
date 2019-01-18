package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.TreeMap;

public class Problem{
	enum types {alert,warning};

	types type = types.alert;
	
	private boolean danger = false;
	private ArrayList<Integer> sensorTypes;
	
	private Instant firstAlert;
	private Instant firstWarning;
	private Instant firstDanger;
	
	private Room room;
	private TreeMap<LockedSensor,Instant> sensors;
	
	public Problem(LockedSensor lockedSensor){
		Instant now = Instant.now();
		sensors = new TreeMap<LockedSensor,Instant>();
		sensors.put(lockedSensor,now);
		
		setFirstAlert(now);
		
		Sensor sensor = lockedSensor.getSensor();
		
		sensorTypes = new ArrayList<Integer>();
		sensorTypes.add(sensor.getType());
		
		room = sensor.getRoom();
		
		propagate();
	}

	private void propagate() {
		room.getFloor().addProblem(this);
		room.getFloor().getBuilding().addProblem(this);
		room.getFloor().getBuilding().getArea().addProblem(this);
		room.getFloor().getBuilding().getArea().getCity().addProblem(this);
	}
	
	public void destroy() {
		withdraw();
		room.removeProblem(this);
	}
	
	private void withdraw() {
		room.getFloor().getBuilding().getArea().getCity().removeProblem(this);
		room.getFloor().getBuilding().getArea().removeProblem(this);
		room.getFloor().getBuilding().removeProblem(this);
		room.getFloor().removeProblem(this);
	}
	
	public void addSensor(LockedSensor lockedSensor) {
		Sensor sensor = lockedSensor.getSensor();
		assert (sensor.getRoom() == getRoom());
		
		if (isAlert() && sensor.getType() != getFirstType()) {
			sensorTypes.add(sensor.getType());
			type = types.warning;
			setFirstWarning(Instant.now());
		}
		
		
		sensors.put(lockedSensor,Instant.now());
	}
	
	public int getFirstType() {
		return sensorTypes.get(0);
	}

	public Room getRoom() {
		return room;
	}
	
	public int getRoomID() {
		return room.getId();
	}
	
	public int getCount() {
		return sensors.size();
	}

	public boolean isDanger() {
		return danger;
	}

	public void setDanger(boolean danger) {
		this.danger = danger;
		setFirstDanger(Instant.now());
	}
	public void setDanger() {
		setDanger(true);
	}

	public boolean isAlert() {
		return (type == types.alert);
	}
	
	public boolean isWarning() {
		return (type == types.warning);
	}

	public Instant getFirstAlert() {
		return firstAlert;
	}

	private void setFirstAlert(Instant firstAlert) {
		this.firstAlert = firstAlert;
	}

	public Instant getFirstWarning() {
		return firstWarning;
	}

	private void setFirstWarning(Instant firstWarning) {
		this.firstWarning = firstWarning;
	}

	public Instant getFirstDanger() {
		return firstDanger;
	}

	private void setFirstDanger(Instant firstDanger) {
		this.firstDanger = firstDanger;
	}

}
