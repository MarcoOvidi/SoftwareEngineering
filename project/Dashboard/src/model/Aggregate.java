package model;

import java.util.HashMap;
import java.util.Map;

abstract class Aggregate<T>{
	private int id;
	private int sensorsNumber;
	private String name;
	private char status;
	
	protected HashMap<Integer,Problem> problems = new HashMap<Integer,Problem>();
	
	protected Map<Integer,T> subs = new HashMap<Integer,T>();
	
	public Aggregate(int id) {
		this.id=id;
	};
	
	public int getId() {
		return id;
	};
	
	public String getName() {
		return name;
	};
	
	public char getStatus(){
		return status;
	};
	
	public int getSensorsNumber() {
		return sensorsNumber;
	};
	
	public Map<Integer,T> getSubs(){
		return subs;
	};
	
	
	public void clearSubs(){
		subs.clear();
	}
	
	public void addSub(int id,T newSub) {
		subs.putIfAbsent(id, newSub);
	}
	
	public boolean hasSub(int id) {
		return subs.containsKey(id);
	}

	public HashMap<Integer,Problem> getProblems() {
		return problems;
	}
	
	public void addProblem(Problem problem) {
		problems.putIfAbsent(problem.getRoomID(), problem);
	}
	
	public void removeProblem(Problem problem) {
		problems.remove(problem.getRoomID());
	}
}
