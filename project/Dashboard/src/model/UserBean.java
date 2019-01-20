package model;

import java.util.HashSet;
import java.util.Set;

public class UserBean { 
	private int id;
	private String cf;
	private String username; 
	private String password;
	private String firstName; 
	private String lastName; 
	private String mail;
	private int type;
	private String status;
	public boolean valid; 
	private Set<Integer> cities = new HashSet<Integer>();
	private Set<Integer> areas = new HashSet<Integer>();
	private Set<Integer> buildings = new HashSet<Integer>();
	
	public void addCity(int id) {
		cities.add(id);
	}
	
	public void addArea(int id) {
		areas.add(id);
		System.out.println(id);
	}
	
	public void addBuilding(int id) {
		buildings.add(id);
	}
	
	public boolean checkCity(String id) {
		return cities.contains(Integer.parseInt(id));
	}
	
	public boolean checkArea(String id) {
		return areas.contains(Integer.parseInt(id));
	}
	
	public boolean checkBuilding(String id) {
		return buildings.contains(Integer.parseInt(id));
	}
	
	public boolean checkAdministrator() {
		if(type==4) 
			return true;
		else
			return false;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int newId) {
		id=newId;
	}
	public String getCF() { 
		return firstName; 
		}
	public void setCF(String newCf) {
		setCf(newCf);
		}
	public String getFirstName() { 
		return firstName; 
		}
	public void setFirstName(String newFirstName) {
		firstName = newFirstName;
		}
	public String getLastName() {
		return lastName;
		}
	public void setLastName(String newLastName) {
		lastName = newLastName; 
		}
	public String getPassword() {
		return password;
		}
	public void setPassword(String newPassword) { 
		password = newPassword; 
		} 
	public String getUsername() {
		return username;
		}
	public void setUserName(String newUsername) { 
		username = newUsername;
		}
	public boolean isValid() { 
		return valid; 
		} 
	public void setValid(boolean newValid) {
		valid = newValid; 
		} 
	public String getMail() {
		return mail;
		}
	public void setMail(String newMail) { 
		mail = newMail;
		}
	public int getType() {
		return type;
		}
	public void setType(int NewType) { 
		type = NewType;
		}
	public String getStatus() {
		return status;
		}
	public void setStatus(String newStatus) { 
		status = newStatus;
		}
	
	public Set<Integer> getBuildingsId(){
		return buildings;
	} 
	
	public Set<Integer> getAreasId(){
		return areas;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	} 

	}