# news包
news包的主要功能是封装有关新闻的接口。主要有两个类。
News : 一条新闻的基本组成和操作。
NewsList:存储多条新闻，用于查询结果、排序。

##News 类
一条新闻的基本组成和操作。

###内部变量
全部为private 将变量封装
long id :primary key
String title: 
String body:
String author;
String/SimpleDateformat Date:日期
String class:类别
ArrayList<Stirng> Entity:实体  (可以将实体变为attribute)
Double hotness_score;
ArrayList<Attribute> attribute 

###构造函数
public News(long id): 唯一一种构造函数，指定一个id作为唯一标识

###内部变量
全部为private 将变量封装
long id :primary key
String title: 
String body:
String author;
String/SimpleDateformat Date:日期
String class:类别
ArrayList<Stirng> Entity:实体  (可以将实体变为attribute)
Double hotness_score;
ArrayList<Attribute> attribute 

###方法 public 

long getID();
String getTitle();
String getBody();
String getAuthor();
String getDate();
String getClass();
String[] getEntity();
Double getHotness_score();
Attribute getAttribute(String name):根据attribute的name返回attribute

void setTitle(String str)
void setBody(String str)
void setAuthor(String str)
void setDate(String str)
void setClass(String str)
void setEntity(String[] str)
void setHotness-score(double score)
void addEntity(String str)
void addAttribute(Attribute attribute)

boolean removeEntity(String entity):删除一个实体 找不到实体返回false
boolean removeAttribute(String name)：删除一个特征 找不到名字返回false

##NewsList 类
存储多条新闻

###内部变量 private
String name:可有可无 
ArrayList<News> array:news的线性存储
HashMap<long,Integer> map:哈希存储位置

###构造函数
public NewsList();
public NewsList(String name);
public NewsList(ArrayList<News> array)
public NresList(String name ,ArrayList<News> array)

###方法

String getName();
ArrayList getList();
News getNews(long id);
NewsList getSubNews(long[] id)

void setName(String name)
void addNews(News news)
void addNewsList(NewsList newsList)

void UpdateMap();

boolean removeNews(long id)
boolean removeNews(News news)
boolean removeNewsList(NewsList newslist)
boolean removeNewsList(long[] id)