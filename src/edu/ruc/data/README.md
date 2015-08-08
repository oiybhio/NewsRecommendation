# package edu.ruc.data
## Pair
```java
private int first; // key
private double second; // value
```
一个键值对<Integer, Double>
## SparseVector
```java
private int length; // vector size
private ArrayList<Pair> arrayList;
```
向量稀疏表示，多个键值对。
## Alphabet
```java
private HashMap<String, Integer> indices;
private ArrayList<String> symbols; // all symbol in this alphabet
```
词典中的一个表
## Dictionary
```java
private int length; // the number of alphabets
private ArrayList<Alphabet> arrayList;
```
词典，类似于全局变量
## Attribute
```java
SparseVector sparseVector;
Dictionary dict; // 全局词典，由构造函数参数传入
int dictId; // 词典ID，由attributeName得到
String attributeName; // 由构造函数参数传入
```
封装SparseVector和dictID <br>
构造函数中 Alphabet attributeSet 将 attributeName map dictID，Alphabet attributeSet 类似于全局变量
## Hint
User和News中应包含成员函数Attribute，并为其分配内存空间
