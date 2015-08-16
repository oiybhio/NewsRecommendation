package edu.ruc.database;

import edu.ruc.user.User;

public class UserDatabase {
    public UserDatabase(){
    	
    }
    public void setUser(User user){
    	return ;
    }
    public User getUser(long uid){
    	return new User(uid);
    }
    public void removeUser(long uid){
    	
    }
    
    public boolean isExistUser(long uid){
    	return true;
    }
}
