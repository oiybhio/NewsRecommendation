package test;

import java.util.*;
import java.io.*;

import edu.ruc.data.*;
import edu.ruc.data.Dictionary;
import edu.ruc.user.*;

public class MakeRandomUser {
	private List<TopicWords> arrayList;
	
	public MakeRandomUser() throws IOException {
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
	
	public OnlineUsers getRandomUser(int userNum, int TopicNum, int WordNum, Dictionary dict, Alphabet attributeSet) {
		OnlineUsers users = new OnlineUsers();
		Random rand = new Random();
		for(int i = 1; i <= userNum; i++) {
			System.out.println("UserID : " + i);
			User user = new User(i);
			Attribute attribute = new Attribute(VectorType.SPARSE, dict, attributeSet, "body");
			int pos = 0;
			int length = arrayList.size();
			for(int j = TopicNum; j > 0; j--) {
				pos = getRandomNumber(pos, length - 1 - (j - 1), rand);
				List<String> words = arrayList.get(pos).getRandomWords(WordNum, rand);
				for (String word:words) {
					attribute.addFeature(word, 1);
					System.out.print(word + " ");
				}
				pos++;
			}
			user.pushBack(attribute);
			users.pushBack(user);
			System.out.println();
		}
		return users;
	}
	
}
