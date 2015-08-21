package test;

import edu.ruc.data.*;
import edu.ruc.data.Dictionary;
import edu.ruc.news.*;
import edu.ruc.user.*;
import edu.ruc.ranker.*;
import edu.ruc.database.*;

import java.io.*;
import java.util.*;

public class Main {
	 private static NewsDatabase newsData;//the database of news
	 private static UserDatabase userData;//the database of user
	 private static OnlineUsers users;
	 private static ResultStore resultStore;
	 private static Dictionary dict;// the dictionary of features
	 private static Alphabet attributeSet;// the alphabet of attribute name
	 private static final String news_filename="src/news_data/news_data_nlp.txt";//the location of news_file 
	 private static final String user_filename="";//the location of user_file
	 private static final String hotness_url="";//the url of solr
	 private static final String print_filename="";
	 private static String default_code="utf-8";
	 private static long num_news;
	 
	 private static void Initialize(){//Initialize
		 //initialize variables
		 num_news=0;
		 newsData=new NewsDatabase();
		 userData=new UserDatabase();
		 resultStore=new ResultStore();
		 dict=new Dictionary();
		 attributeSet=new Alphabet();
	 }
	 private static void Ranker(){//for every user ,rank the newslist
		 //System.out.println(users.size());
		 /*List<News> array = newsData.getNewsList("all").getNewsList();
		 for(News news:array) {
			 news.display();
		 }*/
		 
		 for(int i=0;i<users.size();i++) {
			 User user = users.getUserAt(i);
			 Ranker ranker = new Ranker();
			 //ranker.query(resultStore, user, "sports", newsData.getNewsList("sports").getNewsList());
			 //ranker.query(resultStore, user, "social", newsData.getNewsList("social").getNewsList());
			 //ranker.query(resultStore, user, "economy", newsData.getNewsList("economy").getNewsList());
			 ranker.query(resultStore, user, "all", newsData.getNewsList("all").getNewsList());
		 }
	 }
	 private static News CreateNews(){
		 return new News(num_news++);
	 }
	 
	 /*private static void CreateUsers(){
         	String[] features = new String[]{"上涨","涨幅","姚明","世界杯","骗","杀"};
		 users = new OnlineUsers();
		 for(int i=0;i<3;i++) {
			 User u = new User(i+1);
			 Attribute a = new Attribute(VectorType.SPARSE,dict,attributeSet,"title");
			 a.addFeature(features[2*i],1);
			 a.addFeature(features[2*i+1],1);
			 u.pushBack(a);
			 users.pushBack(u);
		 }
		 //Create the fourth user
		 User u = new User(4);
		 Attribute a = new Attribute(VectorType.SPARSE,dict,attributeSet,"title");
		 a.addFeature("增长",1);
		 a.addFeature("风险",1);
		 a.addFeature("男篮",1);
		 a.addFeature("联赛",1);
		 Attribute b = new Attribute(VectorType.SPARSE,dict,attributeSet,"body");
		 b.addFeature("贝克汉姆",1);
		 b.addFeature("曼联",1);
		 u.pushBack(a);
		 u.pushBack(b);
		 users.pushBack(u);
		 users.display();
		 System.out.println();
     }*/
	 
	 private static void Preprocess() throws IOException{//the preprocess 
		 InputNewsFile(news_filename,default_code);
    	 // InputUserFile(user_filename, default_code);
		 //CreateUsers();
		 Outputnewsdatabase("src/news_database/news.txt");
		 MakeRandomUser makeRandomUser = new MakeRandomUser();
		 int userNum = 2;
		 int TopicNum = 5;
		 int WordNum = 3;
		 users = makeRandomUser.getRandomUser(userNum, TopicNum, WordNum, dict, attributeSet);
		 System.out.println();
		 users.display();
		 System.out.println();
	 }
	 
