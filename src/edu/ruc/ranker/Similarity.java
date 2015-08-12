package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;
import edu.ruc.news.*;

public interface Similarity {
	public void calculate(SparseVector v1, SparseVector v2, double w);
	public void calculate(DenseVector v1, DenseVector v2, double w);
	public double getSimilarity(User user, News news, Weight weight);
}
