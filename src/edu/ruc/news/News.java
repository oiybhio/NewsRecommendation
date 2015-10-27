package edu.ruc.news;

import java.util.ArrayList;
import java.util.List;
import edu.ruc.data.*;

public class News {

//	
//  
//
		
    
	public long id;
	public ArrayList<Attribute> array;
	public String date;
	public String category;
	public String title;
	public String body;
	public String oldarticle_id;
	public String headline;
	public String headline_nlp;

//  
 // 
	public News(long id){
		this.id=id;
		array=new ArrayList<Attribute>();
	}
	
// 
	
	//
	public void setHeadline(String headline){
		this.headline=headline;
	}
	public String getHeadline(){
		return this.headline;
	}
	public void setHeadline_nlp(String headline_nlp){
		this.headline_nlp=headline_nlp;
	}
	public String getHeadline_nlp(){
		return this.headline_nlp;
	}
	public void setOldarticle_id(String oldarticle_id){
		this.oldarticle_id=oldarticle_id;
	}
	public String getOldarticle_id(){
		return this.oldarticle_id;
	}
	public void setDate(String date){
		this.date=date;
	}
	public String getDate(){
		return this.date;
	}
	public void setCategory(String category){
		this.category=category;
	}
	public String getCategory(){
		return this.category;
	}
	public void setTitle(String title){
		this.title=title;
	}
	public String getTitle(){
		return this.title;
	}
	public void setBody(String body){
		this.body=body;
	}
	public String getBody(){
		return this.body;
	}
	
	
	public long getID(){
		return this.id;
	}
	
	//
	public boolean setAttribute(Attribute attribute){
		for(Attribute a:array){
			if(a. getAttributeName().equals(attribute.getAttributeName())){
				array.remove(a);
				array.add(attribute);
				return false;
			}
		}
		array.add(attribute);
		return true;
	}
	
	//
	public boolean removeAttribute(String attribute_name){
		for(Attribute a:array){
			if(a. getAttributeName().equals(attribute_name)){
				array.remove(a);
				
				return true;
			}
		}
		return false;
	}
	
	//
	public int getLengthAttribute(){
		return array.size();
	}
	
	//
	public Attribute getAttribute(String attribute_name){
		
		for(Attribute a:array){
			if(a. getAttributeName().equals(attribute_name)){
				
				
				return a;
			}
		}
		System.out.println("attribute can not find");
		return null;
	}
	
	
	
	//
	public List<Attribute> getAttributeList(){
		return array;
	}
	
	//
	public List<String> getAttributeListName(){
		ArrayList<String> arrayName=new ArrayList<String>();
		for(Attribute a:array){
			arrayName.add(a.getAttributeName());
		}
		return arrayName;
	}
	
	//
	public boolean isExistAttributeByName(String attribute_name){
		for(Attribute a:array){
			if(a. getAttributeName().equals(attribute_name)){		
				return true;
			}
		}
		return false;
	}
	//
	public void display() {
		System.out.println(title);
		for(Attribute attribute:array) {
			attribute.display();
		}
	}
	
	public void getTopK(int K){
		for(Attribute a:array){
			if(a.getAttributeName()=="title"||a.getAttributeName()=="body"){
			//	a.display();
			//	a.getSparseVector().sortKey();
			//	a.display();
				a.getSparseVector().getTopK(K);
			//	a.display();
			//	System.out.println("**************************");
			}
		}
	}
	
	

	
}
