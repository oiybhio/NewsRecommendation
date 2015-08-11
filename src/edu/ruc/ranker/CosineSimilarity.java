package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

public class CosineSimilarity implements Similarity {
	private double sum1, sum2, sum3;
	
	public void calculate(SparseVector v1, SparseVector v2, double w) {
		int len1 = v1.size();
		int len2 = v2.size();
		int i = 0, j = 0;
		while (i < len1 && j < len2) {
			Pair pair1 = v1.getPairAt(i);
			Pair pair2 = v2.getPairAt(j);
			if (pair1.getKey() == pair2.getKey()) {
				sum1 += w * pair1.getValue() * pair2.getValue();
				sum2 += w * pair1.getValue() * pair1.getValue();
				sum3 += w * pair2.getValue() * pair2.getValue();
			}
			else if (pair1.getKey() < pair2.getKey()) {
				sum2 += w * pair1.getValue() * pair1.getValue();
				i += 1;
			} else {
				sum3 += w * pair2.getValue() * pair2.getValue();
				j += 1;
			}
		}
		while (i < len1) {
			Pair pair1 = v1.getPairAt(i);
			sum2 += w * pair1.getValue() * pair1.getValue();
			i += 1;
		}
		while (j < len2) {
			Pair pair2 = v2.getPairAt(j);
			sum3 += w * pair2.getValue() * pair2.getValue();
			j += 1;
		}
	}
	
	public double getSimilarity(User user, News news, Weight weight) {
		sum1 = sum2 = sum3 = 0;
		for (int i=0; i<weight.length; i+=1) {
			String attributeName = weight.attributeNameList.get(i);
			double w = weight.weightList.get(i);
			Attribute attribute1 = user.findAttribute(attributeName);
			Attribute attribute2 = news.findAttribute(attributeName);
			calculate(attribute1.getSparseVector(), attribute2.getSparseVector(), w);
		}
		return (sum1 / Math.sqrt(sum2 * sum3));
	}
	
}
