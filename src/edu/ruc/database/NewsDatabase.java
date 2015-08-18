package edu.ruc.database;

import java.util.ArrayList;
import java.util.HashSet;

import edu.ruc.news.News;
import edu.ruc.news.NewsList;

public class NewsDatabase {
      private ArrayList<News> array;
      private HashSet<Integer> set;
	
	  public NewsDatabase(){
    	  array=new ArrayList<>();
    	  set=new HashSet<>();
      }
      //
      public void setNews(News news){
    	  if(set.contains(news.getID())==false){
    		  array.add(news);
    		  set.add((int)news.getID());
    	  }
    	  else{
    		  for(News n:array){
    			  if(n.getID()==news.getID()){
    				  array.remove(n);
    				  array.add(news);
    			  }
    		  }
    	  }
      }
      
      public boolean isExistNews(News news){
    	  
    	  return set.contains(news.getID());
      }
      public boolean isExistNews(long nid){
    	  return set.contains(nid);
      }
      public News getNews(long id){
    	  for(News n:array){
    		  if(n.getID()==id)
    			  return n;
    	  }
    	  System.out.println("no news");
    	  return new News(-1);
      }
      public NewsList getNewsListbyCategory(String category){
    	  NewsList newsList=new NewsList();
    	  for(News n:array){
    		  if(n.getCategory().equals(category)){
    			  newsList.addNews(n);
    		  }
    	  }
    	  return newsList;
      }
      public void setURL(String url){
    	  
      }
      
      
      
}
