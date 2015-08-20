package test;

import java.util.*;

public class TopicWords {
	private List<String> arrayList;
	
	public TopicWords() {
		arrayList = new ArrayList<String>();
	}
	
	public void add(String str) {
		arrayList.add(str);
	}
	
	private int getRandomNumber(int l, int r, Random rand) {
		int num = rand.nextInt(r - l + 1) + l;
		return num;
	}
	
	public List<String> getRandomWords(int n, Random rand) {
		List<String> ret = new ArrayList<String>();
		
		int pos = 0;
		int length = arrayList.size();
		for(int i = n; i > 0; i--) {
			pos = getRandomNumber(pos, length - 1 - (i - 1), rand);
			ret.add(arrayList.get(pos));
			pos++;
		}
		
		return ret;
	}
	
}
