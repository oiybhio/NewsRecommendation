package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class VSMRanker {
	private List<Score> scoreList;
	private List<News> sortedNewsList;
	
	public VSMRanker() {
		scoreList = new ArrayList<Score>();
		sortedNewsList = new ArrayList<News>();
	}
	
	public List<News> query(User user, List<News> newsList) {
		Weight weight = new Weight(user);
		CosineSimilarity cosineSimilarity = new CosineSimilarity();
		int i = 0;
		for (News news:newsList) {
			double similarity = cosineSimilarity.getSimilarity(user, news, weight);
			scoreList.add(new Score(similarity, i));
			i += 1;
		}
		Collections.sort(scoreList);
		for (Score score:scoreList) {
			sortedNewsList.add(newsList.get(score.position));
		}
		return sortedNewsList;
	}
	
}
