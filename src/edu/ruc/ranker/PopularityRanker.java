package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class PopularityRanker {
	private List<News> sortedNewsList;
	
	public PopularityRanker() {
		sortedNewsList = new ArrayList<News>();
	}
	
	public List<News> query(User user, List<News> newsList) {
		sortedNewsList.addAll(newsList);
		Collections.sort(sortedNewsList, new NewsPopularityComparator());
		return sortedNewsList;
	}
}

class NewsPopularityComparator implements Comparator<Object> { 
    public int compare(Object o1, Object o2) {
    	News news1 = (News) o1;
    	News news2 = (News) o2;
    	Attribute attribute1 = news1.getAttribute("Hotness");
    	Attribute attribute2 = news2.getAttribute("Hotness");
    	double hotness1 = attribute1.getFeature(0);
    	double hotness2 = attribute2.getFeature(0);
    	if (hotness1 == hotness2)
    		return 0;
    	else if (hotness1 > hotness2)
    		return -1;
    	else
    		return 1;
    }
}
