package edu.ruc.WebService;
import java.util.Date;

import net.sf.json.*;
public class Click extends DataAnalysis{
	private long UserID;
	private long NewsID;
	private Date date;
	
	@SuppressWarnings("deprecation")
	public Click(JSONObject json) {
		UserID = json.getLong("UserID");
		NewsID = json.getLong("NewsID");
		date = new Date(json.getString("Date"));
	}
}
