package test;

import edu.ruc.WebService.BehaveType;
import edu.ruc.WebService.DealMsg;
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

import net.sf.json.JSONObject;

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
	 private static final String user_filename="src/user_data/json.txt";//the location of user_file
	 private static final String idf_filename="D://users/wenhui_zhang/xinwen_data/idf/30all.txt";
	 
	 private static final String hotness_url="";//the url of solr
	 private static final String print_filename="";
	 private static final String log_filename="src/log/log"+System.currentTimeMillis()+".txt";
	 private static PrintWriter pw_log;
	 
	 private static String default_code="utf-8";
	 private static long num_news;
	 private static String SOLR_NEWSurlString = "http://183.174.228.20:8983/solr/LTRNews";
	 private static String SOLR_xinwenSurlString = "http://183.174.228.20:8983/solr/xinhua_news";
	 private static String SOLR_weiboSurlString = "http://183.174.228.20:8983/solr/weibo_hotness";
	 private static SolrClient SOLR_NEWS ;
 	 private static SolrClient SOLR_XINWEN ;
 	 private static SolrClient SOLR_WEIBO ;
 	
 	 private static final int TOPK=5;
 	 private static Connection CON;
	 
	 public static void Initialize() throws IOException{//Initialize
		 //initialize variables
		 SOLR_NEWS = new HttpSolrClient(SOLR_NEWSurlString);
		 SOLR_XINWEN=new HttpSolrClient(SOLR_xinwenSurlString);
		 SOLR_WEIBO =new HttpSolrClient(SOLR_weiboSurlString);
		 InitializeDatabase();
		 num_news=1;
		 newsData=new NewsDatabase();
		 newsData.setSolr_news(SOLR_NEWS);
		 newsData.setSolr_xinwen(SOLR_XINWEN);
		 newsData.setSolr_weibo(SOLR_WEIBO);
		 newsData.setConnection(CON);
		
		 userData=new UserDatabase();
		 userData.setConnection(CON);
		 resultStore=new ResultStore();
		 dict=new Dictionary();		
		 dict.loadIDF(LoadIDFfile(idf_filename));
		 attributeSet=new Alphabet();
		 users = new OnlineUsers();
		 InitLogfile();
	 }
	 
	 public static void InitLogfile() throws IOException{
		 pw_log = new PrintWriter(new FileWriter(log_filename));
	 }
	 public static HashMap<String,Double> LoadIDFfile(String filename) throws IOException{
		 BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream(filename),default_code));
		 HashMap<String,Double> map=new HashMap<String, Double>();
		 String str;
		 int order=0;
	   	 while((str=br.readLine())!=null){
	   		StringTokenizer st=new StringTokenizer(str,"\t");
	   		while(st.hasMoreTokens()){
	   			StringTokenizer sst=new StringTokenizer(st.nextToken()," ");
	   			String key=sst.nextToken();
	   			Double val=Double.parseDouble(sst.nextToken());
	   			map.put(key, val);
	   		}
	   	 }
	   	 
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
	 private static void Ranker() throws IOException, SolrServerException, SQLException{//for every user, rank the newslist
		 //System.out.println(users.size());
		 /*List<News> array = newsData.getNewsList("all").getNewsList();
		 for(News news:array) {
			 news.display();
		 }*/
		 
		 MakeRandomHashmap MakeRandomHashmap = new MakeRandomHashmap();
		 
		 for(int i=0;i<users.size();i++) {
			 User user = users.getUserAt(i);
			 //System.out.println("---------pp-------");
		//	 user.display();
		//	 user.getHashmap(dict);
			 Ranker ranker = new Ranker();
//			 ArrayList<News> array=newsData.getNewsListbyTopic(
//					 user.getHashmap(dict),dict,attributeSet).getNewsList();
////			 for(News n:array){
//				 System.out.println(n.getTitle());
//			 }
//			 NewsList temp = ranker.query(resultStore, user, "all", newsData.getNewsListbyTopic(
//					 user.getHashmap(dict,attributeSet),dict,attributeSet).getNewsList(), 10);
			 NewsList temp = ranker.query(resultStore, user, "all", newsData.getNewsListbyTopic(
					 user.getHashmap(dict,attributeSet),"headline",dict,attributeSet).getNewsList(), RankerType.VSM, 10);
			 
			 List<News> ans = temp.getNewsList();
			 //System.out.println("User ID: " + user.getUid());
//			 for(int j=0;j<ans.size();j++)
//				 System.out.println(ans.get(j).getTitle());
		 }
	 }
	 private static News CreateNews(){
		 return new News(num_news++);
	 }
	 
	 private static void CreateUsers() throws IOException, SQLException{
		 /*MakeRandomUser makeRandomUser = new MakeRandomUser();
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
		 b.addFeature("台湾", 1);
		 u.pushBack(a);
		 u.pushBack(b);
		 users.pushBack(u);
		 
		 
		 System.out.println();
		 users.display();
		 System.out.println();*/
		 
	//	 users.getUserAt(0).display();
	//	 createLog();
		 //userData.saveVectorALL(users, "userProfile_temp");
		 //User u1 = userData.getVector(3, dict, attributeSet,"userProfile_temp");
//		 System.out.println("**************");
//		 u1.display();
		for(int i=1;i<7;i++) {
			 User u1 = userData.getVector(i, dict, attributeSet,"userProfile_temp");
		//	 u1.display();
			 if(u1!=null)
				 users.pushBack(u1);
		 }
		users.display();
     }
	 
	 /*public static void createLog(){
		 long nid = 33;
		 Behavior behavior = new Behavior(3, nid, BehaveType.Click.ordinal(), 10);
		 behavior.BehaveAnalyse(users,newsData.getNews(nid));
		 // newsData.getNews(nid).display();
		 behavior.UpdateUserProfile();
		 // users.getUserAt(2).display();
	 }*/
	  public static void testUpdate() throws IOException, SQLException {
		/*BufferedReader br = new BufferedReader(new FileReader(user_filename));
		String jsonString;
		while((jsonString=br.readLine())!=null){
		//	System.out.println("ehfiehfi");
			DealMsg dm = new DealMsg(jsonString);
			dm.start();
			dm.getDataAnalysis().Store(CON);
			dm.getDataAnalysis().BehaveAnalyse(users.findUser(dm.getDataAnalysis().getUid()), newsData.getNews(dm.getDataAnalysis().getNid()));
			dm.getDataAnalysis().UpdateUserProfile(pw_log);
			dm.getDataAnalysis().Store(CON);
			System.out.println("pppppppppppp"+dm.getDataAnalysis().getNid());
			newsData.getNews(dm.getDataAnalysis().getNid()).display();;
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		//	users.findUser(dm.getDataAnalysis().getUid()).display();
			userData.saveVector(users.findUser(dm.getDataAnalysis().getUid()), "userProfile_temp");
		}*/
		  resultStore.clear();
		  users.findUser(3).UpdateAll(CON, users, newsData, pw_log);
	  }
	  public static String testWebservice(String jsonString) throws Exception {	
/*		  BufferedReader br = new BufferedReader(new FileReader(user_filename));
		  String jsonString;
		  while((jsonString=br.readLine())!=null){
			  DealMsg dm = new DealMsg(jsonString);
			  dm.start();
			  dm.getDataAnalysis().deal(users,newsData, resultStore, dict, attributeSet, CON);
		  }
*/	 	DealMsg dm = new DealMsg(jsonString);
  //      System.out.println(jsonString);
		dm.start();
		String s = dm.getDataAnalysis().deal(users,newsData, resultStore, dict, attributeSet, CON);
		  return s;
	  }
	  public static List<News> testRecommend(int N,User u) {
		  Ranker ranker = new Ranker();
		  NewsList temp = ranker.query(resultStore, u, "all", newsData.getNewsListbyTopic(
					 u.getHashmap(dict,attributeSet),"headline",dict,attributeSet).getNewsList(), RankerType.VSM, N);
		  List<News> ans = temp.getNewsList();
		  return ans;
	  }
	 private static void SaveDic() throws SQLException{
		 dict.saveIntoDatabase(CON);
		 attributeSet.saveIntoDatabase(CON);
	 }
	 
	 private static void LoadDic() throws SQLException{
		 dict.loadFromDatabase(CON);
		 attributeSet.loadFromDatabase(CON);
	 }
	public static void Preprocess() throws IOException, SolrServerException, SQLException{//the preprocess 
		 LoadDic();
		 long t1=System.currentTimeMillis();
         newsData.LoadNewsFromDatabaseLTR(dict, attributeSet);
         long t2=System.currentTimeMillis();
         System.out.println("the time of starting database is:"+(t2-t1));
		 //InputNewsFile(news_filename,default_code);
	//	 newsData.getNews(1).display();
    	 // InputUserFile(user_filename, default_code);
		 
		 CreateUsers();
		 
		 SaveDic();
    	 
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
			 title_attribute.getSparseVector().sortKey();
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
     
     public static void close(){
    	 pw_log.close();
     }
     public static void main(String[] args) throws Exception {
    	 Initialize();

    	 Preprocess();
    //	users.findUser(3).display();
    //	 testWebservice();
    //	users.findUser(3).display();
		 
    	 close();
     }
}
