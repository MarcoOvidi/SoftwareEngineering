package model;

public class Notification {
	private String type = "";
	private String message = "";
	
	Problem problem;
	
	public Notification(String type,String message, Problem problem) {
		this.type = type;
		this.message = message;
		this.problem = problem;
	}
	
	public String getType() {
		return type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Problem getProblem() {
		return problem;
	}
	
	public Room getRoom() {
		return problem.getRoom();
	}
}
