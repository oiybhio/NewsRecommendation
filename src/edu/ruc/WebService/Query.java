package edu.ruc.WebService;
import java.util.Date;

import net.sf.json.*;
public class Query extends DataAnalysis{
	private long UserID;
	private String Topic;
	private Date date;
	private String RankMode;
	
	@SuppressWarnings("deprecation")
	public Query(JSONObject json) {
		UserID = json.getLong("UserID");
		Topic = json.getString("Toic");
		date = new Date(json.getString("Date"));
		RankMode = json.getString("RankMode");
	}
}
