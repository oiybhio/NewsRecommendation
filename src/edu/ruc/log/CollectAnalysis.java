package edu.ruc.log;

import edu.ruc.WebService.BehaveType;
import edu.ruc.user.User;
import edu.ruc.weight.TimeWeight;

public class CollectAnalysis extends LogAnalysis {
	/**
     * Constructor for a CollectAnalysis.
     */
	public CollectAnalysis(User u, NewsAnalysis newsA, long StartTime) {
		super(u, newsA, StartTime);
		tw = new TimeWeight(BehaveType.Collect.ordinal(), StartTime);
	} 
	
	/**
     * Update user.
     * @return updated user's feature
     */
	public void UpdateUser(User user) {
		user.Update(newsA.getAttributes(),newsA.getNid(),tw.getWeight());	
		
	}
}
