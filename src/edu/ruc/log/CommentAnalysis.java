package edu.ruc.log;

import edu.ruc.user.User;

public class CommentAnalysis extends LogAnalysis{
	private String comment;
	/**
     * Constructor for a CollectAnalysis.
     */
	public CommentAnalysis(User u, long nid, long StartTime, String comment) {
		super(u, nid, StartTime);
		this.comment = comment;
	} 
	
	/**
     * Update user.
     * @return updated user's feature
     */
	public void UpdateUser(User user) {
		
	}
}
