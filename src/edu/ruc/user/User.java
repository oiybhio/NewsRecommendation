package edu.ruc.user;

import edu.ruc.data.*;

import java.util.*;

public class User {
	private long uid;
	private int length;
	private List<Attribute> arrayList;
	
	/**
     * Constructor for a User.
     */
    public User(long uid) {
    	this.uid = uid;
    	length = 0;
    	arrayList = new ArrayList<Attribute>();
    }
    
    public User(long uid, int length, List<Attribute> arrayList) {
    	this.uid = uid;
    	this.length = length;
    	this.arrayList = arrayList;
    }
    
    /**
     * Get size.
     */
    public int size() {
    	return length;
    }
    
    /**
     * Get uid.
     */
    public long getUid() {
    	return uid;
    }
    
    /**
     * Add a attribute to User.
     *
     * @param attribute the object wanted to push back
     */    
    public void pushBack(Attribute attribute) {
    	arrayList.add(attribute);
    	length++;
    }

    /**
     * Get attribute list from User.
     */    
    public List<Attribute> getAttributeList() {
    	return arrayList;
    }
    
    /**
     * Get a attribute from User.
     *
     * @param i the index of the attribute wanted to get
     */    
    public Attribute getAttributeAt(int i) {
    	Attribute attribute = (Attribute) arrayList.get(i);
		return attribute;
    }
    
    /**
     * Find a attribute from User.
     *
     * @param attributeName the name of the attribute wanted to find
     */    
    public Attribute findAttribute(String attributeName) {
    	for(Attribute attribute:arrayList) {
    		if (attribute.getAttributeName() == attributeName)
    			return attribute;
    	}
    	return null;
    }
    
    /**
     * modify a attribute in User.
     *
     * @param attribute the attribute wanted to modify
     */    
    public void modifyAttribute(Attribute attribute) {
    	for(Attribute a:arrayList) {
    		if (a.getAttributeName() == attribute.getAttributeName()) {
    			a = attribute;
    			return;
    		}
    	}
    	pushBack(attribute);
    }

    /**
     * Update User
     */    
    public void Update(List<Attribute> newsattributes, double weight) {
    	
    }
    
    /**
     * Remove a attribute from User.
     *
     * @param attributeName the name of the attribute wanted to remove
     */    
    public void removeAttribute(String attributeName) {
    	for(Attribute attribute:arrayList) {
    		if (attribute.getAttributeName() == attributeName) {
    			arrayList.remove(attribute);
    			return;
    		}
    	}
    }
    /**
    * Output the elements into screen.
    */
    public void display() {
	System.out.println("userID : "+uid);
	for(int i=0;i<length;i++) {
		getAttributeAt(i).display();
	}
    }
}
