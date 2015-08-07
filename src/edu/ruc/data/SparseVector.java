package edu.ruc.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class SparseVector {
	private int length;
	private List<Pair> arrayList;
	
    /**
     * Constructor for a SparseVector.
     */
    public SparseVector() {
    	length = 0;
    	arrayList = new ArrayList<Pair>();
    }

    /**
     * Get size.
     */
    public int size() {
    	return length;
    }
    
    /**
     * Add a pair to SparseVector.
     *
     * @param pair the object wanted to push back
     */    
    public void pushBack(Pair pair) {
    	arrayList.add(pair);
    	length++;
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
    	int index = arrayList.indexOf(key);
    	if (index != -1)
    		return arrayList.get(index).getValue();
    	else
    		return 0;
    }
    
    /**
     * modify a pair in SparseVector.
     *
     * @param pair the pair wanted to modify
     */    
    public void modifyPair(Pair pair) {
    	int index = arrayList.indexOf(pair.getKey());
    	if (index != -1)
    		arrayList.get(index).setValue(pair.getValue());
    	else
    		pushBack(pair);
    }
    
    /**
     * Remove a pair from SparseVector.
     *
     * @param key the key of the pair wanted to remove
     */    
    public void RemovePair(int key) {
    	arrayList.remove(key);
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
