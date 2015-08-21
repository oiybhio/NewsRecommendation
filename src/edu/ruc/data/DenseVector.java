package edu.ruc.data;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DenseVector {
	private ArrayList<Double> arrayList;
	
    /**
     * Constructor for a DenseVector.
     */
    public DenseVector() {
    	arrayList = new ArrayList<Double>();
    }
    
    /**
     * Constructor for a DenseVector.
     * 
     * @param arrayList Vector
     */
    public DenseVector(ArrayList<Double> arrayList) {
    	this.arrayList = new ArrayList<Double>(arrayList);
    }
    
    /**
     * Constructor for a DenseVector.
     * 
     * @param str Vector
     */
    public DenseVector(String str) {
    	arrayList = new ArrayList<Double>();
		StringTokenizer st = new StringTokenizer(str," ");
		while(st.hasMoreTokens()){
			String token=st.nextToken();
			if (token.length() < 3) continue;
			StringTokenizer stPart = new StringTokenizer(token,":");
			stPart.nextToken();
			pushBack(Double.parseDouble(stPart.nextToken()));
		}
    }

    /**
     * Get size.
     */
    public int size() {
    	return arrayList.size();
    }
    
    /**
     * Add a value to DenseVector.
     *
     * @param w the value wanted to push back
     */    
    public void pushBack(double w) {
    	arrayList.add(w);
    }
    
    /**
     * Add all value to DenseVector.
     * 
     * @param arrayList the DoubleList wanted to push back
     */
    public void addAll(ArrayList<Double> arrayList) {
    	this.arrayList.addAll(arrayList);
    }
    
    /**
     * Get a value from DenseVector.
     *
     * @param i the index of the value wanted to get
     */    
    public double getValue(int i) {
    	return arrayList.get(i);
    }
    
    /**
     * modify a value in DenseVector.
     *
     * @param i the index wanted to modify
     * @param w the value wanted to modify
     */    
    public void modify(int i, double w) {
    	if (i < arrayList.size()) {
    		arrayList.set(i, w);
    	}
    }

	/**
	 * Vector to String.
	 * 
	 * @return String
	 */
	public String vectorToString() {
		String ret = new String();
		for (int i = 0; i < arrayList.size(); i += 1) {
			ret += String.valueOf(i) + ":" + String.valueOf(arrayList.get(i)) + " ";
        }
		return ret;
	}    
    
	/**
	 * Output the elements into screen.
	 */
	public void display() {
		System.out.print("{");
		for (int i = 0; i < arrayList.size(); i += 1)
			System.out.print(i + ":" + arrayList.get(i) + " ");
		System.out.println("}");
	}
	
}
