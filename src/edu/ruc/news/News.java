package edu.ruc.news;

import java.util.ArrayList;
import java.util.List;
import edu.ruc.data.*;

public class News {

//	News : 一条新闻的基本组成和操作。
//  主要的接口就是根据标签名字取attribute
//
		
    
	public long id;
	public ArrayList<Attribute> array;
	public String date;
	public String category;
	public String title;
	public String body;

//  构造函数
 // 唯一一种构造函数，指定一个id作为唯一标识
	public News(long id){
		this.id=id;
		array=new ArrayList<Attribute>();
	}
	
//  方法
	
	//得到ID
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
	
	//设置属性 如果存在 就覆盖 返回fasle
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
	
	//删除属性 不存在返回false
	public boolean removeAttribute(String attribute_name){
		for(Attribute a:array){
			if(a. getAttributeName().equals(attribute_name)){
				array.remove(a);
				
				return true;
			}
		}
		return false;
	}
	
	//返回属性数量
	public int getLengthAttribute(){
		return array.size();
	}
	
	//根据属性名字返回属性
	public Attribute getAttribute(String attribute_name){
		
		for(Attribute a:array){
			if(a. getAttributeName().equals(attribute_name)){
				
				
				return a;
			}
		}
		System.out.println("attribute can not find");
		return null;
	}
	
	
	
	//返回属性列表
	public List<Attribute> getAttributeList(){
		return array;
	}
	
	//返回属性名字列表
	public List<String> getAttributeListName(){
		ArrayList<String> arrayName=new ArrayList<String>();
		for(Attribute a:array){
			arrayName.add(a.getAttributeName());
		}
		return arrayName;
	}
	
	//判断属性是否存在
	public boolean isExistAttributeByName(String attribute_name){
		for(Attribute a:array){
			if(a. getAttributeName().equals(attribute_name)){		
				return true;
			}
		}
		return false;
	}
	//打印
	public String printAttribute(){
		return "";
	}
	

	

	
}
