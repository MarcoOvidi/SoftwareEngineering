package controller;
import java.time.Instant;

import model.Sensor;

public class Checker {
	
	public static void newSensorValue(int id, int value) throws InterruptedException{
		//se null?
		Instant valueInstant = Instant.now();
		int threshold = Cache.setSensorValue(id, value,valueInstant);
		//TODO insert in time series db
		
		if(value > threshold) {
			//System.out.println("over");
			Cache.addProblem(id);

		}
	}
	
	public static void addWarningToCache(Sensor s) {
		//to do
	}
}
