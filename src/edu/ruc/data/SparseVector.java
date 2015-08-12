package edu.ruc.data;

import java.util.ArrayList;
import java.util.Collections;

public class SparseVector {
	private ArrayList<Pair> arrayList;
	
    /**
     * Constructor for a SparseVector.
     */
    public SparseVector() {
    	arrayList = new ArrayList<Pair>();
    }

    /**
     * Get size.
     */
    public int size() {
    	return arrayList.size();
    }
    
    /**
     * Add a pair to SparseVector.
     *
     * @param pair the object wanted to push back
     */    
    public void pushBack(Pair pair) {
    	arrayList.add(pair);
    }
    
    /**
     * Get a pair from SparseVector.
     *
     * @param i the index of the pair wanted to get
     */    
    public Pair getPairAt(int i) {
    	Pair pair = (Pair) arrayList.get(i);
		return pair;
    }
    
    /**
     * Find a pair from SparseVector.
     *
     * @param key the key of the pair wanted to find
     */    
    public double findValue(int key) {
    	for(Pair pair:arrayList) {
    		if (pair.getKey() == key)
    			return pair.getValue();
    	}
    	return 0;
    }
    
    /**
     * modify a pair in SparseVector.
     *
     * @param pair the pair wanted to modify
     */    
    public void modifyPair(Pair pairModify) {
    	for(Pair pair:arrayList) {
    		if (pair.getKey() == pairModify.getKey()) {
    			pair.setValue(pairModify.getValue());
    			return;
    		}
    	}
    	pushBack(pairModify);
    }
    
    /**
     * Remove a pair from SparseVector.
     *
     * @param key the key of the pair wanted to remove
     */    
    public void removePair(int key) {
    	for(Pair pair:arrayList) {
    		if (pair.getKey() == key) {
    			arrayList.remove(pair);
    			return;
    		}
    	}
    }
    
    /**
     * sort all the pairs of SparseVector.
     */
	public void sort() {
    	Collections.sort(arrayList);
    }
	
	/**
	 * Output the elements into screen.
	 */
	public void display() {
		System.out.println("{");
		for(Pair pair:arrayList) {
			pair.display();
			System.out.println(" ");
        }
		System.out.println("}");
	}
}
