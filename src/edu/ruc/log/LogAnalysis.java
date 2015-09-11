package edu.ruc.log;
import java.io.PrintWriter;

import edu.ruc.weight.*;
import edu.ruc.WebService.BehaveType;
import edu.ruc.user.User;

public class LogAnalysis {
	public User user;
	public NewsAnalysis newsA;
	public long startTime;
	public long time;
	public TimeWeight tw;
	/**
     * Constructor for a LogAnalysis.
     */
	public LogAnalysis(User u, NewsAnalysis newsA, long startTime) {
		user = u;
		this.newsA = newsA;
		this.startTime = startTime;
		tw = new TimeWeight(BehaveType.Click.ordinal(), startTime);
	}
	
	/**
     * Update user.
     * @return updated user's features.
     */
	public void UpdateUser(PrintWriter pw_log) {
		user.Update(newsA.getAttributes(),newsA.getNid(),tw.getWeight());
	}
}
