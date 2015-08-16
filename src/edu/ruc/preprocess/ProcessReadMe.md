#Process 预处理
预处理包主要有两大功能 分词和热度计算

##Tokenizer 对语句进行分词

###构造函数
public Tokenizer()
public Tokenizer(String str)

###方法
void setText(String test):装载语料
void appendText(String aptext):追加语料
boolean setEncode(String code):默认utf-8 是否有备选？
void addDictionary(String filename)；从文件读入词典
void addDictionary(String dic):直接读入词典 //格式？
String doTokenizing();//分词
String doTokenizing(String text);
String doTokenizing(String text,string code);
void clear();//清理词典 语料

##Hotness 对新闻进行热度  暂时为title

###构造函数
public Hotness()
public Hotness(String title)

###方法
void setQuery(String query):设置搜索内容
void setPeriod(String time):设置时间区域
double getScore():计算热度 