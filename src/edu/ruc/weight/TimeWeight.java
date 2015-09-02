package edu.ruc.weight;
import edu.ruc.WebService.*;

public class TimeWeight {
	/**
	 * 考虑behaveWeight
	 */
	private long date;
	private int Behave;
	public TimeWeight(int Behave, long date){
		this.Behave = Behave;
		this.date = date;
	}
	public double getWeight() {
		double t = 0.0;
		double gap = (System.currentTimeMillis() - date)/1000/60/60/24;
		t = 1+BehaveWeight.weight[Behave] * Math.exp(-gap) ;
	//	System.out.println("pppppp "+t);
		return t;
	}
}
