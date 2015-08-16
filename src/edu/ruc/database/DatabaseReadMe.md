#Database 存储数据
包含user news 等数据  目前设计有user 和news

##DatabaseUser 

###构造函数
DatabaseUser(String name)

###方法
void addUser(User user)
void modifyUser(User user)
User getUser(long uid)

##DatabaseNews

###构造函数
DatabaseNews(String name)

###方法
void addNews(News news)
void modifyNews(News news)
User getNews(long nid)
