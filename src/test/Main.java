package test;

import edu.ruc.WebService.BehaveType;
import edu.ruc.data.*;
import edu.ruc.data.Dictionary;
import edu.ruc.news.*;
import edu.ruc.user.*;
import edu.ruc.ranker.*;
import edu.ruc.database.*;
import edu.ruc.log.*;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

public class Main {
	 private static NewsDatabase newsData;//the database of news
	 private static UserDatabase userData;//the database of user
	 private static OnlineUsers users;
	 private static ResultStore resultStore;
	 private static Dictionary dict;// the dictionary of features
	 private static Alphabet attributeSet;// the alphabet of attribute name
	 private static final String news_filename="src/news_data/dat0_example.txt";//the location of news_file 
	 private static final String user_filename="";//the location of user_file
	 private static final String hotness_url="";//the url of solr
	 private static final String print_filename="";
	 private static String default_code="utf-8";
	 private static long num_news;
	 private static String urlString = "http://183.174.228.20:8983/solr/Xinhua";
 	 private static SolrClient SORL ;
 	 private static final int TOPK=5;
 	 private static Connection CON;
	 
	 private static void Initialize(){//Initialize
		 //initialize variables
		 SORL = new HttpSolrClient(urlString);
		 InitializeDatabase();
		 num_news=1;
		 newsData=new NewsDatabase();
		 newsData.setSolr(SORL);
		 newsData.setConnection(CON);
		 userData=new UserDatabase();
		 resultStore=new ResultStore();
		 dict=new Dictionary();
		 attributeSet=new Alphabet();
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
	         String password = "zwh920617" ;   
	         try{   
	               CON=    
	               DriverManager.getConnection(url , username , password ) ;   
	         }catch(SQLException se){   
	            System.out.println("数据库连接失败！");   
	            se.printStackTrace() ;   
	         }  
	 }
	 private static void Ranker() throws IOException, SolrServerException, SQLException{//for every user, rank the newslist
		 //System.out.println(users.size());
		 /*List<News> array = newsData.getNewsList("all").getNewsList();
		 for(News news:array) {
			 news.display();
		 }*/
		 
		 MakeRandomHashmap MakeRandomHashmap = new MakeRandomHashmap();
		 
		 for(int i=0;i<users.size();i++) {
			 User user = users.getUserAt(i);
			 Ranker ranker = new Ranker();
			 NewsList temp = ranker.query(resultStore, user, "all", newsData.getNewsListbyTopic(
					 MakeRandomHashmap.getRandomHashmap(),dict,attributeSet,SORL).getNewsList(), 10);
			 List<News> ans = temp.getNewsList();
			 System.out.println("User ID: " + user.getUid());
			 for(int j=0;j<ans.size();j++)
				 System.out.println(ans.get(j).getTitle());
		 }
	 }
	 private static News CreateNews(){
		 return new News(num_news++);
	 }
	 
	 private static void CreateUsers() throws IOException{
		 MakeRandomUser makeRandomUser = new MakeRandomUser();
		 int userNum = 2;
		 int TopicNum = 5;
		 int WordNum = 3;
		 users = makeRandomUser.getRandomUser(userNum, TopicNum, WordNum, dict, attributeSet);
		 
         String[] features = new String[]{"上涨","涨幅","姚明","世界杯","骗","杀"};
		 // users = new OnlineUsers();
		 for(int i=0;i<3;i++) {
			 User u = new User(userNum+i+1);
			 Attribute a = new Attribute(VectorType.SPARSE,dict,attributeSet,"title");
			 a.addFeature(features[2*i],1);
			 a.addFeature(features[2*i+1],1);
			 u.pushBack(a);
			 users.pushBack(u);
		 }
		 //Create the fourth user
		 User u = new User(userNum+4);
		 Attribute a = new Attribute(VectorType.SPARSE,dict,attributeSet,"title");
		 a.addFeature("增长",1);
		 a.addFeature("风险",1);
		 a.addFeature("男篮",1);
		 a.addFeature("联赛",1);
		 Attribute b = new Attribute(VectorType.SPARSE,dict,attributeSet,"body");
		 b.addFeature("贝克汉姆",1);
		 b.addFeature("曼联",1);
		 u.pushBack(a);
		 u.pushBack(b);
		 users.pushBack(u);
		 
		 
		 System.out.println();
		 users.display();
		 System.out.println();
	//	 users.getUserAt(0).display();
	//	 createLog();
     }
	 
	 public static void createLog(){
		 long nid = 33;
		 Behavior behavior = new Behavior(3, nid, BehaveType.Click.ordinal(), 10);
		 behavior.BehaveAnalyse(users,newsData.getNews(nid));
		 // newsData.getNews(nid).display();
		 behavior.UpdateUserProfile();
		 // users.getUserAt(2).display();
	 }
	 private static void SaveDic() throws SQLException{
		 dict.saveIntoDatabase(CON);
	 }
	 
	 private static void LoadDic() throws SQLException{
		 dict.loadFromDatabase(CON);
	 }
	private static void Preprocess() throws IOException, SolrServerException, SQLException{//the preprocess 
  //       LoadDic();
		 InputNewsFile(news_filename,default_code);
		 newsData.getNews(1).display();
    	 // InputUserFile(user_filename, default_code);
//		 SaveDic();
		 CreateUsers();
		 
    	 
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
	 
	 
	 
	 private static void InputNewsFile(String filename,String code) throws IOException, SolrServerException, SQLException{
		 //read newsdata that has been tokenized
		 //add the dictionary
		 BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream(filename),code));
		 String str;
		 int order=1;
		 while((str=br.readLine())!=null){
//			 if(order>=2){
//				 break;
//			 }
			 //System.out.println(order);
			 if(order>=10){
				 break;
			 }
//			 System.out.println(order);
			 String date=str;//read date
			 String title=br.readLine();
			 String title_nlp=br.readLine();
			 String body=br.readLine();
			 String body_nlp=br.readLine();
			 
//			 String news_class=br.readLine();
			 
			 //construct news
			 News news=CreateNews();
			 news.setTitle(title);
			 news.setBody(body);
//			 news.setCategory(news_class);
			 news.setDate(date);
			 //title attribute
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
			 title_attribute.getTopK(TOPK);
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
			 body_attribute.getTopK(TOPK);
			 body_attribute.getSparseVector().sortKey();
			 news.setAttribute(body_attribute);
			 // add attributes,add title,body,news_class
			 Double hotness_score=getHotnessScore(title_nlp);
			 Attribute hotness_attribute=new Attribute(
					 VectorType.DENSE, dict, attributeSet, "hotness");
			 hotness_attribute.addFeature(hotness_score);
			 news.setAttribute(hotness_attribute);
			 // add the newsdatabase
			 newsData.setNews(news);
//			 newsData.saveVector(news
//					 );
			 order++;
		 }
	 }
     
     private static void Load_feature(){// load the feature of news and user
    	 
     }
     public static void main(String[] args) throws IOException, SolrServerException, SQLException{
    	 Initialize();
    	 //preprocess do above actions
    	 
    	 //InputNewsFile(news_filename,default_code);
    	 //InputUserFile(user_filename, default_code);
    	 
    	 //doHotness();
    	 Preprocess();
    	 
    	 //
    	 Ranker();
    	// System.out.println("****************************");
    	// createLog();
    	// System.out.println("****************************");
    	// Ranker();
    	 //print results
    	 //Print();
    	 
     }
}
