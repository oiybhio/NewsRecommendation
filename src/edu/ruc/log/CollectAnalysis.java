package edu.ruc.log;

import edu.ruc.user.User;

public class CollectAnalysis extends LogAnalysis {
	/**
     * Constructor for a CollectAnalysis.
     */
	public CollectAnalysis(User u, NewsAnalysis newsA, long StartTime) {
		super(u, newsA, StartTime);
	} 
	
	/**
     * Update user.
     * @return updated user's feature
     */
	public void UpdateUser(User user) {
		user.Update(newsA.getAttributes(),newsA.getNid(),1);	
	}
}
