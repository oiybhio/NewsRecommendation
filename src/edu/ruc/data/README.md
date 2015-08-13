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
向量稀疏表示，多个键值对
## DenseVector
```java
private ArrayList<Double> arrayList;
```
稠密向量表示
## VectorType
```java
public enum VectorType { SPARSE, DENSE }
```
向量类型 枚举类型 SPARSE or DENSE
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
DenseVector denseVector;
Dictionary dict; // 全局词典，由构造函数参数传入
int dictId; // 词典ID，由attributeName得到
String attributeName; // 由构造函数参数传入
VectorType vectorType; // VectorType.SPARSE or VectorType.DENSE 由构造函数参数传入
```
封装SparseVector和dictID 或 DenseVector <br>
构造函数中 Alphabet attributeSet 将 attributeName map to dictID，Alphabet attributeSet 类似于全局变量
## Hint
User和News中应包含成员函数 List\<Attribute>，并为其分配内存空间
初始化过程中需要创建 Dictionary dict 和 Alphabet attributeSet 实例
