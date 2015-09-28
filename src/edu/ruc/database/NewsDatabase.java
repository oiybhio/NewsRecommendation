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
      public NewsList getNewsListbyTopic(HashMap<String,Double> map,Dictionary dict,Alphabet attributeSet
    		  ) {
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
    	    //System.out.println(myquery);
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
	    			
//	    			String title_nlp=sd.get("title").toString();
//	    			String body_nlp=sd.get("body").toString();
//	    			String date=sd.get("date").toString();
//	    			news.setTitle(title_nlp);
//	   			    news.setBody(body_nlp);
//	   			    news.setDate(date);
//	    			Attribute title_attribute=new Attribute(VectorType.SPARSE, 
//	   					 dict, attributeSet, "title");
//	   			 StringTokenizer st=new StringTokenizer(title_nlp," ");
//	   			 while(st.hasMoreTokens()){
//	   				 String token=st.nextToken();
//	   				 if(title_attribute.getFeature(token)==0){
//	   					 title_attribute.addFeature(token, 1.0);
//	   				 }else{
//	   					 Double d=title_attribute.getFeature(token);
//	   					 title_attribute.modifyFeature(token, d+1);
//	   				 }
//	   			 }
//	   			ArrayList<String> symbol_title=new ArrayList<>();
//	   			 symbol_title=(ArrayList<String>) title_attribute.getSymbolList();
//	   			 for(String s:symbol_title){
//	   				 Double tf=title_attribute.getFeature(s);
//	   				 SolrQuery para = new SolrQuery();
//	   				 String myq="*:*";
//	   		    	 para.set("q", myq);
//	   		    	 para.set("fl","id,idf(\"title\",\""+s+"\")");
//	   		    	 QueryResponse res = solr_xinwen.query(para);
//	   		    	 SolrDocumentList list2 = res.getResults();
//	   		    	 Double idf=Double.parseDouble(list2.get(0).get("idf(\"title\",\""+s+"\")").toString());
//	   		    	title_attribute.modifyFeature(s, tf*idf);
//	   			 }
//	   			 
//	   			title_attribute.getSparseVector().getTopK(this.TopK);
//	   			title_attribute.getSparseVector().sortKey();
//	   			 news.setAttribute(title_attribute);
//	   			 //
//	   			//body attribute
//	   			 Attribute body_attribute=new Attribute(VectorType.SPARSE, 
//	   					 dict, attributeSet, "body");
//	   			 st=new StringTokenizer(body_nlp," ");
//	   			 while(st.hasMoreTokens()){
//	   				 String token=st.nextToken();
//	   				 if(body_attribute.getFeature(token)==0){
//	   					 body_attribute.addFeature(token, 1.0);
//	   				 }else{
//	   					 Double d=body_attribute.getFeature(token);
//	   					 body_attribute.modifyFeature(token, d+1);
//	   				 }
//	   			 }
//	   			 //body attribute
////	   			 ArrayList<String> symbol_body=new ArrayList<>();
////	   			 symbol_body=(ArrayList<String>) body_attribute.getSymbolList();
////	   			 for(String s:symbol_body){
////	   				 Double tf=body_attribute.getFeature(s);
////	   				 SolrQuery para = new SolrQuery();
////	   				 String myq="*:*";
////	   		    	 para.set("q", myq);
////	   		    	 para.set("fl","id,idf(\"body\",\""+s+"\")");
////	   		    	 QueryResponse res = solr_xinwen.query(para);
////	   		    	 SolrDocumentList list2 = res.getResults();
////	   		    	 Double idf=Double.parseDouble(list2.get(0).get("idf(\"body\",\""+s+"\")").toString());
////	   		    	 body_attribute.modifyFeature(s, tf*idf);
////	   		    	 //System.out.println(tf*idf);
////	   			 }
//	   			 
//	   			 body_attribute.getSparseVector().getTopK(this.TopK);
//	   			 body_attribute.getSparseVector().sortKey();
//	   			 news.setAttribute(body_attribute);
//	   			 
//	   			 
//	   			 // add attributes,add title,body,news_class
//	   			 Double hotness_score=getHotnessScore(title_nlp);
//	   			 Attribute hotness_attribute=new Attribute(
//	   					 VectorType.DENSE, dict, attributeSet, "hotness");
//	   			 hotness_attribute.addFeature(hotness_score);
//	   			 news.setAttribute(hotness_attribute);
//	   			 // add the newsdatabase
//	   			 newsList.addNews(news);
//	    	}
	   			
	    	
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
      
      public NewsList getNewsListbyTopic(HashMap<String,Double> map,Dictionary dict,Alphabet attributeSet,int Number
    		  ) {
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
    	    //System.out.println(myquery);
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
		    	//System.out.println(sum);
		    	//System.out.println(sum/list.size());
		    	return sum/list.size();
		 }
		 
	  
      public void setURL(String url){
    	  
      }
      
      
      
}