	 private static Double getHotnessScore(String text,int order) throws IOException{//return score of hotness
		 BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream(
						 "src/hotness/hotness.txt"),"utf-8"));
		 String str;
		 int sum=1;
		 while((str=br.readLine())!=null){
			 if(sum==order){
				 return Double.parseDouble(str);
			 }
			 sum++;
		 }
		 
		 return 0.0;
	 }
	 
	 
	 
	 private static void InputNewsFile(String filename,String code) throws IOException{
		 //read newsdata that has been tokenized
		 //add the dictionary
		 BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream(filename),code));
		 String str;
		 int order=1;
		 while((str=br.readLine())!=null){
			 //System.out.println(order);
			 String date=br.readLine();//read time
			 br.readLine();//read url
			 String title=br.readLine();
			 String title_nlp=br.readLine();
			 String body=br.readLine();
			 String body_nlp=br.readLine();
			 
			 String news_class=br.readLine();
			 
			 //construct news
			 News news=CreateNews();
			 news.setTitle(title);
			 news.setBody(body);
			 news.setCategory(news_class);
			 news.setDate(date);
			 //title attribute
			 Attribute title_attribute=new Attribute(VectorType.SPARSE, 
					 dict, attributeSet, "title");
			 StringTokenizer st=new StringTokenizer(title_nlp," ");
			 while(st.hasMoreTokens()){
				 String token=st.nextToken();
				 if(title_attribute.getFeature(token)==0){
					 title_attribute.addFeature(token, 1.0);
				 }else{
					 Double d=title_attribute.getFeature(token);
					 title_attribute.modifyFeature(token, d+1);
				 }
			 }
			 news.setAttribute(title_attribute);
			 //
			//title attribute
			 Attribute body_attribute=new Attribute(VectorType.SPARSE, 
					 dict, attributeSet, "body");
			 st=new StringTokenizer(body_nlp," ");
			 while(st.hasMoreTokens()){
				 String token=st.nextToken();
				 if(body_attribute.getFeature(token)==0){
					 body_attribute.addFeature(token, 1.0);
				 }else{
					 Double d=body_attribute.getFeature(token);
					 body_attribute.modifyFeature(token, d+1);
				 }
			 }
			 news.setAttribute(body_attribute);
			 // add attributes,add title,body,news_class
			 Double hotness_score=getHotnessScore(title_nlp, order);
			 Attribute hotness_attribute=new Attribute(
					 VectorType.DENSE, dict, attributeSet, "hotness");
			 hotness_attribute.addFeature(hotness_score);
			 news.setAttribute(hotness_attribute);
			 // add the newsdatabase
			 newsData.setNews(news);
			 order++;
		 }
	 }
	 
     //private static void InputUserFile(String filename,String code){
		 
	 //}
	 private static void InputNewsdatabase(String filename) throws IOException{
		 BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream(filename),"utf-8"));
		 String str;
		 while((str=br.readLine())!=null){
			 
			 StringTokenizer st=new StringTokenizer(str,"\t");
			String id=st.nextToken();
			String date=st.nextToken();
			String category=st.nextToken();
			String title=st.nextToken();
			String body=st.nextToken();
			String title_vector=st.nextToken();
			String body_vector=st.nextToken();
			String hotness_vector=st.nextToken();
			News news=new News(Integer.parseInt(id));
			news.setDate(date);
			news.setBody(body);
			news.setCategory(category);
			news.setTitle(title);
			Attribute title_a=new Attribute(VectorType.SPARSE, dict, attributeSet, "title",title_vector);
		    news.setAttribute(title_a);
		    Attribute body_a=new Attribute(VectorType.SPARSE, dict, attributeSet, "body",body_vector);
		    news.setAttribute(body_a);
		    Attribute hotness_a=new Attribute(VectorType.DENSE, dict, attributeSet, "hotness",hotness_vector);
		    news.setAttribute(hotness_a);
		 }
		 br.close();
	 }
	 private static void Outputnewsdatabase(String filename) throws IOException{
		 BufferedWriter bw=new BufferedWriter(new 
				 OutputStreamWriter(new FileOutputStream(filename),"utf-8"));
		NewsList newsList=newsData.getNewsList("all");
		ArrayList<News> arrayNews=newsList.getNewsList();
		for(News n:arrayNews){
			bw.write(n.getID()+"\t");
			bw.write(n.getDate()+"\t");
			bw.write(n.getCategory()+"\t");
			bw.write(n.getTitle()+"\t");
			bw.write(n.getBody()+"\t");
			bw.write("title:"+n.getAttribute("title").vectorToString()+"\t");
			bw.write("body:"+n.getAttribute("body").vectorToString()+"\t");
			bw.write("hotness:"+n.getAttribute("hotness").vectorToString()+"\t");
			bw.newLine();
		}
		bw.close();
		 
	 }
     private static void Load_feature(){// load the feature of news and user
    	 
     }
     public static void main(String[] args) throws IOException{
    	 Initialize();
    	 //preprocess do above actions
    	 
    	 //InputNewsFile(news_filename,default_code);
    	 //InputUserFile(user_filename, default_code);
    	 
    	 //doHotness();
    	 Preprocess();
    	 
    	 //
    	 Ranker();
    	 //print results
    	 //Print();
    	 
     }
}
