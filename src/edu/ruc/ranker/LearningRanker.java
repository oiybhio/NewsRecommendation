/**
 * Didn't finish.
 */

package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class LearningRanker {
	private List<News> sortedNewsList;
	
	public LearningRanker() {
		sortedNewsList = new ArrayList<News>();
	}
	
	public List<News> query(User user, List<News> newsList) {
		sortedNewsList.addAll(newsList);
		return sortedNewsList; // Didn't finish.
	}
}
