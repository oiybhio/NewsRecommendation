package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class TimeRanker {
	private List<News> sortedNewsList;
	
	public VSMRanker() {
		sortedNewsList = new ArrayList<News>();
	}
	
	public List<News> query(User user, List<News> newsList) {
		sortedNewsList.addAll(newsList);
		Collections.sort(sortedNewsList, new NewsDateComparator());
	}
}

class NewsDateComparator implements Comparator { 
    public int compare(News one, News another) {
    	return -one.date.compareTo(another.date);
    }
}
