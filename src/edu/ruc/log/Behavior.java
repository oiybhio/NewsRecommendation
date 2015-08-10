package edu.ruc.log;

import edu.ruc.data.Pair;
import edu.ruc.user.*;

public class Behavior implements Comparable{
	private long uid;
	private long nid;
	private String behave;
	private long startTime;
	private String comment;
	private long time;
	
	private LogAnalysis analysis;
	/**
     * Constructor for a behavior.
     * @param time  some behaviors have time record, such as browse <br>
     *              and some don't, such as click
     */
	
	public Behavior(long uid, long nid, String behave, long startTime, long time) {
		this.uid = uid;
		this.nid = nid;
		this.behave = behave;
		this.startTime = startTime;
		this.time = time;
	}
	public Behavior(long uid, long nid, String behave, long startTime) {
		this.uid = uid;
		this.nid = nid;
		this.behave = behave;
		this.startTime = startTime;
	}
	public Behavior(long uid, long nid, String behave, long startTime, String comment) {
		this.uid = uid;
		this.nid = nid;
		this.behave = behave;
		this.startTime = startTime;
		this.comment = comment;
	}
	/**
     * Analyse user's behavior.
     */
	public void BehaveAnalyse(OnlineUsers Ousers) {
		User u = Ousers.findUser(uid);
		if(behave.equals("click")) 
			analysis = new ClickAnalysis(u,nid,startTime);
		else if(behave.equals("collect")) 
			analysis = new CollectAnalysis(u,nid,startTime);
		else if(behave.equals("browse")) 
			analysis = new BrowseAnalysis(u,nid,startTime,time);
		else if(behave.equals("comment")) 
			analysis = new CommentAnalysis(u,nid,startTime,comment);
	}
	
	/**
     * Update user's profile.
     */
	public void UpdateUserProfile() {
		analysis.UpdateUser();
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
	public String getBehave() {
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
