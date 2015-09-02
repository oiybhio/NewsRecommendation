package edu.ruc.WebService;

import edu.ruc.log.ClickAnalysis;
import edu.ruc.log.NewsAnalysis;
import edu.ruc.news.News;
import edu.ruc.user.User;

public class DataAnalysis {
	public long UserID;
	public long NewsID;
	public void BehaveAnalyse(User u,News news) {
	}
	/**
     * Update user's profile.
     */
	public void UpdateUserProfile() {
	}
	/**
     * Get uid.
     */
	public long getUid() {
		return UserID;
	}
	
	/**
     * Get nid.
     */
	public long getNid() {
		return NewsID;
	}
}
