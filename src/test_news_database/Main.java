package test_news_database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
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

public class Main {
	 private static NewsDatabase newsData;
	 private static String default_code="utf-8";//the default code
	 private static Dictionary dict;// the dictionary of features
	 private static Alphabet attributeSet;// the alphabet of attribute name
	 private static final String news_filename=
			 "src/news_data/dat0_example.txt";//the location of news_file 
	 private static String SOLR_NEWS_urlString = "http://localhost:8983/solr/test2";
	 private static String SOLR_Xinwen_urlString = "http://183.174.228.20:8983/solr/Xinhua";
 	 private static int TopK;
	 private static SolrClient SOLR_NEWS ;
 	 private static SolrClient SOLR_XINWEN ;
 	 private static Connection CON;
	 public static void Initialize() throws SQLException{
		 TopK=5;
		 SOLR_NEWS = new HttpSolrClient(SOLR_NEWS_urlString);
		 SOLR_XINWEN=new HttpSolrClient(SOLR_Xinwen_urlString);
		 dict=new Dictionary();
		 attributeSet=new Alphabet();
		 InitializeDatabase();
		 newsData=new NewsDatabase();
		 newsData.setSolr_news(SOLR_NEWS);
		 newsData.setSolr_xinwen(SOLR_XINWEN);
		 newsData.setConnection(CON);
//		 attributeSet.loadFromDatabase(CON);
//		 dict.loadFromDatabase(CON);
	 }
	 public static void InitializeDatabase(){
	     try{   
    		//加载MySql的驱动类   
    		    Class.forName("com.mysql.jdbc.Driver") ;   
    	 }catch(ClassNotFoundException e){   
    	   System.out.println("找不到驱动程序类 ，加载驱动失败！");   
    	   e.printStackTrace() ;   
         } 
    	 String url = "jdbc:mysql://localhost:3306/test?"
    	 		+ "useUnicode=true&characterEncoding=UTF-8" ;    
         String username = "root" ;   
         String password = "zwh920617" ;   
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
		 while((str=br.readLine())!=null){
//			 if(order>1){
//				 break;
//			 }
			 //load the news from file
			 String date=str;//read date
			 String title=br.readLine();
			 String title_nlp=br.readLine();
			 String body=br.readLine();
			 String body_nlp=br.readLine();
			 
			 //save the solr test2 
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
			 // input database
			 News news=new News(order);
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
			 ArrayList<String> symbol_title=new ArrayList<>();
			 symbol_title=(ArrayList<String>) title_attribute.getSymbolList();
			 for(String s:symbol_title){
				 Double tf=title_attribute.getFeature(s);
				 SolrQuery para = new SolrQuery();
				 String myq="*:*";
		    	 para.set("q", myq);
		    	 para.set("fl","id,idf(\"title\",\""+s+"\")");
		    	 QueryResponse res = SOLR_XINWEN.query(para);
		    	 SolrDocumentList list2 = res.getResults();
		    	 Double idf=Double.parseDouble(list2.get(0).get("idf(\"title\",\""+s+"\")").toString());
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
				 SolrQuery para = new SolrQuery();
				 String myq="*:*";
		    	 para.set("q", myq);
		    	 para.set("fl","id,idf(\"body\",\""+s+"\")");
		    	 QueryResponse res = SOLR_XINWEN.query(para);
		    	 SolrDocumentList list2 = res.getResults();
		    	 Double idf=Double.parseDouble(list2.get(0).get("idf(\"body\",\""+s+"\")").toString());
		    	 body_attribute.modifyFeature(s, tf*idf);
		    	 //System.out.println(tf*idf);
			 }
			 
			 body_attribute.getSparseVector().getTopK(TopK);
			 body_attribute.getSparseVector().sortKey();
			 news.setAttribute(body_attribute);
			 //hotness
			 Double hotness_score=getHotnessScore(title_nlp);
   			 Attribute hotness_attribute=new Attribute(
   					 VectorType.DENSE, dict, attributeSet, "hotness");
   			 hotness_attribute.addFeature(hotness_score);
   			 news.setAttribute(hotness_attribute);
   			 //save the vector
   			 newsData.saveVector(news);
			 order++;
			 System.out.println(order);
		 }
	 }
	 private static Double getHotnessScore(String text) throws IOException, SolrServerException{//return score of hotness
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
	    	QueryResponse response = SOLR_XINWEN.query(parameters);
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
	 public static void SaveDic() throws SQLException{
		 attributeSet.saveIntoDatabase(CON);
		 dict.saveIntoDatabase(CON);
	 }
	
     public static void main(String[] args) throws IOException, SolrServerException, SQLException{
    	 // this project test the news which are from file/port 
    	 // save the solr, save the database, load from database
    	 // test the ranker
    	 Initialize();
    	 LoadNewsFromFile(news_filename);
    	 SaveDic();
     }
}
