package edu.ruc.ranker;

import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class Ranker {

	public List<News> query(ResultStore resultStore, User user, String category, List<News> candidateList) {
		
		int top = 10;
		
		List<News> newsList0, newsList1, newsList2/*, newsList3*/; 
		
		Result result = resultStore.find(user, category, RankerType.VSM);
		if (result == null) {
			VSMRanker vsmRanker = new VSMRanker();
			newsList0 = vsmRanker.query(user, candidateList);
			resultStore.add(new Result(user, category, RankerType.VSM, newsList0));
		} else {
			newsList0 = result.getNewsList();
		}
		
		System.out.println("UserId:" + user.getUid() + " Category:" + category + " Ranker:VSM");
		int i = 0;
		for(News news:newsList0) {
			System.out.println(news.getTitle());
			i++;
			if (i >= top) break;
		}
		System.out.println();
		
		result = resultStore.find(user, category, RankerType.POPULARITY);
		if (result == null) {
			PopularityRanker popularityRanker = new PopularityRanker();
			newsList1 = popularityRanker.query(user, candidateList);
			resultStore.add(new Result(user, category, RankerType.POPULARITY, newsList1));
		} else {
			newsList1 = result.getNewsList();
		}

		System.out.println("UserId:" + user.getUid() + " Category:" + category + " Ranker:Hotness");
		i = 0;
		for(News news:newsList1) {
			System.out.println(news.getTitle());
			i++;
			if (i >= top) break;
		}
		System.out.println();		
		
		result = resultStore.find(user, category, RankerType.TIME);
		if (result == null) {
			TimeRanker timeRanker = new TimeRanker();
			newsList2 = timeRanker.query(user, candidateList);
			resultStore.add(new Result(user, category, RankerType.TIME, newsList2));
		} else {
			newsList2 = result.getNewsList();
		}

		System.out.println("UserId:" + user.getUid() + " Category:" + category + " Ranker:Time");
		i = 0;
		for(News news:newsList2) {
			System.out.println(news.getTitle());
			i++;
			if (i >= top) break;
		}
		System.out.println();		
	
		/*result = resultStore.find(user, category, RankerType.LEARNING);
		if (result == null) {
			LearningRanker learningRanker = new LearningRanker();
			newsList3 = learningRanker.query(user, candidateList);
			resultStore.add(new Result(user, category, RankerType.LEARNING, newsList3));
		} else {
			newsList3 = result.getNewsList();
		}*/
		
		//RandomMerge randomMerge = new RandomMerge(0.25, 0.25, 0.25, 0.25);
		//newsList = randomMerge.merge(newsList0, newsList1, newsList2, newsList3);
		
		return null; // Didn't finish.
	}
	
}
