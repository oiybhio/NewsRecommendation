package edu.ruc.log;

import java.io.PrintWriter;
import java.util.Date;

import net.sf.json.JSONObject;
import edu.ruc.data.Pair;
import edu.ruc.user.*;
import edu.ruc.news.*;
import edu.ruc.WebService.*;

public class Behavior implements Comparable{
	private long uid;
	private long nid;
	private int behave;
	private long startTime;
	private String comment;
	private long time;
	
	private LogAnalysis analysis;
	/**
     * Constructor for a behavior.
     * @param time  some behaviors have time record, such as browse <br>
     *              and some don't, such as click
     */
	public Behavior(String s){
		JSONObject json = JSONObject.fromObject(s);
		uid = json.getLong("UserID");
		String js = json.getString("json");
		json = JSONObject.fromObject(js);
		nid = json.getLong("NewsID");
		behave = json.getInt("behave");
		startTime = new Date(json.getString("Date")).getTime();
	}
	public Behavior(long uid, long nid, int behave, long startTime, long time) {
		this.uid = uid;
		this.nid = nid;
		this.behave = behave;
		this.startTime = startTime;
		this.time = time;
	}
	public Behavior(long uid, long nid, int behave, long startTime) {
		this.uid = uid;
		this.nid = nid;
		this.behave = behave;
		this.startTime = startTime;
	}
	public Behavior(long uid, long nid, int behave, long startTime, String comment) {
		this.uid = uid;
		this.nid = nid;
		this.behave = behave;
		this.startTime = startTime;
		this.comment = comment;
	}
	/**
     * Analyse user's behavior.
     */
	public void BehaveAnalyse(OnlineUsers Ousers,News news) {
		User u = Ousers.findUser(uid);
		NewsAnalysis na = new NewsAnalysis(news,nid);
		if(behave==BehaveType.Click.ordinal()) // Click 
			analysis = new ClickAnalysis(u,na,startTime);
		else if(behave==BehaveType.Collect.ordinal()) // Collect
			analysis = new CollectAnalysis(u,na,startTime);
		else if(behave==BehaveType.Browse.ordinal()) // Browse 
			analysis = new BrowseAnalysis(u,na,startTime,time);
		else if(behave==BehaveType.Comment.ordinal()) //Comment 
			analysis = new CommentAnalysis(u,na,startTime,comment);
	}
	
	/**
     * Update user's profile.
     */
	public void UpdateUserProfile(PrintWriter pw_log) {
		analysis.UpdateUser(pw_log);
	}
	
	/**
     * Get uid.
     */
	public long getUid() {
		return uid;
	}
	
	/**
     * Get nid.
     */
	public long getNid() {
		return nid;
	}
	
	/**
     * Get behave.
     */
	public int getBehave() {
		return behave;
	}
	
	/**
     * Get time.
     */
	
	public long time() {
		return time;
	}
	
    /**
     * Output the elements of behavior into screen.
     */    
    public void display() {
    	System.out.print( uid + "," + nid + "," + behave + "," + time);
    }
    
    /**
     * Comparator.
     */
    @Override
    public int compareTo(Object o) {
        if(this == o) {
            return 0;            
        }
        else if (o != null && o instanceof Pair) {   
            Behavior b = (Behavior) o; 
            if(uid <= b.uid){
                return -1;
            }
            else {
            	return 1;
            }
        }else{
        	return -1;
        }
    }
    
}
