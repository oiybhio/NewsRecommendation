package edu.ruc.ranker;

import java.util.ArrayList;
import java.util.List;

import edu.ruc.data.Alphabet;
import edu.ruc.data.DenseVector;
import edu.ruc.data.Dictionary;
import edu.ruc.data.Pair;
import edu.ruc.data.SparseVector;
import weka.classifiers.functions.LinearRegression;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class LRegression {
	private LinearRegression lr;
	private List<String> attributeNames;
	private List<Integer> alphabetSizeSum;
	private int attributeSum;
	
	public LRegression(Dictionary dict, Alphabet attributeSet) {
		lr = null;
		attributeNames = attributeSet.getSymbols();
		alphabetSizeSum = new ArrayList<Integer>();
		int len = attributeNames.size();
		attributeSum = 0;
		for (int i = 0; i < len; i++) {
			alphabetSizeSum.add(attributeSum + dict.getAlphabetAt(i).size());
			attributeSum += dict.getAlphabetAt(i).size();
		}
	}
	
	public void training(List<Sample> samples) throws Exception {
		Instances trainingData = loadInstances(samples);
		lr = new LinearRegression();
        /*String options[]=new String[4];
        options[0]="-R";
        options[1]="1E-5";
        options[2]="-M";
        options[3]="10";
        logic.setOptions(options);*/
		lr.buildClassifier(trainingData);
	}
	
	public void classify(List<Sample> samples) throws Exception {
		Instances testData = loadInstances(samples);
		int n = testData.numInstances();
		for (int i = 0; i < n; i++) {
			System.out.print(samples.get(i).classNum + " ");
			System.out.println(lr.classifyInstance(testData.instance(i)));
			//samples.get(i).classNum = new Integer((int)lr.classifyInstance(testData.instance(i)));
			//System.out.println(samples.get(i).classNum);
		}
	}
	
	public Instances loadInstances(List<Sample> samples) {
	    FastVector atts = new FastVector();
	    for (int i = 0; i < attributeSum; i++)
	    	atts.addElement(new weka.core.Attribute("att" + i));
	    atts.addElement(new weka.core.Attribute("class"));
	    
	    Instances data = new Instances("UserNewsRelation", atts, 0);
	    
	    for(Sample sample:samples) {
	    	double[] vals = new double[data.numAttributes()];
	    	calculateFeature(sample,vals);
	    	if (sample.classNum != null)
	    		vals[attributeSum] = sample.classNum;
	    	data.add(new Instance(1.0, vals));
	    }
	    
	    data.setClassIndex(data.numAttributes() - 1);
	    
	    //System.out.println(data);
	    
	    return data;
	}
	
	public void calculateFeature(Sample sample, double[] vals) {
		int len = attributeNames.size();
		for (int i = 0; i < len; i++) {
			String attributeName = attributeNames.get(i);
			edu.ruc.data.Attribute attribute1 = sample.user.findAttribute(attributeName);
			edu.ruc.data.Attribute attribute2 = sample.news.getAttribute(attributeName);
			if (attribute1 == null || attribute2 == null) continue;
			int startLabel;
			if (i == 0)
				startLabel = 0;
			else
				startLabel = alphabetSizeSum.get(i - 1);
			switch(attribute1.getVectorType()) {
			case SPARSE:
				calculate(attribute1.getSparseVector(), attribute2.getSparseVector(), vals, startLabel);
				break;
			case DENSE:
				calculate(attribute1.getDenseVector(), attribute2.getDenseVector(), vals, startLabel);
			}
		}
	}
	
	public void calculate(SparseVector v1, SparseVector v2, double[] vals, int startLabel) {
		v1.sortKey();
		v2.sortKey();
		int len1 = v1.size();
		int len2 = v2.size();
		int i = 0, j = 0;
		while (i < len1 && j < len2) {
			Pair pair1 = v1.getPairAt(i);
			Pair pair2 = v2.getPairAt(j);
			if (pair1.getKey() == pair2.getKey()) {
				//System.out.println(pair2.getKey());
				vals[pair1.getKey() + startLabel] = pair1.getValue() * pair2.getValue();
				i++; j++;
			}
			else if (pair1.getKey() < pair2.getKey()) {
				i++;
			} else {
				j++;
			}
		}
    	//for(i=0;i<vals.length;i++)
    	//	System.out.print(vals[i] + " ");
    	//System.out.println();
	}

	public void calculate(DenseVector v1, DenseVector v2, double[] vals, int startLabel) {
		int len1 = v1.size();
		int len2 = v2.size();
		int len = Math.min(len1, len2);
		for (int i = 0; i < len; i++) {
			vals[i + startLabel] = v1.getValue(i) * v2.getValue(i);
		}
	}
	
}
