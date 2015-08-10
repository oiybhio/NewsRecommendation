package edu.ruc.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.ruc.data.Pair;

public class SparseBehavior {
	private int length;
	private List<Behavior> arrayList;
	
    /**
     * Constructor for a SparseBehavior.
     */
    public SparseBehavior() {
    	length = 0;
    	arrayList = new ArrayList<Behavior>();
    }

    /**
     * Get size.
     */
    public int size() {
    	return length;
    }
    
    /**
     * Add a behavior to SparseBehavior.
     *
     * @param behavior the object wanted to push back
     */    
    public void pushBack(Behavior behavior) {
    	arrayList.add(behavior);
    	length++;
    }
    
    /**
     * Get a pair from SparseBehavior.
     *
     * @param i the index of the pair wanted to get
     */    
    public Behavior getBehaviorAt(int i) {
    	Behavior behavior = (Behavior) arrayList.get(i);
		return behavior;
    }
    
    /**
     * Find a pair from SparseBehavior.
     *
     * @param key the uid of the Behavior wanted to find
     */    
    public Behavior findValue(long uid) {
    	for(Behavior behavior:arrayList) {
    		if (behavior.getUid() == uid)
    			return behavior;
    	}
    	return null;
    }
    
    public Behavior findValue(long uid, long nid) {
    	for(Behavior behavior:arrayList) {
    		if (behavior.getUid() == uid && behavior.getNid() == nid)
    			return behavior;
    	}
    	return null;
    }
    
    /**
     * sort all the pairs of SparseVector.
     */
	public void sort() {
    	Collections.sort(arrayList);
    }
    
    /**
     * Remove a pair from SparseBehavior.
     *
     * @param key the key of the Behavior wanted to remove
     */    
    public void RemoveBehavior(int key) {
    	arrayList.remove(key);
    }
    
	
	/**
	 * Output the elements into screen.
	 */
	public void display() {
		System.out.println("{");
		for(Behavior behavior:arrayList) {
			behavior.display();
			System.out.println(" ");
        }
		System.out.println("}");
	}
}
