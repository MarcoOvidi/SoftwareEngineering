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

	public void readLock() {
		l.readLock().lock();
	}
	public void readUnlock() {
		l.readLock().unlock();
	}
	public void writeLock() {
		l.writeLock().lock();
	}
	public void writeUnlock() {
		l.writeLock().unlock();
	}
}
