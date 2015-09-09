package test_news_database;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.print.attribute.HashAttributeSet;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocumentList;


public class Caculate_idf {
	 //private static HashMap<String, String> map=new HashMap<String, Double>();
    private static	SolrClient SOLR ;
    private static final long All_doc=10501811;
	public static void SaveMap(String filename,HashMap<String,String> map) throws IOException{
		 BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(
	   			 new FileOutputStream(filename),"utf-8"));
		 Iterator iter=map.entrySet().iterator();
		 int order=0;
		 while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next(); 
			String key = entry.getKey().toString();
			String val = entry.getValue().toString();
			bw.write(key+" ");
			bw.write(val+"\t");
			order++;
			if(order%100==0){
				bw.newLine();
			}
		}
		bw.close(); 
	   	 
	 }
	 public static void LoadMap(String filename,HashMap<String,String> map) throws IOException{
		 BufferedReader br=new BufferedReader(
	   			 new InputStreamReader(new FileInputStream(filename),"utf-8"));
	   	 String str;
	   	 int order=0;
	   	 while((str=br.readLine())!=null){
	   		StringTokenizer st=new StringTokenizer(str,"\t");
	   		while(st.hasMoreTokens()){
	   			StringTokenizer sst=new StringTokenizer(st.nextToken()," ");
	   			String key=sst.nextToken();
	   			String val=sst.nextToken();
	   			map.put(key, val);
	   		}
	   	 }
	   	 br.close();
	   		 
	 }
	 public static void Caculate(String filename,HashMap<String,String> map) throws IOException{
		 BufferedReader br=new BufferedReader(
	   			 new InputStreamReader(new FileInputStream(filename),"utf-8"));
	   	 String str;
	   	 int order=0;
	   	 int Total=0;
	   	 int Same=0;
	   	 while((str=br.readLine())!=null){
	   		 if(order%1000==0){
	   			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				 System.out.println(df2.format(new Date())+"//"+order);// new Date()为获取当前系统时间
				
	   		    //System.out.println(order+":"+Total+":"+Same);
	   		 }
	   		 //System.out.println(order+":"+Total+":"+Same);
	   		 order++;
//	   		 if(order<=33000){
//	   			 continue;
//	   		 }
	   		 
	   		 br.readLine();
	   		 String title=br.readLine();
	   		 //System.out.println(order+":"+title);
//	   		 StringTokenizer st=new StringTokenizer(title," ");
////	   		 if(order<=1095000){
////	   			 continue;
////	   		 }
//	   		 while(st.hasMoreTokens()){
//	   			 String token=st.nextToken();
//	   			 
//	   			 if(!(map.containsKey(token))){
//	   				 //Total++;
//	   				 //System.out.println(order+":"+token);
//	   				 SolrQuery para = new SolrQuery();
//	   				 //String query=ClientUtils.escapeQueryChars(token);
//	   				 String myq="title:"+token;
//	   				 
//					
//	   		    	 para.set("q", myq);
//	   		    	
//	   		    	 QueryResponse res;
//					try {
//						res = SOLR.query(para);
//						SolrDocumentList list2 = res.getResults();
//		   		    	 long num_docfreq=list2.getNumFound();
//		   		    	 String idf=Math.log(All_doc*1.0/(num_docfreq+1))+"";
//		   		    	 map.put(token,idf);
//					} catch (SolrServerException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}finally{
//						continue;
//					}
//	   		    	 
//	   		    	 
//	   			 }else{
//	   				 //Same++;
//	   				//System.out.println(Total+":"+Same);
//	   			 }
//	   				 
//	   			 
//	   		 }
	   		 br.readLine();
	   		 
	   		 String body=br.readLine();
	   		 StringTokenizer st=new StringTokenizer(body," ");
	   		 int sum=0;
	   		 while(st.hasMoreTokens()){
	   			 
	   			 String token=st.nextToken();
	   			 sum++;
	   			 if(sum>=30){
	   				 break;
	   			 }
	   			 if(!(map.containsKey(token))){
	   				 Total++;
	   				 //System.out.println(order+":"+sum);
	   				 
	   				String query=ClientUtils.escapeQueryChars(token);
	   				 String myq="title:"+query;
	   				SolrQuery para = new SolrQuery();
					
	   		    	 para.set("q", myq);
	   		    	
	   		    	 QueryResponse res;
					try {
						res = SOLR.query(para);
					
	   		    	 SolrDocumentList list2 = res.getResults();
	   		    	 long num_docfreq=list2.getNumFound();
	   		    	 String idf=Math.log(All_doc*1.0/(num_docfreq+1))+"";
	   		    	 map.put(token,idf);
					} catch (SolrServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						continue;
					}
	   		    	
	   			 }else{
	   				 //Same++;
	   				 //System.out.println(Total+":"+Same);
	   			 }
	   				 
	   			 
	   		 }
	   		 
	   	 }
	   	 br.close();
	 }
     public static void main(String[] args) throws IOException, SolrServerException{
    	 SOLR=new HttpSolrClient("http://localhost:8983/solr/Xinhua");
    	 HashMap<String,String> map=new HashMap<String, String>();
    	 for(int i=95;i<=100;i++){
    		 SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			 System.out.println(df2.format(new Date())+"//"+i+"start");// new Date()为获取当前系统时间
				
    		 LoadMap("D://users/wenhui_zhang/xinwen_data/idf/"+(i-1)+"all.txt", map);
    		 Caculate("D://users/wenhui_zhang/xinwen_data/nlp/dat"+i+".txt", map);
    		 SaveMap("D://users/wenhui_zhang/xinwen_data/idf/"+i+"all.txt", map);
    	 }
    	 
    
      
     }
}
