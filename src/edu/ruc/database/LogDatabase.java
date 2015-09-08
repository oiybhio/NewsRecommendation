package edu.ruc.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
    	JSONObject json = JSONObject.fromObject(s);
    	String strsql = "insert into log(logID, uid, json, flag)" + " values(?,?,?,?)";
		long uid = json.getLong("UserID");
		String logID = ""+System.currentTimeMillis()+""+uid;

		PreparedStatement pstmt = con.prepareStatement(strsql);
	    pstmt.setString(1, logID);
	    pstmt.setLong(2, uid);
	    pstmt.setString(3, s);
	    pstmt.setInt(4, 0);
	    pstmt.execute();
    }
    public ArrayList<Behavior> getBehaviors(long uid) throws SQLException {
    	String sql = "select * from log where uid="+uid+";";
    	ArrayList<Behavior> behaviors = new ArrayList<Behavior>();
        //	System.out.println(sql);
    	Statement stmt = con.createStatement();      
    	ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
        	behaviors.add(new Behavior(rs.getString("json")));
        }
    	return behaviors;
    }
    public void setflag(long uid) throws SQLException {
    	String sql = "update log set flag=1 where uid="+uid+";";
    	Statement stmt = con.createStatement();   
    	stmt.executeUpdate(sql);
    }
}
