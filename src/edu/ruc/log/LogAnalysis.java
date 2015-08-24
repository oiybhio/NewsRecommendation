package edu.ruc.log;

import edu.ruc.user.User;

public class LogAnalysis {
	public User user;
	public NewsAnalysis newsA;
	public long startTime;
	public long time;
	/**
     * Constructor for a LogAnalysis.
     */
	public LogAnalysis(User u, NewsAnalysis newsA, long startTime) {
		user = u;
		this.newsA = newsA;
		this.startTime = startTime;
	}
	
	/**
     * Update user.
     * @return updated user's features.
     */
	public void UpdateUser() {
	//	user.Update();
	}
}
