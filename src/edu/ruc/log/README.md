# package edu.ruc.log
## Behavior
```java
private long uid;  // userID
private long nid;  // newsID
private String behave;   // user's behavior
private long startTime;   // The action execution time
private String comment;   // user's comment
private long time;    //  the duration of the action
private LogAnalysis analysis;   // the analysis of the action
```
一个数据结构用来记录用户行为<uid, nid, behave, time ... >
## NewsAnalysis
```java
对news特征值的获取与分析，目前还没与文慧师兄对接
## LogAnalysis
```java
private User user;  // user
private NewsAnalysis newsA; // news
private long startTime; // The action execution time
private long time;  //  the duration of the action
```
根据用户行为处理更新用户的特征值
## ClickAnalysis  CollectAnalysis  CommentAnalysis BrowseAnalysis
extends from LogAnalysis
从LogAnalysis处继承得到，用来处理不同的用户行为
##  SparseBehavior
```java
private int length;  // the number of behaviors
private List<Behavior> arrayList;
```
当前获得的全部未经处理的用户行为Log
