package test;

import edu.ruc.database.NewsDatabase;
import edu.ruc.database.UserDatabase;
import edu.ruc.news.News;
import edu.ruc.news.NewsList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import edu.ruc.data.*;

public class Main {
	 private static NewsDatabase newsData;//the database of news
	 private static UserDatabase userData;//the database of user
	 private static Dictionary dic_text;// the dictionary of text feature
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
		 dic_text=new Dictionary(); 
				 
	 	 
	 }
	 private static void Ranker(){//for every user ,rank the newslist
		 
	 }
	 private static News CreateNews(){
		 return new News(num_news++);
	 }
	 private static void Preprocess() throws IOException{//the preprocess 
		 InputNewsFile(news_filename,default_code);
    	 InputUserFile(user_filename, default_code);
    	 
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
			 AddDictionary(title);
			 String body=br.readLine();
			 AddDictionary(body);
			 String news_class=br.readLine();
			 AddDictionary(news_class);
			 //construct news
			 News news=CreateNews();
			 // add attributes by bohua do it ,add title,body,news_class
			 doHotness();
			 // add the newsdatabase
			 newsData.setNews(news);
		 }
	 }
	 private static void AddDictionary(String str){
		 
	 }
     private static void InputUserFile(String filename,String code){
		 
	 }
     
     private static void Load_feature(){// load the feature of news and user
    	 
     }
     private static void Print(){
    	 
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
    	 Print();
    	 
     }
}
