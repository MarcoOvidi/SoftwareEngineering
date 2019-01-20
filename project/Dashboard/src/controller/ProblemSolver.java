package controller;

import model.Notification;
import model.Problem;

public interface ProblemSolver {
	public Notification processProblem(Problem problem);
}
