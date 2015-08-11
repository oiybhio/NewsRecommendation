/**
 * Didn't finish.
 */

package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

import java.util.*;

public class RandomMerge {
	private double [] p;
	
	public RandomMerge(double p1, double p2, double p3, double p4) {
		p = new double [] {p1, p2, p3, p4};
	}
	public RandomMerge() {
		p = new double [] {0.25, 0.25, 0.25, 0.25};
	}
	
	public List<News> merge(List<News> newsList1, List<News> newsList2, List<News> newsList3, List<News> newsList4) {
		// Didn't finish.
	}
}
