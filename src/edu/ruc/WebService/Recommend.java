package edu.ruc.WebService;
import java.util.Date;

import net.sf.json.*;
public class Recommend extends DataAnalysis{
	private long UserID;
	private Date date;
	
	@SuppressWarnings("deprecation")
	public Recommend(JSONObject json) {
		UserID = json.getLong("UserID");
		date = new Date(json.getString("Date"));
	}
}
