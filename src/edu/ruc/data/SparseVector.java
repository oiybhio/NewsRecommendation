package edu.ruc.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class SparseVector {
	private ArrayList<Pair> arrayList;
	
    /**
     * Constructor for a SparseVector.
     */
    public SparseVector() {
    	arrayList = new ArrayList<Pair>();
    }
    
    /**
     * Constructor for a SparseVector.
     * 
     * @param str Vector
     */
    public SparseVector(String str) {
    	arrayList = new ArrayList<Pair>();
		StringTokenizer st = new StringTokenizer(str," ");
		while(st.hasMoreTokens()){
			String token=st.nextToken();
			if (token.length() < 3) continue;
			StringTokenizer stPart = new StringTokenizer(token,":");
			pushBack(Pair.create(Integer.parseInt(stPart.nextToken()), Double.parseDouble(stPart.nextToken())));
		}
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
     * Modify a pair in SparseVector.
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
     * Sort all the pairs of SparseVector by key.
     */
	public void sortKey() {
        Collections.sort(arrayList,new Comparator<Pair>(){  
            public int compare(Pair pair0, Pair pair1) {  
                return pair0.getKey().compareTo(pair1.getKey());  
            }  
        });
    }
	
	/**
	 * Sort all the pairs of SparseVector by value.
	 */
	public void sortValue() {
        Collections.sort(arrayList,new Comparator<Pair>(){  
            public int compare(Pair pair0, Pair pair1) {  
                return pair1.getValue().compareTo(pair0.getValue());  
            }
        });		
	}
	
	/**
	 * Get top k
	 * 
	 * @param k
	 */
	public void getTopK(int k) {
		sortValue();
	   	ArrayList<Pair> temp = arrayList;
	   	clear();
	   	int count = 0;
	   	for (Pair pair:temp) {
	   		if(++count > k) break;
	   		arrayList.add(pair);
	   	}
	}
	
	/**
	 * Clear
	 */
	public void clear() {
		arrayList = new ArrayList<Pair>();
	}
	
	/**
	 * Vector to String.
	 * 
	 * @return String
	 */
	public String vectorToString() {
		String ret = new String();
		for(Pair pair:arrayList) {
			ret += pair.pairToString() + " ";
        }
		return ret;
	}
	
	/**
	 * Output the elements into screen.
	 */
	public void display() {
		System.out.print("{");
		for(Pair pair:arrayList) {
			pair.display();
			System.out.print(" ");
        }
		System.out.println("}");
	}
	
}
