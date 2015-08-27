package edu.ruc.ranker;

import edu.ruc.news.News;
import edu.ruc.user.User;

public class Sample {
	public User user;
	public News news;
	public Integer classNum;
	
	public Sample(User user, News news, int classNum) {
		this.user = user;
		this.news = news;
		this.classNum = new Integer(classNum);
	}
	
	public Sample(User user, News news) {
		this.user = user;
		this.news = news;
		this.classNum = null;
	}
	
}
