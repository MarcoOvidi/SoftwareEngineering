package model;

import java.util.HashMap;
import java.util.Map;

public abstract class Aggregate<T> {
	protected int id;
	protected int sensorsNumber;
	protected char status;
	
	protected HashMap<Integer,Problem> problems = new HashMap<Integer,Problem>();
	
	protected Map<Integer,T> subs = new HashMap<Integer,T>();
	
	public Aggregate(int id) {
		this.id=id;
	};
	
	public int getId() {
		return id;
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
	
//	public void clearSubs(){
//		subs.clear();
//	}
	
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
	
	public String problemsDescription() {
		int numAlert = 0;
		int numAlertRooms = 0;
		int numWarning = 0;
		int numWarningRooms = 0;
		int numNotWorking = 0;
		int numNotWorkingRooms = 0;
		String descr = "";
		
		for (Problem p : problems.values()) {
			if(p.isThereAnyNotWorkingSensor()) {
				numNotWorkingRooms++;
				numNotWorking += p.getNotWorkingNumber();
			}
			else if (p.isAlert()) {
				numAlertRooms++;
				numAlert += p.getSensorNumber();
			}
			else if (p.isWarning()){
				numWarningRooms++;
				numWarning+= p.getSensorNumber();
			}
		}

		if (numWarning > 0) {
			descr += "Warning for " + numWarning + " sensors in " + numWarningRooms + " rooms. ";
		}
		if (numAlert > 0) {
			descr += "Alert for " + numAlert + " sensors in " + numAlertRooms + " rooms. ";
		}
		if (numNotWorking > 0) {
			descr += numNotWorking + " not working sensors in " + numNotWorkingRooms+ " rooms. ";
		}
		
		//System.out.println(descr);
		return descr;
	}
}
