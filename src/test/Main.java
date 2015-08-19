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
	 private static final String news_filename="";//the location of news_file 
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
		 for(int i=0;i<users.size();i++) {
			 User user = users.getUserAt(i);
			 Ranker ranker = new Ranker();
			 ranker.query(resultStore, user, "sports", newsData.getNewsList("sports").getNewsList());
			 ranker.query(resultStore, user, "social", newsData.getNewsList("social").getNewsList());
			 ranker.query(resultStore, user, "economy", newsData.getNewsList("economy").getNewsList());
			 ranker.query(resultStore, user, "all", newsData.getNewsList("all").getNewsList());
		 }
	 }
	 private static News CreateNews(){
		 return new News(num_news++);
	 }
	 
	 private static void CreateUsers(){
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
		 Attribute b = new Attribute(VectorType.SPARSE,dict,attributeSet,"title");
		 b.addFeature("贝克汉姆",1);
		 b.addFeature("曼",1);
		 b.addFeature("联",1);
		 u.pushBack(a);
		 u.pushBack(b);
		 users.pushBack(u);
		 users.display();
     }

	 private static void Preprocess() throws IOException{//the preprocess 
		 InputNewsFile(news_filename,default_code);
    	 // InputUserFile(user_filename, default_code);
		 CreateUsers();
		 
    	 doHotness();
	 }
	 private static Double getHotnessScore(){//return score of hotness
		 return 0.0;
	 }
	 
	 private static void doHotness(){
		 getHotnessScore();
	 }
	 
	 private static void InputNewsFile(String filename,String code) throws IOException{
		 //read newsdata that has been tokenized
		 //add the dictionary
		 BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream(filename),code));
		 String str;
		 while((str=br.readLine())!=null){
			 br.readLine();//read time
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
			 // add attributes,add title,body,news_class
			 doHotness();
			 // add the newsdatabase
			 newsData.setNews(news);
		 }
	 }
	 private static void AddDictionary(String str){
		 
	 }
     //private static void InputUserFile(String filename,String code){
		 
	 //}
     
     private static void Load_feature(){// load the feature of news and user
    	 
     }
     public static void main(String[] args) throws IOException{
    	 Initialize();
    	 //preprocess do above actions
    	 
    	 //InputNewsFile(news_filename,default_code);
    	 //InputUserFile(user_filename, default_code);
    	 
    	 //doHotness();
    	 Preprocess();
    	 //set feature
    	 Load_feature();
    	 //
    	 Ranker();
    	 //print results
    	 //Print();
    	 
     }
}
