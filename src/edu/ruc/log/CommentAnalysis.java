package edu.ruc.log;

import edu.ruc.WebService.BehaveType;
import edu.ruc.user.User;
import edu.ruc.weight.TimeWeight;

public class CommentAnalysis extends LogAnalysis{
	private String comment;
	/**
     * Constructor for a CollectAnalysis.
     */
	public CommentAnalysis(User u, NewsAnalysis newsA, long StartTime, String comment) {
		super(u, newsA, StartTime);
		this.comment = comment;
		tw = new TimeWeight(BehaveType.Comment.ordinal(), StartTime);
	}
	
	/**
     * Update user.
     * @return updated user's feature
     */
	public void UpdateUser(User user) {
		user.Update(newsA.getAttributes(),newsA.getNid(),tw.getWeight());
	}
}
