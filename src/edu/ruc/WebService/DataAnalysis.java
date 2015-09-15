package edu.ruc.WebService;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import edu.ruc.data.Alphabet;
import edu.ruc.data.Dictionary;
import edu.ruc.database.NewsDatabase;
import edu.ruc.log.ClickAnalysis;
import edu.ruc.log.NewsAnalysis;
import edu.ruc.news.News;
import edu.ruc.ranker.ResultStore;
import edu.ruc.user.OnlineUsers;
import edu.ruc.user.User;

public class DataAnalysis {
	public long UserID;
	public long NewsID;
	public void BehaveAnalyse(User u,News news) {
	}
	/**
     * Update user's profile.
     */
	public void UpdateUserProfile(PrintWriter pw_log) {
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
	/**
	 * store Log into database
	 * @param CON
	 * @throws SQLException 
	 */
	public void deal(OnlineUsers users,NewsDatabase newsData, ResultStore resultStore, 
			Dictionary dict,Alphabet attributeSet, Connection con)throws Exception  {
		
	}
}
