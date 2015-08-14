package edu.ruc.log;

import java.util.*;
import edu.ruc.data.*;

public class NewsAnalysis {
	private long nid;
	private News news;
	/**
     * Constructor for a analysis.
     */
	public NewsAnalysis(News news, long nid) {
		this.nid = nid;
		this.news = news;
	}

	/**
     * Get news.
     */
	public News getNews() {
		return news;
	}
}
