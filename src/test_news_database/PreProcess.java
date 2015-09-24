package test_news_database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import edu.ruc.data.Alphabet;
import edu.ruc.data.Attribute;
import edu.ruc.data.Dictionary;
import edu.ruc.data.VectorType;
import edu.ruc.database.NewsDatabase;
import edu.ruc.news.News;
import edu.ruc.news.NewsList;

public class PreProcess {
	 private static NewsDatabase newsData;
	 private static String default_code="utf-8";//the default code
	 private static Dictionary dict;// the dictionary of features
	 private static Alphabet attributeSet;// the alphabet of attribute name
//	 private static final String news_filename=
//			 "src/news_data/dat1000_example.txt";//the location of news_file 
	 private static final String news_filename=
			 "src/news_data/uci_solr_nlp.txt";//the location of news_file 
	 
	 private static final String idf_filename="D://users/wenhui_zhang/xinwen_data/idf/1.txt";
	 
	 private static String SOLR_NEWS_urlString = "http://localhost:8983/solr/UCI";
	 private static String SOLR_Xinwen_urlString = "http://localhost:8983/solr/Xinhua";
 	 private static int TopK;
	 private static SolrClient SOLR_NEWS ;
 	 private static SolrClient SOLR_XINWEN ;
 	 private static Connection CON;
	 public static void Initialize() throws SQLException, IOException{
		 TopK=5;
		 SOLR_NEWS = new HttpSolrClient(SOLR_NEWS_urlString);
		 SOLR_XINWEN=new HttpSolrClient(SOLR_Xinwen_urlString);
		 dict=new Dictionary();
		 dict.loadIDF(LoadIDFfile(idf_filename));
		 attributeSet=new Alphabet();
		 InitializeDatabase();
		 newsData=new NewsDatabase();
		 newsData.setSolr_news(SOLR_NEWS);
		 newsData.setSolr_xinwen(SOLR_XINWEN);
		 newsData.setConnection(CON);
//		 attributeSet.loadFromDatabase(CON);
//		 dict.loadFromDatabase(CON);
	 }
	 public static HashMap<String,Double> LoadIDFfile(String filename) throws IOException{
		 BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream(filename),default_code));
		 HashMap<String,Double> map=new HashMap<String, Double>();
		 String str;
		 int order=0;
	   	 while((str=br.readLine())!=null){
	   		
//	   		 if(order%1000==0){
//	   			 System.out.println(order);
//	   		 }
	   		StringTokenizer st=new StringTokenizer(str,"\t");
	   		while(st.hasMoreTokens()){
	   			StringTokenizer sst=new StringTokenizer(st.nextToken()," ");
	   			String key=sst.nextToken();
	   			Double val=Double.parseDouble(sst.nextToken());
	   			map.put(key, val);
	   		}
	   		order++;
	   	 }
	   	 
	   	 br.close();
		 return map;
		 
	 }
	 
	 public static void InitializeDatabase(){
	     try{   
    		//加载MySql的驱动类   
    		    Class.forName("com.mysql.jdbc.Driver") ;   
    	 }catch(ClassNotFoundException e){   
    	   System.out.println("找不到驱动程序类 ，加载驱动失败！");   
    	   e.printStackTrace() ;   
         } 
    	 String url = "jdbc:mysql://localhost:3306/xinhua?"
    	 		+ "useUnicode=true&characterEncoding=UTF-8" ;    
         String username = "root" ;   
         String password = "123456" ;   
         try{   
               CON=    
               DriverManager.getConnection(url , username , password ) ;   
         }catch(SQLException se){   
            System.out.println("数据库连接失败！");   
            se.printStackTrace() ;   
         }  
 }

	 public static void LoadNewsFromFile(String inputfile) throws IOException, SolrServerException, SQLException{
		 BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream(inputfile),default_code));
		 String str;
		 int order=1;
		 long p1=0;
		 long p2=0;
		 long p3=0;
		 long p4=0;
		 long p5=0;
		 long p6=0;
		 while((str=br.readLine())!=null){
			 order++;
			 
			 
			 //load the news from file
//			 String date=str;//read date
//			 String title=br.readLine();
//			 String title_nlp=br.readLine();
//			 String body=br.readLine();
//			 String body_nlp=br.readLine();
			 //UCI
			 long t1=System.currentTimeMillis();
			 String title=str;
			 String title_nlp=br.readLine();
			 String body=br.readLine();
			 String body_nlp=br.readLine();
			 String date=br.readLine();//read date
			 if(order<=100000){
				 continue;
			 }
			 if(order>=300000){
				 break;
			 }
			 //System.out.println(order);
			 //save the solr test2 
			
			 long t2=System.currentTimeMillis();
			 SolrInputDocument document = new SolrInputDocument();
			 
			 document.addField("id", order+"");
			 document.addField("title_content", title);
			 document.addField("title", title_nlp);
			 document.addField("body_content", body);
			 document.addField("body", body_nlp);
			 document.addField("date", date);
			
			 UpdateResponse response = SOLR_NEWS.add(document);
			 // Remember to commit your changes!
			 
			 SOLR_NEWS.commit();
			 long t3=System.currentTimeMillis();
			 p1+=t3-t2;
			 // save database
			 News news=new News(order);
			 news.setTitle(title);
			 news.setBody(body);
			 news.setDate(date);
			 newsData.SaveNews(news);
			   //save vector 
			 long t5=System.currentTimeMillis();
			 p2+=t5-t3;
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
			
			 ArrayList<String> symbol_title=new ArrayList<>();
			 symbol_title=(ArrayList<String>) title_attribute.getSymbolList();
			 for(String s:symbol_title){
				 Double tf=title_attribute.getFeature(s);
//				 SolrQuery para = new SolrQuery();
//				 String myq="*:*";
//		    	 para.set("q", myq);
//		    	 para.set("fl","id,idf(\"title\",\""+s+"\")");
//		    	 QueryResponse res = SOLR_XINWEN.query(para);
//		    	 SolrDocumentList list2 = res.getResults();
//		    	 Double idf=Double.parseDouble(list2.get(0).get("idf(\"title\",\""+s+"\")").toString());
		    	 Double idf=dict.getIDF(s);
				 title_attribute.modifyFeature(s, tf*idf);
			 }
			 
			 title_attribute.getSparseVector().getTopK(TopK);
			 title_attribute.getSparseVector().sortKey();
			 news.setAttribute(title_attribute);
			 
			 
			 //body_attribute
			
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
			 
			 ArrayList<String> symbol_body=new ArrayList<>();
			 symbol_body=(ArrayList<String>) body_attribute.getSymbolList();
			 
			 for(String s:symbol_body){
				 Double tf=body_attribute.getFeature(s);
				  Double idf=dict.getIDF(s);
				 //System.out.println(s+":"+tf*idf);
				 body_attribute.modifyFeature(s, tf*idf);
		    	 //System.out.println(tf*idf);
			 }
			 
			 body_attribute.getSparseVector().getTopK(TopK);
			 body_attribute.getSparseVector().sortKey();
			 news.setAttribute(body_attribute);
			 //hotness
			 long t6=System.currentTimeMillis();
			 p3+=t6-t5;
			 Double hotness_score=getHotnessScore(title_nlp);
			 long t9=System.currentTimeMillis();
			 p4+=t9-t6;
   			 Attribute hotness_attribute=new Attribute(
   					 VectorType.DENSE, dict, attributeSet, "hotness");
   			 hotness_attribute.addFeature(hotness_score);
   			 news.setAttribute(hotness_attribute);
   			 //save the vector
   			 
   			 newsData.saveVector(news);
   			 long t10=System.currentTimeMillis();
   			 p5+=t10-t9;
   			 p6+=t10-t1;
			 //System.out.println(order);
			 if(order%50==0){
				 System.out.println(order);
			 }
			 
		 }
		 System.out.println(p1+":"+p2+":"+p3+":"+p4+":"+p5+":"+p6);
	 }
	 private static Double getHotnessScore(String text) throws IOException, SolrServerException{//return score of hotness
		 //System.out.println(text);
		 long p1=0;
		   long p2=0;
		   long p3=0;
		 long t1=System.currentTimeMillis(); 
		 StringTokenizer st=new StringTokenizer(text," ");
		 long t2=System.currentTimeMillis(); 
		 p1=t2-t1;
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
	    	 long t3=System.currentTimeMillis();
			 p2=t3-t2;
	    	QueryResponse response = SOLR_XINWEN.query(parameters);
	    	
	    	long t4=System.currentTimeMillis();
			 p3=t4-t3;
	    	SolrDocumentList list = response.getResults();
	    	
	    	for(int i=0;i<list.size();i++){
	    	   SolrDocument sd=list.get(i);
	    	   //System.out.println(sd);
	    	   sum+=Double.parseDouble(sd.get("score").toString());
	    	}
	    	
	    	//System.out.println(sum);
	    	//System.out.println(sum/list.size());
	    	long t5=System.currentTimeMillis();
			long  p4=t5-t4;
			//System.out.println(p1+":"+p2+":"+p3+":"+p4);
			
	    	return sum/list.size();
	 }
	 public static void SaveDic() throws SQLException{
		 attributeSet.saveIntoDatabase(CON);
		 dict.saveIntoDatabase(CON);
	 }
	 public static void Preprocess() throws SQLException, IOException, SolrServerException{
		 Initialize();
    	 LoadNewsFromFile(news_filename);
		 //LoadNewsFromDatabase(CON);
//		 NewsList newsList=newsData.getNewsList("all");
//		 for(News n: newsList.getNewsList()){
//		    System.out.println(n.getTitle());
//		 }
    	 SaveDic();
	 }
	 public static void LoadNewsFromDatabase(Connection con) throws SQLException{
		    Statement stmt = con.createStatement();
	    	ResultSet result = stmt.executeQuery("select id,date,title,body from content");
	    	while (result.next()){
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
	            		dict, attributeSet,"body",vector_hotness);
	            news.setAttribute(hotness_attribute);
	            newsData.setNews(news);
	        }
	    	
	 }
	 
     public static void main(String[] args) throws IOException, SolrServerException, SQLException{
    	 // this project test the news which are from file/port 
    	 // save the solr, save the database, load from database
    	 // test the ranker
    	 Preprocess();
    	 
    	 
     };
}
