package edu.ruc.data;

import java.util.ArrayList;

public class Dictionary {
	private int length;
	private ArrayList<Alphabet> arrayList;
	
    /**
     * Constructor for a Dictionary.
     */
    public Dictionary() {
    	length = 0;
    	arrayList = new ArrayList<Alphabet>();
    }
    
    /**
     * Get size.
     */
    public int size() {
    	return length;
    }

    /**
     * Add Alphabet to Dictionary.
     *
     * @param labelSet the Alphabet wanted to push back
     * @param symbols a array of Strings
     */    
    public void pushBack(Alphabet labelSet) {
    	arrayList.add(labelSet);
    	length++;
    }
    public void pushBack() {
    	arrayList.add(new Alphabet());
    	length++;
    }
    public void pushBack(String[] symbols) {
    	arrayList.add(new Alphabet(symbols));
    	length++;
    }
    public void pushBack(String symbol) {
    	arrayList.add(new Alphabet(symbol));
    	length++;
    }
    
    /**
     * Get Alphabet from Dictionary.
     *
     * @param i the index of the Alphabet wanted to get
     */    
    public Alphabet getAlphabetAt(int i) {
    	while (i >= length) {
    		pushBack();
    	}
    	Alphabet labelSet = (Alphabet) arrayList.get(i);
		return labelSet;
    }
}
