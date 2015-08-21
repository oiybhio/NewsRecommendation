package edu.ruc.WebService;
import net.sf.json.*;
public class Register extends DataAnalysis{
	private long UserID;
	private String Password;
	private String Info;
	public Register(JSONObject json) {
		UserID = json.getLong("UserID");
		Password = json.getString("Password");
		Info = json.getString("Info");
	}

}
