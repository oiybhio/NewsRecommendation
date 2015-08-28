package edu.ruc.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import edu.ruc.data.Alphabet;
import edu.ruc.data.Attribute;
import edu.ruc.data.Dictionary;
import edu.ruc.data.VectorType;
import edu.ruc.news.News;
import edu.ruc.news.NewsList;

public class NewsDatabase {
      private ArrayList<News> array;
      private HashSet<Integer> set;
      private HashSet<String> queryset;
	  private SolrClient solr;
	  private Connection con;
	  public NewsDatabase(){
    	  array=new ArrayList<>();
    	  set=new HashSet<>();
    	  queryset=new HashSet<>();
    	  queryset.add("AND");
    	  queryset.add("OR");
    	  queryset.add("NOT");
    	  queryset.add("||");
    	  queryset.add("&&");
    	  queryset.add("!");
      }
      //
	  	 
	  public void setSolr(SolrClient s){
		  this.solr=s;
	  }
	  public void setConnection(Connection con){
		  this.con=con;
	  }
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
      public NewsList getNewsList(String category){
    	  NewsList newsList=new NewsList();
    	  if(category.equals("all")){
    		  for(News n:array){
        		  newsList.addNews(n);
        	  }
        	  return newsList;
    	  }else{
    	  for(News n:array){
    		  if(n.getCategory().equals(category)){
    			  newsList.addNews(n);
    		  }
    	  }
    	  }
    	  return newsList;
      }
      public void SaveNews(News n){
    	  
      }
      public void saveVector(News n) throws SQLException{
    	    String strsql = "insert into vector (id,attribute_name,vector,type)"
					+ " values(?,?,?,?)";
			
			PreparedStatement pstmt = con.prepareStatement(strsql);
		    pstmt.setLong(1, n.getID());
		    pstmt.setString(2, "title");
		    pstmt.setString(3, n.getAttribute("title").vectorToString());
		    pstmt.setInt(4, 0);
		    boolean rs = pstmt.execute();
		    pstmt = con.prepareStatement(strsql);
		    pstmt.setLong(1, n.getID());
		    pstmt.setString(2, "body");
		    pstmt.setString(3, n.getAttribute("body").vectorToString());
		    pstmt.setInt(4, 0);
		    rs = pstmt.execute();
		    
		    pstmt = con.prepareStatement(strsql);
		    pstmt.setLong(1, n.getID());
		    pstmt.setString(2, "hotness");
		    pstmt.setString(3, n.getAttribute("hotness").vectorToString());
		    pstmt.setInt(4, 1);
		   
		    
		    rs = pstmt.execute();
      }
      public NewsList getNewsListbyTopic(HashMap<String,Double> map,Dictionary dict,Alphabet attributeSet
    		  ,SolrClient SORL) {
    	  NewsList newsList=new NewsList();
    	  Iterator iter=map.entrySet().iterator();
    	  String myquery="";
    	  boolean if_first=true;
    	  while (iter.hasNext()) {
    		  Map.Entry entry = (Map.Entry) iter.next();
    		  if(if_first){
    			    String key = (String)entry.getKey();
    	    	    Double val = (Double)entry.getValue();
					String query=ClientUtils.escapeQueryChars(key);
					if(queryset.contains(query)){
						continue;
					}
					if_first=false;
					myquery+=("title:"+query+"^"+val);
					
					
				}else{
					    String key = (String)entry.getKey();
	    	    	    Double val = (Double)entry.getValue();
						String query=ClientUtils.escapeQueryChars(key);
						if(queryset.contains(query)){
							continue;
						}
						myquery+=(" OR title:"+query+"^"+val);
				
				}
    		
    		
    	  }
    	    System.out.println(myquery);
    	    SolrQuery parameters = new SolrQuery();
	    	parameters.set("q", myquery);
	    	parameters.set("fl","id,title,body,date");
	    	parameters.set("row","1000");
	    	try{
	    	QueryResponse response = solr.query(parameters);
	    	SolrDocumentList list = response.getResults();
	    	Statement stmt = con.createStatement();
	    	
	    	for(int i=0;i<list.size();i++){
	    		
	    			SolrDocument sd=list.get(i);
	    			long id=Long.parseLong(sd.get("id").toString());
	    			News news=new News(id);
	    			
	    			String title_nlp=sd.get("title").toString();
	    			String body_nlp=sd.get("body").toString();
	    			String date=sd.get("date").toString();
	    			news.setTitle(title_nlp);
	   			    news.setBody(body_nlp);
	   			    news.setDate(date);
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
	   			 Double hotness_score=getHotnessScore(title_nlp,SORL);
	   			 Attribute hotness_attribute=new Attribute(
	   					 VectorType.DENSE, dict, attributeSet, "hotness");
	   			 hotness_attribute.addFeature(hotness_score);
	   			 news.setAttribute(hotness_attribute);
	   			 // add the newsdatabase
	   			 newsList.addNews(news);
	   			
	   			
	    	}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		
	    	}
	    	   
    	      //String id_str=(String)sd.get("id");
	    	   //System.out.println(id_str);
//    	  for(int i=1;i<10;i++){
//    		  
//	    	   String sql = "select vector from vector where id="+i+" and attribute_name="+"'title'";
//	    	   //System.out.println(sql);
//	    	   Statement stmt = con.createStatement();
//	              
//	           ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
////	           Attribute a=new Attribute(vectorType, dict, attributeSet, attributeName)
////	          
//	           String title_vec=rs.getString(1) ;// 入如果返回的是int类型可以用getInt()
//	            System.out.println();
//	   	    
////	    	   System.out.println(id);  
//	    	}
    	    return newsList;
      }
      public Double getHotnessScore(String text,SolrClient SORL) throws IOException, SolrServerException{//return score of hotness
			 StringTokenizer st=new StringTokenizer(text," ");
				boolean if_first=true;
				String myquery="";
				while(st.hasMoreTokens()){
					if(if_first){
						String token=st.nextToken();
						String query=ClientUtils.escapeQueryChars(token);
						
						if_first=false;
						myquery+=("title:"+query);
						
						
					}else{
						String token=st.nextToken();
						String query=ClientUtils.escapeQueryChars(token);
						myquery+=(" OR title:"+query);
					
					}
					
				}
				SolrQuery parameters = new SolrQuery();
		    	parameters.set("q", myquery);
		    	parameters.set("fl","score,title");
		    	Double sum=0.0;
		    	QueryResponse response = SORL.query(parameters);
		    	SolrDocumentList list = response.getResults();
		    	for(int i=0;i<list.size();i++){
		    	   SolrDocument sd=list.get(i);
		    	   //System.out.println(sd);
		    	   sum+=Double.parseDouble(sd.get("score").toString());
		    	}
		    	//System.out.println(sum);
		    	//System.out.println(sum/list.size());
		    	return sum/list.size();
		 }
		 
	  
      public void setURL(String url){
    	  
      }
      
      
      
}
