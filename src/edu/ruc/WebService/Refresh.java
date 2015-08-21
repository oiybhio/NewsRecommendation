package edu.ruc.WebService;
import java.util.Date;

import net.sf.json.*;
public class Refresh extends DataAnalysis{
	private long UserID;
	private Date date;
	
	@SuppressWarnings("deprecation")
	public Refresh(JSONObject json) {
		UserID = json.getLong("UserID");
		date = new Date(json.getString("Date"));
	}
}
