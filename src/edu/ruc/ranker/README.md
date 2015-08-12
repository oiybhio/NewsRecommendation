# package edu.ruc.ranker

## Ranker
```java
public List<News> query(User user, List<News> candidateList)
```
外部调用接口

## VSMRanker
```java
private List<Score> scoreList;
private List<News> sortedNewsList;

public List<News> query(User user, List<News> newsList)
```
基于空间向量模型的推荐

### Weight
```java
public List<String> attributeNameList;
public List<Double> weightList;
public int length;
```
空间向量模型中的Attribute权重

### Similarity (interface)
```java
public double getSimilarity(User user, News news, Weight weight)
```
空间向量模型中相似度

### CosineSimilarity (implements Similarity)
```java
public double getSimilarity(User user, News news, Weight weight)
```
空间向量模型中Cosine相似度

### Score
```java
public double similarity; // 相似度
public int position; // 在原 List<News> 中的位置
```
空间向量模型中User和News的相似度评分

## LearningRanker
```java
private List<News> sortedNewsList;

public List<News> query(User user, List<News> newsList)
```
基于机器学习的推荐（尚未完成）

## TimeRanker
```java
private List<News> sortedNewsList;

public List<News> query(User user, List<News> newsList)
```
基于时间的推荐

## PopularityRanker
```java
private List<News> sortedNewsList;

public List<News> query(User user, List<News> newsList)
```
基于热度的推荐

## RandomMerge
```java
private double [] p = new double [] {p1, p2, p3, p4};

public List<News> merge(List<News> newsList1, List<News> newsList2, List<News> newsList3, List<News> newsList4)
```
将不同的推荐列表随机整合在一起（尚未完成）
