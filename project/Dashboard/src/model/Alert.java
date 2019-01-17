package model;

public class Alert{
	
	public static int c=0;
	
	private int id=0;
	private int idSensor;
	private int idRoom;
	private int idFloor;
	private int idBuilding;
	private int idArea;
	private int idCity;
	
	public Alert(int idSensor, int idRoom, int idFloor, int idBuilding, int idArea, int idCity){
		this.idSensor=idSensor;
		this.idRoom=idRoom;
		this.idFloor=idFloor;
		this.idBuilding=idBuilding;
		this.idArea=idArea;
		this.idCity=idCity;
		this.id=c;
		c++;
	}
	
	int getIdSensor() {
		return idSensor;
	}
	
	int getIdRoom() {
		return idRoom;
	}
	
	int getIdFloor() {
		return idFloor;
	}
	
	int getIdBuilding() {
		return idBuilding;
	}
	
	int getIdArea() {
		return idArea;
	}
	
	int getIdCity() {
		return idCity;
	}
	
	public int getId(){
		return id;
	}

}
