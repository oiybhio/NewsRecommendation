package edu.ruc.ranker;

import java.util.ArrayList;

public class Ranker {
	
	/**Time Ranker. Sort candidate list by time.
	 * 
	 * @param user
	 * @param candidateList news list
	 * @return news list sorted by time
	 */
	public ArrayList<News> timeRanker(User user, ArrayList<News> candidateList) {
		ArrayList<News> ret = timeScore(user, candidateList);
		return ret;
	}
	
	/**Calculate news score by time.
	 * 
	 * @param user
	 * @param candidateList news list
	 * @return news list sorted by time
	 */
	public ArrayList<News> timeScore(User user, ArrayList<News> candidateList) {
		// Calculate news score by time
	}
	
	/**Popularity Ranker. Sort candidate list by popularity.
	 * 
	 * @param user
	 * @param candidateList news list
	 */
	public ArrayList<News> popularityRanker(User user, ArrayList<News> candidateList) {
		ArrayList<News> ret = popularityScore(user, candidateList);
		return ret;
	}
	
	/**Calculate news score by popularity.
	 * 
	 * @param user
	 * @param candidateList news list
	 * @return news list sorted by popularity
	 */
	public ArrayList<News> popularityScore(User user, ArrayList<News> candidateList) {
		// Calculate news score by popularity
	}
	
	/**Distance Ranker. Sort candidate list by distance.
	 * 
	 * @param user
	 * @param candidateList news list
	 * @param attributeName attribute name list
	 * @param weight attribute weight list
	 * @return news list sorted by distance
	 */
	public ArrayList<News> distanceRanker(User user, ArrayList<News> candidateList, ArrayList<String> attributeName, ArrayList<Double> weight) {
		ArrayList<News> ret = distanceScore(user, candidateList);
		return ret;
	}
	
	/**Calculate news score by distance.
	 * 
	 * @param user
	 * @param candidateList news list
	 * @param attributeName attribute name list
	 * @param weight attribute weight list
	 * @return news list sorted by distance
	 */
	public ArrayList<News> distanceScore(User user, ArrayList<News> candidateList, ArrayList<String> attributeName, ArrayList<Double> weight) {
		// Calculate news score by distance
	}
	
	/**learning to rank Ranker. Sort candidate list by learning to rank.
	 * 
	 * @param user
	 * @param candidateList news list
	 */
	public ArrayList<News> learningToRankRanker(User user, ArrayList<News> candidateList) {
		ArrayList<News> ret = learningToRankScore(user, candidateList);
		return ret;
	}
	
	/**Calculate news score by learning to rank.
	 * 
	 * @param user
	 * @param candidateList news list
	 * @return news list sorted by learning to rank
	 */
	public ArrayList<News> learningToRankScore(User user, ArrayList<News> candidateList) {
		// Calculate news score by learning to rank
	}
}
