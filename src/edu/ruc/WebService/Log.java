package edu.ruc.WebService;
import java.util.Date;

import net.sf.json.*;
public class Log extends DataAnalysis{
	private long UserID;
	private long NewsID;
	private Date StartTime;
	private long Time;
	private int Action;
	private String Info;
	
	@SuppressWarnings("deprecation")
	public Log(JSONObject json) {
		UserID = json.getLong("UserID");
		NewsID = json.getLong("NewsID");
		StartTime = new Date(json.getString("StartTime"));
		Time = json.getLong("Time");
		Action = json.getInt("Action");
		Info = json.getString("Info");
	}
}
