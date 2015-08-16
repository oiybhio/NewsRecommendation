package edu.ruc.log;

import edu.ruc.user.User;

public class BrowseAnalysis extends LogAnalysis {
	private long time;
	/**
     * Constructor for a BrowseAnalysis.
     */
	public BrowseAnalysis(User u, NewsAnalysis newsA, long startTime, long time) {
		super(u, newsA, startTime);
		this.time = time;
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Update user.
     * @return updated user's feature
     */
	public void UpdateUser(User user) {
	}
}
