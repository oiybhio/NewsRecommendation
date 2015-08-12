package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class Ranker {

	public List<News> query(User user, List<News> candidateList) {
		VSMRanker vsmRanker = new VSMRanker();
		List<News> newsList0 = vsmRanker.query(user, candidateList);
		
		PopularityRanker popularityRanker = new PopularityRanker();
		List<News> newsList1 = popularityRanker.query(user, candidateList);
		
		TimeRanker timeRanker = new TimeRanker();
		List<News> newsList2 = timeRanker.query(user, candidateList);
		
		//LearningRanker learningRanker = new LearningRanker();
		//List<News> newsList3 = learningRanker.query(user, candidateList);		
		
		//RandomMerge randomMerge = new RandomMerge(0.25, 0.25, 0.25, 0.25);
		//newsList = randomMerge.merge(newsList0, newsList1, newsList2, newsList3);
	}
	
}
