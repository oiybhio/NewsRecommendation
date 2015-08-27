package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

public class CosineSimilarity implements Similarity {
	private double sum1, sum2, sum3;
	
	public void calculate(SparseVector v1, SparseVector v2, double w) {
		v1.sortKey();
		v2.sortKey();
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
				i++; j++;
			}
			else if (pair1.getKey() < pair2.getKey()) {
				sum2 += w * pair1.getValue() * pair1.getValue();
				i++;
			} else {
				sum3 += w * pair2.getValue() * pair2.getValue();
				j++;
			}
		}
		while (i < len1) {
			Pair pair1 = v1.getPairAt(i);
			sum2 += w * pair1.getValue() * pair1.getValue();
			i++;
		}
		while (j < len2) {
			Pair pair2 = v2.getPairAt(j);
			sum3 += w * pair2.getValue() * pair2.getValue();
			j++;
		}
	}
	
	public void calculate(DenseVector v1, DenseVector v2, double w) {
		int len1 = v1.size();
		int len2 = v2.size();
		int len = Math.min(len1, len2);
		for (int i = 0; i < len; i++) {
			sum1 += w * v1.getValue(i) * v2.getValue(i);
			sum2 += w * v1.getValue(i) * v1.getValue(i);
			sum3 += w * v2.getValue(i) * v2.getValue(i);
		}
	}
	
	public double getSimilarity(User user, News news, Weight weight) {
		sum1 = sum2 = sum3 = 0;
		for (int i = 0; i < weight.length; i++) {
			String attributeName = weight.attributeNameList.get(i);
			double w = weight.weightList.get(i);
			Attribute attribute1 = user.findAttribute(attributeName);
			Attribute attribute2 = news.getAttribute(attributeName);
			if (attribute1 == null || attribute2 == null) continue;
			switch(attribute1.getVectorType()) {
			case SPARSE:
				calculate(attribute1.getSparseVector(), attribute2.getSparseVector(), w);
				break;
			case DENSE:
				calculate(attribute1.getDenseVector(), attribute2.getDenseVector(), w);
			}
			
		}
		return (sum1 / Math.sqrt(sum2 * sum3));
	}
	
}
