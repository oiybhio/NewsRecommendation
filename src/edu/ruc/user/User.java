package edu.ruc.user;

import edu.ruc.data.*;

import java.util.*;

public class User {
	private long uid;
	private int length;
	private List<Attribute> arrayList;
	private List<Long> Read;
	
	/**
     * Constructor for a User.
     */
    public User(long uid) {
    	this.uid = uid;
    	length = 0;
    	arrayList = new ArrayList<Attribute>();
    	Read = new ArrayList<Long>();
    }
    
    public User(long uid, int length, List<Attribute> arrayList) {
    	this.uid = uid;
    	this.length = length;
    	this.arrayList = arrayList;
    	Read = new ArrayList<Long>();
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
     * Get Readed.
     */
     
    public List<Long> getReaded() {
    	return Read;
    }
    public String ReadToString() {
    	String s = "";
    	for(int i=0;i<Read.size();i++) {
    		s = s + Long.toString(Read.get(i))+ " ";
    	}
    	return s;
    }
    public void StringToRead(String s) {
    	if (s==null||s.equals(""))
    		return;
    	String[] str = s.split(" ");
    	for(int i=0;i<str.length;i++) {
    		Read.add(Long.parseLong(str[i]));
    	};
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
     * check is a attribute in User.
     */  
  	public boolean isExistAttributeByName(String attribute_name){
  		for(Attribute a:arrayList){
  			if(a. getAttributeName().equals(attribute_name)){		
  				return true;
  			}
  		}
  		return false;
  	}

    /**
     * Update User
     */    
    public void Update(List<Attribute> newsattributes, long nid,double weight ) {
    	Read.add(nid);
		for(int i=0;i<newsattributes.size();i++) {
			if(isExistAttributeByName(newsattributes.get(i).getAttributeName())) {
				merge(newsattributes.get(i));
			}else{
				length++;
				arrayList.add(newsattributes.get(i));
			}
		}
    }
    /**
     * Get News weight to User
     */
	public int getWeight(long nid) {
		return 1;
	}
	/**
     * merge a attribute from User.
     *
     * @param attributeName the name of the attribute wanted to remove
     */    
    public void merge(Attribute a) {
    	for(Attribute attribute:arrayList) {
    		if (attribute.getAttributeName() == a.getAttributeName()) {
    		//	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^");
    		//	attribute.display();
    		//	System.out.println("***********************");
    		//	a.display();
    		//	System.out.println("^^^^^^^^^^^^^^^^^^^^^^^");
    			attribute.getSparseVector().sortKey();
    			int i=0,j=0;
    			while(i<attribute.getSparseVector().size()&&j<a.getSparseVector().size()) {
    			//	System.out.println(attribute.getSparseVector().getPairAt(i).getKey()+"  "+a.getSparseVector().getPairAt(j).getKey());
    				if(attribute.getSparseVector().getPairAt(i).getKey()==a.getSparseVector().getPairAt(j).getKey()) {
    					double t = attribute.getSparseVector().getPairAt(i).getValue()+a.getSparseVector().getPairAt(j).getValue();
    					attribute.getSparseVector().getPairAt(i).setValue(t);
    					i++; j++;
    				}
    				else if(attribute.getSparseVector().getPairAt(i).getKey()<a.getSparseVector().getPairAt(j).getKey())
    					i++;
    				else if(attribute.getSparseVector().getPairAt(i).getKey()>a.getSparseVector().getPairAt(j).getKey()) {
    					attribute.addFeature(a.getSparseVector().getPairAt(j).getKey(), a.getSparseVector().getPairAt(j).getValue());
    					j++;
    				}
    			}
    			if(j<a.getSparseVector().size()) {
    				for(int k=j;k<a.getSparseVector().size();k++) {
    					attribute.addFeature(a.getSparseVector().getPairAt(j).getKey(), a.getSparseVector().getPairAt(j).getValue());
    				}
    			}
    			return;
    		}
    	}
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
	System.out.println("read news : "+ReadToString());
	for(int i=0;i<length;i++) {
		getAttributeAt(i).display();
	}
    }
}
