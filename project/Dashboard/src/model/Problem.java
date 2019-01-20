package model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import controller.ProblemSolver;
import controller.SimpleProblemSolver;

public class Problem {

	private static ProblemSolver problemSolver = new SimpleProblemSolver();

	enum types {
		alert, warning, sensorDown
	};

	types type = types.alert;

	private boolean danger = false;
	private ArrayList<Integer> sensorTypes;
	private ArrayList<Integer> sensorTypesNotWorking;

	private Instant firstAlert;
	private Instant firstWarning;
	private Instant firstDanger;
	private Instant firstNotWorking;

	private Room room;
	private HashMap<LockedSensor, Instant> sensors;
	private HashMap<LockedSensor, Instant> notWorking; // Instant means last instant of last received value

	// creates a new problem for a sensor above threshold
	public Problem(LockedSensor lockedSensor) {
		sensors = new HashMap<LockedSensor, Instant>();
		sensorTypes = new ArrayList<Integer>();
		notWorking = new HashMap<LockedSensor, Instant>();
		sensorTypesNotWorking = new ArrayList<Integer>();

		int sensorType;
		Instant timestamp;
		lockedSensor.readLock();
		timestamp = lockedSensor.getSensor().getLastValue();
		sensorType = lockedSensor.getSensor().getType();
		room = lockedSensor.getSensor().getRoom();
		lockedSensor.readUnlock();

		// lockedSensor.writeLock();
		lockedSensor.getSensor().setColor("alert");
		// lockedSensor.writeUnlock();

		type = types.alert;
		sensors.put(lockedSensor, timestamp);
		setFirstAlert(timestamp);
		sensorTypes.add(sensorType);

		propagate();
	}

	// creates a new problem for a missing sensor
	public Problem(LockedSensor lockedSensor, boolean notSendingValues) {
		sensors = new HashMap<LockedSensor, Instant>();
		sensorTypes = new ArrayList<Integer>();
		notWorking = new HashMap<LockedSensor, Instant>();
		sensorTypesNotWorking = new ArrayList<Integer>();

		int sensorType;
		Instant timestamp;
		lockedSensor.readLock();
		timestamp = lockedSensor.getSensor().getLastValue();
		sensorType = lockedSensor.getSensor().getType();
		room = lockedSensor.getSensor().getRoom();
		lockedSensor.readUnlock();

		// lockedSensor.writeLock();
		lockedSensor.getSensor().setColor("notWorking");
		// lockedSensor.writeUnlock();

		type = types.sensorDown;
		notWorking.put(lockedSensor, timestamp);
		setFirstNotWorking(timestamp);
		sensorTypesNotWorking.add(sensorType);

		propagate();
	}

	private void propagate() {
		// System.out.println("propagate new problem");
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
		int sensorType;
		Instant timestamp;
		lockedSensor.getLock().readLock().lock();
		timestamp = lockedSensor.getSensor().getLastValue();
		sensorType = lockedSensor.getSensor().getType();
		assert (lockedSensor.getSensor().getRoom() == getRoom());
		lockedSensor.getLock().readLock().unlock();

		if (type == types.sensorDown) {
			type = types.alert;
			setFirstAlert(timestamp);

			// lockedSensor.writeLock();
			// lockedSensor.writeUnlock();
		}
		sensors.put(lockedSensor, timestamp);

		if (isAlert() && sensorType != getFirstType()) {
			type = types.warning;
			setFirstWarning(Instant.now());

			// lockedSensor.writeLock();
			// lockedSensor.getSensor().setColor("warning");
			// lockedSensor.writeUnlock();
		}
		if (!sensorTypes.contains(sensorType))
			sensorTypes.add(sensorType);

		lockedSensor.getSensor().setColor("alert");
	}

	public void addMissingSensor(LockedSensor lockedSensor) {
		int sensorType;
		Instant timestamp;
		lockedSensor.getLock().readLock().lock();
		timestamp = lockedSensor.getSensor().getLastValue();
		sensorType = lockedSensor.getSensor().getType();
		assert (lockedSensor.getSensor().getRoom() == getRoom());
		lockedSensor.getLock().readLock().unlock();

		notWorking.put(lockedSensor, timestamp);
		if (notWorking.size() != 0)
			setFirstNotWorking(timestamp);
		if (!sensorTypesNotWorking.contains(sensorType))
			sensorTypesNotWorking.add(sensorType);

		// lockedSensor.writeLock();
		lockedSensor.getSensor().setColor("notWorking");
		// lockedSensor.writeUnlock();
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

	public boolean isNotWorking() {
		return (type == types.sensorDown);
	}

	public boolean isThereAnyNotWorkingSensor() {
		return (notWorking.size() != 0);
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

	public Instant getFirstNotWorking() {
		return firstNotWorking;
	}

	private void setFirstNotWorking(Instant firstNotWorking) {
		this.firstNotWorking = firstNotWorking;
	}

	public ArrayList<Integer> getSensorTypesNotWorking() {
		return sensorTypesNotWorking;
	}

	public int getSensorNumber() {
		return sensors.size();
	}

	public int getNotWorkingNumber() {
		return notWorking.size();
	}

	public int getSensorTypesNumber() {
		return sensorTypes.size();
	}

	public ArrayList<Notification> getNotifications() {
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		Notification danger = problemSolver.processProblem(this);
		if (danger != null)
			notifications.add(danger);
		for (LockedSensor ls : notWorking.keySet()) {
			ls.readLock();
			notifications
					.add(new Notification("notWorking", "Sensor " + ls.getSensor().getId() + " not working!", this));
			ls.readUnlock();
		}
		return notifications;
	}
}
