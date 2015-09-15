package edu.ruc.WebService;
import net.sf.json.*;
public class Login extends DataAnalysis{
	private long UserID;
	private String Password;
	public Login(JSONObject json) {
		UserID = json.getLong("UserID");
		Password = json.getString("Password");
	}
	public long getUserID() {
		return UserID;
	}
}
