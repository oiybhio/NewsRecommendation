package edu.ruc.news;

import java.util.ArrayList;

public class NewsList {
	 private ArrayList<News> array;
	 //构造函数
     public NewsList(){
    	 array=new ArrayList<News>();
     }
     //添加news 如果存在 就覆盖 返回false
     public boolean addNews(News news){
    	 for(News n:array){
    		 if(n.getID()==news.getID()){
    			 array.remove(n);
    			 array.add(news);
    			 return false;
    		 }
    	 }
    	 array.add(news);
    	 return true;
     }
     
     
     public void addNewsList(NewsList newslist){
    	 
     }
     
     public boolean removeNewsByNews(News news){
    	 for(News n:array){
    		 if(n.getID()==news.getID()){
    			 array.remove(n);
    			 
    			 return true;
    		 }
    	 }
    	 System.out.println("nothing remove");
    	 return false;
     }

     public boolean removeNewsByID(long id){
    	 for(News n:array){
    		 if(n.getID()==id){
    			 array.remove(n);
    			 
    			 return true;
    		 }
    	 }
    	 System.out.println("nothing remove");
    	 return false;
     }

     public News getNews(long id){
    	 for(News n:array){
    		 if(n.getID()==id){
    			 return n;
    		 }
    	 }
    	 System.out.println("cann't find");
    	 return null;
     }

     public ArrayList<News> getNewsList(){
    	 return array;
     }
}

     
