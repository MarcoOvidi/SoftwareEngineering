package model;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockedSensor {
	private Sensor s;
	private ReentrantReadWriteLock l=new ReentrantReadWriteLock();
	
	public LockedSensor(Sensor s) {
		this.s=s;
	}
	
	public Sensor getSensor() {
		return s;
	}
	
	public void setSensor(Sensor s){
		this.s=s;
	}

	public ReentrantReadWriteLock getLock() {
		return l;
	}
}
