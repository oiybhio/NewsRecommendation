package edu.ruc.log;
import edu.ruc.user.*;
public class ClickAnalysis extends LogAnalysis {
	
	/**
     * Constructor for a ClickAnalysis.
     */
	public ClickAnalysis(User u, NewsAnalysis newsA, long StartTime) {
		super(u, newsA, StartTime);
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Update user.
     * @return updated user's feature
     */
	public void UpdateUser(User user) {
		user.Update(newsA.getAttributes(),1.0);
	}
}
