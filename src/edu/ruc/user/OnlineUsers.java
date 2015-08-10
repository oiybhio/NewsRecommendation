package edu.ruc.user;

import java.util.*;

public class OnlineUsers {
	private int length;
	private List<User> arrayList;
	
	/**
     * Constructor for a OnlineUsers.
     */
    public OnlineUsers() {
    	length = 0;
    	arrayList = new ArrayList<User>();
    }
    
    /**
     * Add a User to OnlineUsers.
     *
     * @param user the object wanted to push back
     */    
    public void pushBack(User user) {
    	arrayList.add(user);
    	length++;
    }
    
    /**
     * Get size.
     */
    public int size() {
    	return length;
    }
    
    /**
     * Get a user from OnlineUsers.
     *
     * @param i the index of the user wanted to get
     */    
    public User getUserAt(int i) {
    	User user = (User) arrayList.get(i);
		return user;
    }
    
    /**
     * Find a user from OnlineUsers.
     *
     * @param uid the uid of the user wanted to find
     */    
    public User findUser(long uid) {
    	for(User user:arrayList) {
    		if (user.getUid() == uid)
    			return user;
    	}
    	return null;
    }
    
    /**
     * modify a user in OnlineUsers.
     *
     * @param user the user wanted to modify
     */    
    public void modifyUser(User userModify) {
    	for(User user:arrayList) {
    		if (user.getUid() == userModify.getUid()) {
    			user = userModify;
    			return;
    		}
    	}
    	pushBack(userModify);
    }
    
    /**
     * Remove a user from OnlineUsers.
     *
     * @param uid the uid of the user wanted to remove
     */    
    public void removeUser(long uid) {
    	for(User user:arrayList) {
    		if (user.getUid() == uid) {
    			arrayList.remove(user);
    			return;
    		}
    	}
    }
    
}
