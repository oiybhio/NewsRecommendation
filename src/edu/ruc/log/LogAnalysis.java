package edu.ruc.log;

import edu.ruc.user.User;

public class LogAnalysis {
	private User user;
	private NewsAnalysis newsA;
	private long startTime;
	private long time;
	/**
     * Constructor for a LogAnalysis.
     */
	public LogAnalysis(User u, long nid, long startTime, long time) {
		user = u;
		newsA = new NewsAnalysis(nid);
		this.time = time;
		this.startTime = startTime;
	}
	public LogAnalysis(User u, long nid, long StartTime) {
		user = u;
		newsA = new NewsAnalysis(nid);
		this.startTime = startTime;
	} 
	
	/**
     * Update user.
     * @return updated user's features.
     */
	public void UpdateUser() {
	}
}
