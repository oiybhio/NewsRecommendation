package edu.ruc.ranker;

import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;
import java.text.*;

public class TimeRanker {
	private List<News> sortedNewsList;
	
	public TimeRanker() {
		sortedNewsList = new ArrayList<News>();
	}
	
	public List<News> query(User user, List<News> newsList) {
		sortedNewsList.addAll(newsList);
		Collections.sort(sortedNewsList, new NewsDateComparator());
		return sortedNewsList;
	}
}

class NewsDateComparator implements Comparator<Object> { 
    public int compare(Object o1, Object o2) {
    	News news1 = (News) o1;
    	News news2 = (News) o2;
    	String dateString1 = news1.getDate();
    	String dateString2 = news2.getDate();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");    	     
    	Date date1 = null;
		try {
			date1 = sdf.parse(dateString1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    	Date date2 = null;
		try {
			date2 = sdf.parse(dateString2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return -date1.compareTo(date2);
    }
    
}
