package edu.ruc.ranker;

public class Score implements Comparable {
	public double similarity;
	public int position;
	
	/**
	 * Constructor for a Score.
	 * 
	 * @param similarity
	 * @param position
	 */
	public Score(double similarity, int position) {
		this.similarity = similarity;
		this.position = position;
	}
	
    /**
     * Comparator.
     */
    @Override
    public int compareTo(Object o) {
        if(this == o) {
            return 0;            
        }
        else if (o != null && o instanceof Score) {   
            Score p = (Score) o; 
            if(similarity > p.similarity){
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
