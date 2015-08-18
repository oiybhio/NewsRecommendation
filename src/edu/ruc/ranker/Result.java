package edu.ruc.ranker;

import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class Result {
	private User user;
	private String category;
	private RankerType rankerType;
	private List<News> arrayList;
	//private Date date;
	
	public Result(User user, String category, RankerType rankerType, List<News> arrayList) {
		this.user = user;
		this.category = category;
		this.rankerType = rankerType;
		this.arrayList = arrayList;
		//this.date = new Date();
	}
	
	public User getUser() {
		return user;
	}
	
	public String getCategory() {
		return category;
	}
	
	public RankerType getRankerType() {
		return rankerType;
	}	
	
	public List<News> getNewsList() {
		return arrayList;
	}
	
	public boolean validationChecking() {
		return true; // Didn't finish.
	}
	
}
