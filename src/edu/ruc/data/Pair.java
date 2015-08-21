package edu.ruc.data;

import java.util.Objects;

public class Pair implements Comparable<Object> {
    private int first;
    private double second;

    /**
     * Constructor for a Pair.
     *
     * @param first the first object in the Pair
     * @param second the second object in the pair
     */
    public Pair(int first, double second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gets the key for this pair.
     *
     * @return the key for this pair
     */
    public int getKey() {
        return first;
    }
    
    /**
     * Gets the value for this pair.
     *
     * @return the value for this pair
     */
    public double getValue() {
        return second;
    }

    /**
     * Sets the key for this pair.
     *
     * @param first the key
     */
    public void setKey(int first) {
        this.first = first;
    }
    
    /**
     * Sets the value for this pair.
     *
     * @param second the value
     */
    public void setValue(double second) {
        this.second = second;
    }
    
    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link Pair} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     *         equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair p = (Pair) o;
        return Objects.equals(p.first, first) && Objects.equals(p.second, second);
    }

    /**
     * Convenience method for creating an appropriately typed pair.
     * @param a the first object in the Pair
     * @param b the second object in the pair
     * @return a Pair that is templatized with the types of a and b
     */
    public static Pair create(int first, double second) {
        return new Pair(first, second);
    }
    
    /**
     * Pair to String
     * 
     * @return String
     */
    public String pairToString() {
    	return String.valueOf(first) + ":" +String.valueOf(second);
    }
    
    /**
     * Output the elements of pair into screen.
     */    
    public void display() {
    	System.out.print( new Integer(first) + ":" + new Double(second) );
    }
    
    /**
     * Comparator.
     */
    @Override
    public int compareTo(Object o) {
        if(this == o) {
            return 0;            
        }
        else if (o != null && o instanceof Pair) {   
            Pair p = (Pair) o; 
            if(first <= p.first){
                return -1;
            }
            else {
            	return 1;
            }
        }else{
        	return -1;
        }
    }
}
