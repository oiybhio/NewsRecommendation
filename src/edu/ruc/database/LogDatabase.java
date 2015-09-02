package edu.ruc.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.crypto.Data;

import net.sf.json.JSONObject;
import edu.ruc.WebService.BehaveType;
import edu.ruc.data.Alphabet;
import edu.ruc.data.Attribute;
import edu.ruc.data.Dictionary;
import edu.ruc.data.VectorType;
import edu.ruc.log.Behavior;
import edu.ruc.news.News;
import edu.ruc.user.OnlineUsers;
import edu.ruc.user.User;

public class LogDatabase {
	private Connection con;
    public void setConnection(Connection con){
		  this.con=con;
	}
    public void saveLog(String s) throws SQLException {
    	String strsql = "insert into log (uid, nid, behave, startTime, comment, time)" + " values(?,?,?,?,?,?)";
    	JSONObject json = JSONObject.fromObject(s);
		long uid = json.getLong("uid");
		long nid = json.getLong("nid");
		int behave = json.getInt("behave");
		long startTime = json.getLong("startTime");
		String comment = json.getString("comment");
		long time = json.getLong("time");

		PreparedStatement pstmt = con.prepareStatement(strsql);
	    pstmt.setLong(1, uid);
	    pstmt.setLong(2, nid);
	    pstmt.setInt(3, behave);
	    pstmt.setLong(4, startTime);
	    pstmt.setString(5, comment);
	    pstmt.setLong(6, time);
	    pstmt.execute();
    }
    public Behavior getBehavior(long uid, long nid, Data time) {
    	return null;
    }
}
