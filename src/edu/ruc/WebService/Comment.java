package edu.ruc.WebService;
import java.util.Date;

import net.sf.json.*;
public class Comment extends DataAnalysis{
	private long UserID;
	private long NewsID;
	private String comment;
	private Date date;
	
	@SuppressWarnings("deprecation")
	public Comment(JSONObject json) {
		UserID = json.getLong("UserID");
		NewsID = json.getLong("NewsID");
		comment = json.getString("comment");
		date = new Date(json.getString("Date"));
	}
}
