# package edu.ruc.user
## user
```java
private long uid; // user's id
private int length;  //the number of attributes
private List<Attribute> arrayList;
```
一个数据结构用来描述用户的特征<uid, length, List>
## OnlineUsers
```java
private int length; // the number of users
private List<User> arrayList;
```
将当前上线的所有用户都load到内存
