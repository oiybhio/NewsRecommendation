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
	}
}

class NewsPopularityComparator implements Comparator { 
    public int compare(News one, News another) {
    	if (one.popularity == another.popularity)
    		return 0;
    	else if (one.popularity > another.popularity)
    		return -1;
    	else
    		return 1;
    }
}
