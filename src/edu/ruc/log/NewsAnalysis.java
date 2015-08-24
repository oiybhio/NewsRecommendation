package edu.ruc.log;

import edu.ruc.news.*;
import java.util.*;
import edu.ruc.data.*;
import edu.ruc.news.*;

public class NewsAnalysis {
	private long nid;
	private News news;
	
	private int length;
	private List<Attribute> arrayList;
	/**
     * Constructor for a analysis.
     */
	public NewsAnalysis(News news, long nid) {
		this.nid = nid;
		this.news = news;
		length = 0;
		arrayList = new ArrayList<Attribute>();
		String[] attributeName = new String[]{"title","body"};
		for(int i=0;i<attributeName.length;i++) {
			if(news.isExistAttributeByName(attributeName[i])) {
				length++;
			//	System.out.println(news.getAttribute(attributeName[i]).vectorToString());
				arrayList.add(news.getAttribute(attributeName[i]));
			}
		}
	}

	/**
     * Get news.
     */
	public News getNews() {
		return news;
	}
	/**
     * Get attributes length.
     */
	public int size() {
		return length;
	}
	/**
     * Get uid.
     */
	public long getNid() {
		return nid;
	}
	/**
     * Get attributes.
     */
	public List<Attribute> getAttributes() {
		return arrayList;
	}
}
