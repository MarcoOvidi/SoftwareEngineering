package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import com.sun.xml.internal.bind.v2.model.core.ID;

import dao.DatabaseException;
import dao.SensorQuerySet;
import model.Sensor;


public class Cache2{
	/*private static Semaphore semaphore = new Semaphore(1);   
	private static List<Sensor> array = new ArrayList<Sensor>();
	private static Map<Integer,Sensor> map= new HashMap<Integer, Sensor>(); 
	private static List<Warning> warnings = new ArrayList<Warning>();
	
	public static void init() {
		System.out.println("Downloading sensors");
		try {
			List<Sensor> list=new ArrayList<Sensor>();
			
			for(int i=0; i<15; i++) {
				
				list.addAll(SensorQuerySet.getSensors(i));
				System.out.println("Package "+(i+1)+"/15");
			}
			
			for(Sensor curr: list) {
				insertSensor(curr);
				System.out.println(curr.getId());
				
			}
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void setSensor(Sensor s) throws InterruptedException {
		if(array[s.getId()]!=null)
			array[s.getId()]=new Sensor(s.getId(),s.getStatus(), s.getType(), s.getTreshold(), s.getValue(), s.getIdRoom());

	}
	
	public static void insertSensor(Sensor s) throws InterruptedException {
		semaphore.acquire();
			array[s.getId()]=new Sensor(s.getId(),s.getStatus(), s.getType(), s.getTreshold(), s.getValue(), s.getIdRoom());
		semaphore.release();
	}

	public static Sensor getSensor(int id) throws InterruptedException {
		semaphore.acquire();
		Sensor s=null;
		System.out.println(array[id]);
		if(array[id]!=null)
			s = new Sensor(array[id].getId(), array[id].getStatus(), array[id].getType(), array[id].getTreshold(), array[id].getValue(), array[id].getIdRoom());
		semaphore.release();
		return s;
	}
	
	public static void addWarning(Warning w) {
		
		warnings.add(w);
		
	}

	public static List<Warning> getWarnings(){
		return warnings;
	}
	
	public static List<Sensor> getSensorsByRoom(int IdRoom) throws InterruptedException{
		List<Sensor> list= new ArrayList();
		semaphore.acquire();
		for(int i=0; i<array.length; i++)
			if(array[i]!=null)
				if(array[i].getIdRoom()==IdRoom)
					list.add(new Sensor(array[i].getId(), array[i].getStatus(), array[i].getType(), array[i].getTreshold(), array[i].getValue(), array[i].getIdRoom()));
		semaphore.release();
		return list;
	
	}*/
}
