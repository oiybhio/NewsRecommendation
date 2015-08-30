package edu.ruc.log;

import edu.ruc.WebService.BehaveType;
import edu.ruc.user.User;
import edu.ruc.weight.TimeWeight;

public class BrowseAnalysis extends LogAnalysis {
	private long time;
	/**
     * Constructor for a BrowseAnalysis.
     */
	public BrowseAnalysis(User u, NewsAnalysis newsA, long startTime, long time) {
		super(u, newsA, startTime);
		this.time = time;
		// TODO Auto-generated constructor stub
		tw = new TimeWeight(BehaveType.Browse.ordinal(), startTime);
	}
	
	/**
     * Update user.
     * @return updated user's feature
     */
	public void UpdateUser(User user) {
		user.Update(newsA.getAttributes(),newsA.getNid(),tw.getWeight());
	}
}
