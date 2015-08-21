package edu.ruc.WebService;
import net.sf.json.*;
public class Modify extends DataAnalysis{
	private long UserID;
	private String Info;
	public Modify(JSONObject json) {
		UserID = json.getLong("UserID");
		Info = json.getString("Info");
	}
}
