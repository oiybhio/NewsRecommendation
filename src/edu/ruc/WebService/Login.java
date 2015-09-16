package edu.ruc.WebService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import test.MakeRandomHashmap;
import edu.ruc.data.Alphabet;
import edu.ruc.data.Dictionary;
import edu.ruc.database.NewsDatabase;
import edu.ruc.news.News;
import edu.ruc.news.NewsList;
import edu.ruc.ranker.Ranker;
import edu.ruc.ranker.ResultStore;
import edu.ruc.user.OnlineUsers;
import edu.ruc.user.User;
import net.sf.json.*;
public class Login extends DataAnalysis{
	private long UserID;
	private String Password;
	public Login(JSONObject json) {
		UserID = json.getLong("UserID");
		Password = json.getString("Password");
	}
	/**
     * Get uid.
     */
	public long getUid() {
		return UserID;
	}
	public String deal(OnlineUsers users,NewsDatabase newsData, ResultStore resultStore, 
			Dictionary dict,Alphabet attributeSet, Connection con)throws Exception {
			
		 MakeRandomHashmap MakeRandomHashmap = new MakeRandomHashmap();
	 	 User user = users.findUser(UserID);
		 System.out.println("---------pp-------");
		 Ranker ranker = new Ranker();
		 NewsList temp = ranker.query(resultStore, user, "all", newsData.getNewsListbyTopic(
				 user.getHashmap(dict),dict,attributeSet).getNewsList(), 10);
		 List<News> ans = temp.getNewsList();
		 JSONObject json = new JSONObject();
		 System.out.println("User ID: " + user.getUid());
		 for(int j=0;j<ans.size();j++)
			 json.put("News"+j, ans.get(j).getTitle());
		 System.out.println(json.toString());
		return json.toString();
	}
}
