package edu.ruc.database;

import java.util.ArrayList;

import edu.ruc.user.User;

public class UserDatabase {
	ArrayList<User> array=new ArrayList<>();
    public UserDatabase(){
    	array=new ArrayList<>();
    }
    public void setUser(User user){
    	array.add(user);
    	return ;
    }
    public User getUser(long uid){
    	for(User u:array){
    		if(u.getUid()==uid){
    			return u;
    		}
    	}
    	return new User(uid);
    }
    public void removeUser(long uid){
    	for(User u:array){
    		if(u.getUid()==uid){
    			array.remove(u);
    		}
    	}
    }
    
    public boolean isExistUser(long uid){
    	for(User u:array){
    		if(u.getUid()==uid){
    			
    			return true;
    		}
    	}
    	return false;
    }
}
