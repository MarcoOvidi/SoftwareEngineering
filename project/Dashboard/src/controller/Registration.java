package controller;

import dao.DatabaseException;
import dao.SensorQuerySet;
import model.Room;
import model.Sensor;
public class Registration {
 

public static void sensorregistration(int id, boolean status, Integer type, int treshold, int value, int roomID)throws InterruptedException, DatabaseException {
	Room room = Cache.getRoomByID(roomID);
	Sensor s = new Sensor(id,status,type,treshold,value,roomID,room);
	Cache.insertSensor(s); // insert the new sensor in the cache
	SensorQuerySet.createPlacedSensor (s) ; // insert the new sensor in the database
	}
}