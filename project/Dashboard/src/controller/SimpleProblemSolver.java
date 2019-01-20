package controller;

import model.Notification;
import model.Problem;

public class SimpleProblemSolver implements ProblemSolver {

	@Override
	public Notification processProblem(Problem problem) {
		if (problem.getSensorNumber() > 2) {
			Notification danger = new Notification("danger","Potential fire!",problem);
			problem.setDanger();
			return danger;
		}
		else
			return null;
	}

}
