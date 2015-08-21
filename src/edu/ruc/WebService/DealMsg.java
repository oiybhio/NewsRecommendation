package edu.ruc.WebService;

import net.sf.json.*;

public class DealMsg {
	private String Msg;
	private JSONObject json;
	private int behave;
	private DataAnalysis da;
	public DealMsg(String Msg) {
		this.Msg = Msg;
		json = JSONObject.fromObject(Msg);
		behave = json.getInt("behave");
	}
	public void start() {
		if(behave == BehaveType.Click.ordinal())
			da = new Click(json);
		if(behave == BehaveType.Comment.ordinal())
			da = new Comment(json);
		if(behave == BehaveType.Log.ordinal())
			da = new Log(json);
		if(behave == BehaveType.Login.ordinal())
			da = new Login(json);
		if(behave == BehaveType.Modify.ordinal())
			da = new Modify(json);
		if(behave == BehaveType.Query.ordinal())
			da = new Query(json);
		if(behave == BehaveType.Recommend.ordinal())
			da = new Recommend(json);
		if(behave == BehaveType.Refresh.ordinal())
			da = new Refresh(json);
		if(behave == BehaveType.Register.ordinal())
			da = new Register(json);
	}
}
