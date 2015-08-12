# package edu.ruc.ranker

## Ranker
```java
public List<News> query(User user, List<News> candidateList)
```
外部调用接口

## VSMRanker
基于空间向量模型的推荐

### Weight
空间向量模型中的Attribute权重

### Similarity (interface)
空间向量模型中相似度

### CosineSimilarity (implements Similarity)
空间向量模型中Cosine相似度

### Score
空间向量模型中User和News的相似度评分

## LearningRanker
基于机器学习的推荐（尚未完成）

## TimeRanker
基于时间的推荐

## PopularityRanker
基于热度的推荐

## RandomMerge
将不同的推荐列表随机整合在一起（尚未完成）
