package edu.ruc.data;

import java.sql.*;
import java.util.*;

public class Dictionary {
	private int length;
	private ArrayList<Alphabet> arrayList;
	private HashMap<String,Double> word_idf;
	private Double Common_value=10.0;
    /**
     * Constructor for a Dictionary.
     */
    public Dictionary() {
    	length = 0;
    	arrayList = new ArrayList<Alphabet>();
    	word_idf=new HashMap<String, Double>();
    }
    
    public Dictionary(HashMap<String,Double> map) {
    	length = 0;
    	arrayList = new ArrayList<Alphabet>();
    	word_idf=new HashMap<String, Double>();
    	this.word_idf=map;
    }
    
    
    /**
     * Get size.
     */
    public int size() {
    	return length;
    }

    /**
     * Add Alphabet to Dictionary.
     *
     * @param labelSet the Alphabet wanted to push back
     * @param symbols a array of Strings
     */    
    public void pushBack(Alphabet labelSet) {
    	arrayList.add(labelSet);
    	length++;
    }
    public void pushBack() {
    	arrayList.add(new Alphabet());
    	length++;
    }
    public void pushBack(String[] symbols) {
    	arrayList.add(new Alphabet(symbols));
    	length++;
    }
    /*public void pushBack(String symbol) {
    	arrayList.add(new Alphabet(symbol));
    	length++;
    }*/
    
    /**
     * Get Alphabet from Dictionary.
     *
     * @param i the index of the Alphabet wanted to get
     */    
    public Alphabet getAlphabetAt(int i) {
    	while (i >= length) {
    		pushBack();
    	}
    	Alphabet labelSet = (Alphabet) arrayList.get(i);
		return labelSet;
    }
    
    /**
     * Save dictionary into database
     * 
     * @param con Connection
     * @throws SQLException
     */
    public void saveIntoDatabase(Connection con) throws SQLException {
    	int i = 0;
    	for (Alphabet alphabet:arrayList) {
    		alphabet.saveIntoDatabase(con, i);
    		i++;
    	}
    }
    
    /**
     * Load dictionary from database
     * 
     * @param con Connection
     * @throws SQLException
     */
    public void loadFromDatabase(Connection con) throws SQLException {
    	Statement stmt = con.createStatement();
    	ResultSet result = stmt.executeQuery("select dict_id, symbol, symbol_id from dictionarys");
        while (result.next()){
            int dictId = result.getInt("dict_id");
            String symbol = result.getString("symbol");
            int symbol_id = result.getInt("symbol_id");
            while (dictId >= length)
            	pushBack();
            arrayList.get(dictId).addSymbol(symbol, symbol_id);
        }
        result.close();
    }
    
    public void loadIDF(HashMap<String,Double> map){
    	this.word_idf=map;
    }
    
    public void loadIDF(String key,Double val){
    	word_idf.put(key, val);
    }
    public Double getIDF(String key){
    	if(!word_idf.containsKey(key)){
    		return Common_value;
    	}else{
    		return word_idf.get(key);
    	}
    }
    
    
    
}
