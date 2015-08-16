package test;

import edu.ruc.database.NewsDatabase;
import edu.ruc.database.UserDatabase;
import edu.ruc.news.NewsList;
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
	 private static void Initialize(){//Initialize
		 //initialize variables
	 	 
	 }
	 private static void Ranker(){//for every user ,rank the newslist
		 
	 }
	 
	 private static void Preprocess(){//the preprocess 
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
	 
	 private static void InputNewsFile(String filename,String code){
		 //read newsdata that has been tokenized
		 //add the dictionary
	 }
     private static void InputUserFile(String filename,String code){
		 
	 }
     
     private static void Load_feature(){// load the feature of news and user
    	 
     }
     private static void Print(){
    	 
     }
     public static void main(String[] args){
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
