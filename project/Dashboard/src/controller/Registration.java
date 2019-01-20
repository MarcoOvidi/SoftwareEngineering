package controller;

import dao.DatabaseException;
import dao.SensorQuerySet;
public class Registration {

public static void sensorRegistration(int ID_sensor, boolean status, Integer type, int treshold, int value, int roomID)throws InterruptedException, DatabaseException {
	SensorQuerySet.createPlacedSensor (ID_sensor, status, type, treshold, value, roomID) ; // insert the new sensor in the database
	
	//Cache.insertSensor(id, status, type, treshold, value, roomID); // insert the new sensor in the cache
	}
}