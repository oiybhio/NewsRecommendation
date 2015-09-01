package edu.ruc.ranker;

import edu.ruc.news.*;

import java.util.*;

public class RandomMerge {
	private double [] p;
	
	public RandomMerge(double p1, double p2, double p3) {
		p = new double [] {p1, p1 + p2, p1 + p2 + p3};
	}
	public RandomMerge() {
		p = new double [] {1.0/3, 2.0/3, 1.0};
	}
	
	public List<News> merge(List<List<News>> newsList, List<Long> readList) {
		Random rand = new Random();
		List<News> ret = new ArrayList<News>();
		Set<Long> readSet = new HashSet<Long>();
		for(Long nid:readList)
			readSet.add(nid);
		int [] i = new int [] {0, 0, 0};
		int [] len = new int [3];
		for(int j = 0; j < 3; j++)
			len[j] = newsList.get(j).size();
		while(i[0] < len[0] || i[1] < len[1] || i[2] < len[2]) {
			double randDouble = rand.nextDouble();
			for (int j = 0; j < 3; j++) {
				if (randDouble <= p[j]) {
					while (i[j] < len[j] && readSet.contains(newsList.get(j).get(i[j]).getID()))
						i[j]++;
					if (i[j] < len[j]) {
						ret.add(newsList.get(j).get(i[j]));
						readSet.add(newsList.get(j).get(i[j]).getID());
					}
					continue;
				}
			}
		}
		return ret;
	}
}
