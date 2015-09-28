package edu.ruc.ranker;

import edu.ruc.user.*;

import java.util.*;

public class ResultStore {
	private List<Result> arrayList;
	
	public ResultStore() {
		arrayList = new ArrayList<Result>();
	}
	
	public void clear() {
		arrayList = new ArrayList<Result>();
	}
	
	public void clear(User user) {
		for(int i = 0; i < arrayList.size(); i++) {
			Result result = arrayList.get(i);
			if (Objects.equals(result.getUser(),user))
			{
				arrayList.remove(result);
				i--;
			}
		}
	}
	
	public void add(Result result) {
		arrayList.add(result);
	}
	
	public Result find(User user, String category, RankerType rankerType) {
		for(Result result:arrayList) {
			if ( Objects.equals(result.getUser(),user) 
					&& Objects.equals(result.getCategory(),category) 
					&& Objects.equals(result.getRankerType(),rankerType) ) {
				if (result.validationChecking())
					return result;
				else {
					//arrayList.remove(result);
					return null;
				}
			}
		}
		return null;
	}
	
}
