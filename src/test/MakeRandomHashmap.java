package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class MakeRandomHashmap {
	private List<TopicWords> arrayList;
	
	public MakeRandomHashmap() throws IOException {
		arrayList = new ArrayList<TopicWords>();
		
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						new FileInputStream("src/ChineseTopicWords/ChineseTopicWords.txt"),"utf-8"));
		String str;
		while( (str = br.readLine()) != null) {
			TopicWords topicWords = new TopicWords();
			StringTokenizer st = new StringTokenizer(str," ");
			while(st.hasMoreTokens()){
				String token=st.nextToken();
				topicWords.add(token);
			}
			arrayList.add(topicWords);
		}
		br.close();
	}
	
	private int getRandomNumber(int l, int r, Random rand) {
		int num = rand.nextInt(r - l + 1) + l;
		return num;
	}
	
	public HashMap<String, Double> getRandomHashmap() {
		HashMap<String, Double> indices = new HashMap<String, Double>();
		Random rand = new Random();
		int TopicNum = 2;
		int WordNum = 3;
		int pos = 0;
		int length = arrayList.size();
		for(int i = TopicNum; i > 0; i--) {
			pos = getRandomNumber(pos, length - 1 - (i - 1), rand);
			List<String> words = arrayList.get(pos).getRandomWords(WordNum, rand);
			for (String word:words) {
				double value = rand.nextDouble();
				indices.put(word, new Double(value));
			}
			pos++;
		}
		return indices;
	}
	
}
