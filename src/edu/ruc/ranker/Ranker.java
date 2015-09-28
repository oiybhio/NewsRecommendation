package edu.ruc.ranker;

import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class Ranker {

	public NewsList query(ResultStore resultStore, User user, String category, List<News> candidateList, int topK) {
		
		if (candidateList.isEmpty()) return null;
		
		List<List<News>> newsList = new ArrayList<List<News>>();
		
		Result result = resultStore.find(user, category, RankerType.VSM);
		if (result == null) {
			VSMRanker vsmRanker = new VSMRanker();
			newsList.add(vsmRanker.query(user, candidateList));
			resultStore.add(new Result(user, category, RankerType.VSM, newsList.get(0)));
		} else {
			newsList.add(result.getNewsList());
		}
		
		result = resultStore.find(user, category, RankerType.POPULARITY);
		if (result == null) {
			PopularityRanker popularityRanker = new PopularityRanker();
			newsList.add(popularityRanker.query(user, candidateList));
			resultStore.add(new Result(user, category, RankerType.POPULARITY, newsList.get(1)));
		} else {
			newsList.add(result.getNewsList());
		}
		
		result = resultStore.find(user, category, RankerType.TIME);
		if (result == null) {
			TimeRanker timeRanker = new TimeRanker();
			newsList.add(timeRanker.query(user, candidateList));
			resultStore.add(new Result(user, category, RankerType.TIME, newsList.get(2)));
		} else {
			newsList.add(result.getNewsList());
		}
		
		List<News> newsListMerged;
		result = resultStore.find(user, category, RankerType.ALL);
		if (result == null) {
			RandomMerge randomMerge = new RandomMerge();
			newsListMerged = randomMerge.merge(newsList, user.getReaded());
			resultStore.add(new Result(user, category, RankerType.ALL, newsListMerged));
		} else {
			newsListMerged = result.getNewsList();
		}
		
		NewsList ret = new NewsList();
		if (newsListMerged.size() < topK)
			topK = newsListMerged.size();
		for(int i = 0; i < topK; i++)
			ret.addNews(newsListMerged.get(i));
		
		return ret;
	}
	
}
