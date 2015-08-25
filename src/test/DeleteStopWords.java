package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class DeleteStopWords {
    public static void main() throws IOException{
    	BufferedReader br=new BufferedReader(new 
				 InputStreamReader(new FileInputStream("stopword.txt"),"utf-8"));
    	BufferedReader br2=new BufferedReader(new 
				 InputStreamReader(new FileInputStream("news_data_nlp.txt"),"utf-8"));
    	BufferedWriter bw=new BufferedWriter(new 
				 OutputStreamWriter(new FileOutputStream("nlp.txt"),"utf-8"));
		 HashSet<String> set=new HashSet<>();
		 
    	 String str;
		 while((str=br.readLine())!=null){
			 set.add(str);
		 }
		 br.close();
		 int order=1;
		 while((str=br2.readLine())!=null){
			 if(order%8==5||order%8==7){
				 StringTokenizer st=new StringTokenizer(str, " ");
				 while(st.hasMoreTokens()){
					 String token=st.nextToken();
					 if(set.contains(token)){
						 System.out.println(order+":"+token);
					 }else{
						 bw.write(token+" ");
					 }
				 }
				 bw.newLine();
			 }else{
				 bw.write(str);
				 bw.newLine();
			 }
		 }
    }
}
