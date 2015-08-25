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
		List<Double> range = new ArrayList<Double>();
		
		News news0 = newsList.get(0);
		Attribute attribute0 = news0.getAttribute("hotness");
		DenseVector denseVector0 = attribute0.getDenseVector();
		int len0 = denseVector0.size();
		for(int i = 0; i < len0; i++)
			range.add(new Double(denseVector0.getValue(i)));
		
		int n = newsList.size();
		for(int i = 1; i < n; i++) {
			News news = newsList.get(i);
			Attribute attribute = news.getAttribute("hotness");
			DenseVector denseVector = attribute.getDenseVector();
			int len = denseVector.size();
			for(int j = 0; j < len; j++) {
				Double x = range.get(j);
				Double y = denseVector.getValue(j);
				if (y > x)
					x = new Double(y);
			}
		}
		
		sortedNewsList.addAll(newsList);
		Collections.sort(sortedNewsList, new NewsPopularityComparator(range));
		return sortedNewsList;
	}
}

class NewsPopularityComparator implements Comparator<Object> {
	private List<Double> range;
	
	public NewsPopularityComparator(List<Double> range) {
		this.range = range;
	}
	
    public int compare(Object o1, Object o2) {
    	News news1 = (News) o1;
    	News news2 = (News) o2;
    	Attribute attribute1 = news1.getAttribute("hotness");
    	Attribute attribute2 = news2.getAttribute("hotness");
    	
    	double sum1 = 0;
    	double sum2 = 0;
    	
    	int len = range.size();
    	for(int i = 0; i < len; i++) {
    		sum1 += attribute1.getFeature(i) / range.get(i);
    		sum2 += attribute2.getFeature(i) / range.get(i);
    	}
    	
    	if (sum1 == sum2)
    		return 0;
    	else if (sum1 > sum2)
    		return -1;
    	else
    		return 1;
    }
    
}
