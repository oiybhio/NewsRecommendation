package edu.ruc.database;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
	  private SolrClient solr_xinwen;
	  private SolrClient solr_weibo;
	  private SolrClient solr_news;
	  private Connection con;
	  private int TopK;
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
    	  this.TopK=5;
      }
      //
	  public void setTOPK(int k){
		  this.TopK=k;
	  }
	  public void setSolr_news(SolrClient s){
		  this.solr_news=s;
	  }
	  public void setSolr_xinwen(SolrClient s){
		  this.solr_xinwen=s;
	  }
	  public void setSolr_weibo(SolrClient s){
		  this.solr_weibo=s;
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
      public void SaveNews(News n) throws SQLException{
    	  String strsql = "replace into content (id,date,title,body)"
					+ " values(?,?,?,?)";
			
			PreparedStatement pstmt = con.prepareStatement(strsql);
		    pstmt.setLong(1, n.getID());
		    pstmt.setString(2, n.getDate());
		    pstmt.setString(3, n.getTitle());
		    pstmt.setString(4,n.getBody());
		    boolean rs = pstmt.execute();
		    
      }
      public void SaveNewsLTR(News n) throws SQLException{
    	  String strsql = "replace into contentLTR (id,oldarticle_id,headline,headline_nlp)"
					+ " values(?,?,?,?)";
			
			PreparedStatement pstmt = con.prepareStatement(strsql);
		    pstmt.setLong(1, n.getID());
		    pstmt.setString(2, n.getOldarticle_id());
		    pstmt.setString(3, n.getHeadline());
		    pstmt.setString(4, n.getHeadline_nlp());
		    
		    boolean rs = pstmt.execute();
		    
      }
      public  void LoadNewsFromDatabase(Dictionary dict,Alphabet attributeSet) throws SQLException{
		    Statement stmt = con.createStatement();
	    	ResultSet result = stmt.executeQuery("select id,date,title,body from content");
	    	int order=0;
	    	while (result.next()){
//	    		if(order>=100){
//	    			break;
//	    		}
	    		order++;
	    		//System.out.println(order);
	            long id = Long.parseLong(result.getString("id"));
	            
	            String date = result.getString("date");
	            String title = result.getString("title");
	            String body = result.getString("body");
	            News news=new News(id);
	            news.setBody(body);
	            news.setDate(date);
	            news.setTitle(title);
	            String query_title="select vector from vector where id="+id+" and attribute_name=\'title\'";
	            //System.out.println(query_title);
	            Statement stmt_title = con.createStatement();
	            ResultSet result_title_vector = stmt_title.executeQuery(query_title);
	            String vector_title=null;
	            while(result_title_vector.next()){
	            	vector_title=result_title_vector.getString("vector");
	            }
	          // System.out.println(vector_title);
	            Attribute title_attribute=new Attribute(VectorType.SPARSE, 
	            		dict, attributeSet,"title",vector_title);
	            news.setAttribute(title_attribute);
	            
	            String query_body="select vector from vector where id="+id+" and attribute_name=\'body\'";
	            Statement stmt_body = con.createStatement();
	            ResultSet result_body_vector = stmt_body.executeQuery(query_body);
	            String vector_body=null;
	            while(result_body_vector.next()){
	            	vector_body=result_body_vector.getString("vector");
	            }
	            Attribute body_attribute=new Attribute(VectorType.SPARSE, 
	            		dict, attributeSet,"body",vector_body);
	            news.setAttribute(body_attribute);
	            String query_hotness="select vector from vector where id="+id+" and attribute_name=\'hotness\'";
	            Statement stmt_hotness = con.createStatement();
	            ResultSet result_hotness_vector = stmt_hotness.executeQuery(query_hotness);
	            String vector_hotness=null;
	            while(result_hotness_vector.next()){
	            	vector_hotness=result_hotness_vector.getString("vector");
	            }
	            Attribute hotness_attribute=new Attribute(VectorType.DENSE, 
	            		dict, attributeSet,"hotness",vector_hotness);
	            news.setAttribute(hotness_attribute);
	            array.add(news);
	        }
	 }
	 
      public  void LoadNewsFromDatabaseLTR(Dictionary dict,Alphabet attributeSet) throws SQLException{
		    Statement stmt = con.createStatement();
	    	ResultSet result = stmt.executeQuery("select id,headline from contentltr");
	    	int order=0;
	    	while (result.next()){
//	    		if(order>=1000){
//	    			break;
//	    		}
	    		order++;
//	    		if(order%100==0){
//	    			System.out.println(order);
//	    		}
	            long id = Long.parseLong(result.getString("id"));
	            
	            String headline=result.getString("headline");
	            News news=new News(id);
	            news.setHeadline(headline);
	            String query_headline="select vector from vectorltr where id="+id+" and attribute_name=\'headline\'";
	            //System.out.println(query_title);
	            Statement stmt_headline = con.createStatement();
	            ResultSet result_headline_vector = stmt_headline.executeQuery(query_headline);
	            String vector_headline=null;
	            while(result_headline_vector.next()){
	            	vector_headline=result_headline_vector.getString("vector");
	            }
	          // System.out.println(vector_title);
	            Attribute headline_attribute=new Attribute(VectorType.SPARSE, 
	            		dict, attributeSet,"headline",vector_headline);
	            news.setAttribute(headline_attribute);
	            //xiwen_hotness
	            String query_xinwen_hotness="select vector from vectorltr where id="+id+" and attribute_name=\'xinwen_hotness\'";
	            Statement stmt_xinwen_hotness = con.createStatement();
	            ResultSet result_xinwen_hotness_vector = stmt_xinwen_hotness.executeQuery(query_xinwen_hotness);
	            String vector_xinwen_hotness=null;
	            while(result_xinwen_hotness_vector.next()){
	            	vector_xinwen_hotness=result_xinwen_hotness_vector.getString("vector");
	            }
	            Attribute xinwen_hotness_attribute=new Attribute(VectorType.DENSE, 
	            		dict, attributeSet,"xinwen_hotness",vector_xinwen_hotness);
	            news.setAttribute(xinwen_hotness_attribute);
	            
	            //weibo_hotness
	            String query_weibo_hotness="select vector from vectorltr where id="+id+" and attribute_name=\'weibo_hotness\'";
	            Statement stmt_weibo_hotness = con.createStatement();
	            ResultSet result_weibo_hotness_vector = stmt_weibo_hotness.executeQuery(query_weibo_hotness);
	            String vector_weibo_hotness=null;
	            while(result_xinwen_hotness_vector.next()){
	            	vector_weibo_hotness=result_weibo_hotness_vector.getString("vector");
	            }
	            Attribute weibo_hotness_attribute=new Attribute(VectorType.DENSE, 
	            		dict, attributeSet,"weibo_hotness",vector_xinwen_hotness);
	            news.setAttribute(weibo_hotness_attribute);
	            array.add(news);
	        }
	 }
	 
 
      public void saveVector(News n) throws SQLException{
    	    String strsql = "replace into vector (id,attribute_name,vector,type)"
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
      public void saveVectorLTR(News n) throws SQLException{
  	    String strsql = "replace into vectorLTR (id,attribute_name,vector,type)"
					+ " values(?,?,?,?)";
			
			PreparedStatement pstmt = con.prepareStatement(strsql);
		    pstmt.setLong(1, n.getID());
		    pstmt.setString(2, "headline");
		    pstmt.setString(3, n.getAttribute("headline").vectorToString());
		    pstmt.setInt(4, 0);
		    boolean rs = pstmt.execute();
		    pstmt = con.prepareStatement(strsql);
		    pstmt.setLong(1, n.getID());
		    pstmt.setString(2, "xinwen_hotness");
		    pstmt.setString(3, n.getAttribute("xinwen_hotness").vectorToString());
		    pstmt.setInt(4, 1);
		    rs = pstmt.execute();
		    
		    pstmt = con.prepareStatement(strsql);
		    pstmt.setLong(1, n.getID());
		    pstmt.setString(2, "weibo_hotness");
		    pstmt.setString(3, n.getAttribute("weibo_hotness").vectorToString());
		    pstmt.setInt(4, 1);
		   
		    
		    rs = pstmt.execute();
    }
    
      public NewsList getNewsListbyTopic(HashMap<String,Double> map,
    		  String field,Dictionary dict,Alphabet attributeSet
    		  ) {
    	  NewsList newsList=new NewsList();
    	  String myquery="";
    	  if(map.size()==0){
    		  
    	  }else{
	    	  Iterator iter=map.entrySet().iterator();
	    	  
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
						myquery+=(field+":"+query+"^"+val);
						
						
					}else{
						    String key = (String)entry.getKey();
		    	    	    Double val = (Double)entry.getValue();
							String query=ClientUtils.escapeQueryChars(key);
							if(queryset.contains(query)){
								continue;
							}
							myquery+=(" OR "+field+":"+query+"^"+val);
					
					}
	    		
	    		
	    	  }
    	  	}
    	    if(myquery.equals("")){
    	    	myquery="*:*";
    	    }
    	    SolrQuery parameters = new SolrQuery();
	    	parameters.set("q", myquery);
	    	//parameters.set("fl","id,title,body,date");
	    	parameters.set("fl","id");
	    	parameters.set("rows","100");
	    	System.out.println(myquery);
	    	try{
	    	QueryResponse response = solr_news.query(parameters);
	    	SolrDocumentList list = response.getResults();
	    	Statement stmt = con.createStatement();
	    	HashSet<Long> setNewsID=new HashSet<Long>();
	    	//NewsList newsList=new NewsList();
	    	BufferedWriter bw2=new BufferedWriter(
					new OutputStreamWriter(new 
							FileOutputStream("newsid.txt",true),"utf-8"));
			System.out.println(list.size());
	    	for(int i=0;i<list.size();i++){
	    		
	    			SolrDocument sd=list.get(i);
	    			long id=Long.parseLong(sd.get("id").toString());
	    			setNewsID.add(id);
	    			bw2.write(id+"\t");
	    	}	
	    	bw2.newLine();
			bw2.close();
	    	BufferedWriter bw=new BufferedWriter(
					new OutputStreamWriter(new 
							FileOutputStream("newslist.txt",true),"utf-8"));
			 
	    	for(News n:array){		//News news=new News(id);
	    		if(setNewsID.contains(n.getID())){
	    			newsList.addNews(n);
	    			//write the newslist
	    			 bw.write(n.getTitle()+"\t");
	    			   
	    		}
	    	}
	    	bw.newLine();
			bw.close();
	    	return newsList;
	    			
//	    		
//	    	}
	   			
	    	
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		
	    	}
	    	   
    	        

    	    return newsList;
      }
      
      public NewsList getNewsListbyTopic(HashMap<String,Double> map,String field,
    		  Dictionary dict,Alphabet attributeSet,int Number
    		  ) {
    	  NewsList newsList=new NewsList();
    	  
    	  String myquery="";
    	  Iterator iter=map.entrySet().iterator();
    	  if(map.size()>0){
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
						myquery+=(field+":"+query+"^"+val);
						
						
					}else{
						    String key = (String)entry.getKey();
		    	    	    Double val = (Double)entry.getValue();
							String query=ClientUtils.escapeQueryChars(key);
							if(queryset.contains(query)){
								continue;
							}
							myquery+=(" OR "+field+":"+query+"^"+val);
					
					}
	    		
	    		
	    	  }
    	  }
    	    //System.out.println(myquery);
    	    if(myquery.equals("")){
    	    	myquery="*:*";
    	    }
    	    SolrQuery parameters = new SolrQuery();
	    	parameters.set("q", myquery);
	    	//parameters.set("fl","id,title,body,date");
	    	parameters.set("fl","id");
	    	parameters.set("rows",Number+"");
	    	System.out.println(myquery);
	    	try{
	    	QueryResponse response = solr_news.query(parameters);
	    	SolrDocumentList list = response.getResults();
	    	Statement stmt = con.createStatement();
	    	HashSet<Long> setNewsID=new HashSet<Long>();
	    	//NewsList newsList=new NewsList();
	    	BufferedWriter bw2=new BufferedWriter(
					new OutputStreamWriter(new 
							FileOutputStream("newsid.txt",true),"utf-8"));
			//System.out.println(list.size());
			
	    	for(int i=0;i<list.size();i++){
	    		
	    			SolrDocument sd=list.get(i);
	    			long id=Long.parseLong(sd.get("id").toString());
	    			setNewsID.add(id);
	    			bw2.write(id+"\t");
	    	}	
	    	if(list.size()<Number){
	    		SolrQuery parameters2 = new SolrQuery();
		    	parameters2.set("q", "*:*");
		    	//parameters.set("fl","id,title,body,date");
		    	parameters2.set("fl","id");
		    	parameters2.set("rows",(Number-list.size())+"");
		    	QueryResponse response2 = solr_news.query(parameters);
		    	SolrDocumentList list2 = response2.getResults();
		    	for(int j=0;j<list2.size();j++){
		    		
	    			SolrDocument sd2=list.get(j);
	    			long id=Long.parseLong(sd2.get("id").toString());
	    			setNewsID.add(id);
	    			bw2.write(id+"\t");
		    	}
		    	
			}
	    	bw2.newLine();
			bw2.close();
	    	BufferedWriter bw=new BufferedWriter(
					new OutputStreamWriter(new 
							FileOutputStream("newslist.txt",true),"utf-8"));
			 
	    	for(News n:array){		//News news=new News(id);
	    		if(setNewsID.contains(n.getID())){
	    			newsList.addNews(n);
	    			//write the newslist
	    			 bw.write(n.getTitle()+"\t");
	    			   
	    		}
	    	}
	    	bw.newLine();
			bw.close();
	    	return newsList;
	    			
//	    			
	   			
	    	
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		
	    	}
	    	   
    	     
    	    return newsList;
      }
      
      public Double getHotnessScore(String text) throws IOException, SolrServerException{//return score of hotness
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
		    	QueryResponse response = this.solr_xinwen.query(parameters);
		    	SolrDocumentList list = response.getResults();
		    	for(int i=0;i<list.size();i++){
		    	   SolrDocument sd=list.get(i);
		    	   //System.out.println(sd);
		    	   sum+=Double.parseDouble(sd.get("score").toString());
		    	}
		    	
		    	return sum/list.size();
		 }
		 
	  
      public void setURL(String url){
    	  
      }
      
      
      
}
