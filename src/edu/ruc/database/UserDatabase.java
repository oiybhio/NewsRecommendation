package edu.ruc.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.ruc.data.Alphabet;
import edu.ruc.data.Attribute;
import edu.ruc.data.Dictionary;
import edu.ruc.data.VectorType;
import edu.ruc.news.News;
import edu.ruc.user.OnlineUsers;
import edu.ruc.user.User;

public class UserDatabase {
	private Connection con;
    public void setConnection(Connection con){
		  this.con=con;
	  }
    public void saveVector(User u, String table) throws SQLException{
    	String sql = "select * from "+table+" where uid="+u.getUid()+";";
    //	System.out.println(sql);
    	Statement stmt = con.createStatement();      
    	ResultSet rs = stmt.executeQuery(sql);
    	if(!rs.next()){
		    String strsql = "insert into "+ table +" (uid,attribute_name,vector,type,readed)" + " values(?,?,?,?,?)";
		    for(Attribute attribute:u.getAttributeList()) {
		    	PreparedStatement pstmt = con.prepareStatement(strsql);
			    pstmt.setLong(1, u.getUid());
			    pstmt.setString(2, attribute.getAttributeName());
			    pstmt.setString(3, attribute.vectorToString());
			    pstmt.setInt(4, 0);
			    pstmt.setString(5,u.ReadToString());
			    pstmt.execute();
		    }
    	} else{
    		sql = "delete from "+table+" where uid="+u.getUid()+";";
    		PreparedStatement p = con.prepareStatement(sql);
    		p.executeUpdate();
    		String strsql = "insert into "+ table +" (uid,attribute_name,vector,type,readed)" + " values(?,?,?,?,?)";
		    for(Attribute attribute:u.getAttributeList()) {
		    	PreparedStatement pstmt = con.prepareStatement(strsql);
			    pstmt.setLong(1, u.getUid());
			    pstmt.setString(2, attribute.getAttributeName());
			    pstmt.setString(3, attribute.vectorToString());
			    pstmt.setInt(4, 0);
			    pstmt.setString(5,u.ReadToString());
			    pstmt.execute();
		    }
	    }
    }
    public void saveVectorALL(OnlineUsers users, String table) throws SQLException{
    	for(int i=0;i< users.size();i++) {
    		User u = users.getUserAt(i);
    		saveVector(u, table);
    	}
    }
    public User getVector(long uid, Dictionary dict, Alphabet attributeSet,String table) throws SQLException {
    	String sql;
    	Statement stmt;
    	ResultSet rs;
    	/*String sql = "select * from userInfo where uid="+uid+";";
    	System.out.println(sql);
    	Statement stmt = con.createStatement();      
    	ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
    	if(!rs.next())
    		return null;*/
    	
    	User u = new User(uid);    	
    	sql = "select * from "+ table +"  where uid="+uid+" limit 1;";
    //	System.out.println(sql);
    	stmt = con.createStatement();      
    	rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
    	int count=0;
    	while(rs.next()){
    		Attribute a=new Attribute(VectorType.SPARSE, dict, attributeSet, rs.getString("attribute_name"),rs.getString("vector"));
    		u.modifyAttribute(a);
    		u.StringToRead(rs.getString("readed"));
    		count++;
    	}
    	if(count==0)
    		return null;
    	return u;
    }
}
