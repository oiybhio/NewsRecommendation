package edu.ruc.data;

import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * An Alphabet object stores the mapping between symbols (represented by
 * String objects) and integers (represented by Integer objects). It can be
 * used to map feature strings to feature indices, for example, or to map
 * class labels to class indices.
 * <p>
 * A symbol can never be deleted from an Alphabet object once it has been
 * added. Integers are assigned to symbols sequentially, starting from 0. 
 * For example, suppose we have the following code to insert symbols into an 
 * Alphabet object:
 * <p>
 *   Alphabet alpha = new Alphabet();
 *   alpha.addSymbol("a");
 *   alpha.addSymbol("b");
 *   alpha.addSymbol("z");
 *   alpha.addSymbol("a");
 *   alpha.addSymbol("c");
 * <p>
 * Then internally the following mapping is stored:
 * <p>
 *   a -- 0
 *   b -- 1
 *   z -- 2
 *   c -- 3
 */

public class Alphabet {
	private HashMap<String, Integer> indices;
	private ArrayList<String> symbols;
	
	/**
	 * Constructs a new Alphabet object with no symbol stored.
	 */
	public Alphabet() {
		indices = new HashMap<String, Integer>();
		symbols = new ArrayList<String>();
	}

	/**
	 * Constructs a new Alphabet object with some symbols stored.
	 * 
	 * @param symbols The array of String
	 */
	public Alphabet(String[] symbols) {
		indices = new HashMap<String, Integer>();
		this.symbols = new ArrayList<String>();
		addSymbols(symbols);
	}

	/**
	 * Constructs a new Alphabet object with one symbol stored.
	 * 
	 * @param symbol String
	 */
	/*public Alphabet(String symbol) {
		indices = new HashMap<String, Integer>();
		this.symbols = new ArrayList<String>();
		addSymbol(symbol);
	}*/
	
	/**
	 * Constructs a new Alphabet object that has been constructed before.
	 * 
	 * @param str String from database
	 */
	public Alphabet(String str) {
		indices = new HashMap<String, Integer>();
		symbols = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str," ");
		while(st.hasMoreTokens()){
			String token=st.nextToken();
			if (token.length() < 3) continue;
			StringTokenizer stPart = new StringTokenizer(token,":");
			String sym = stPart.nextToken();
			int value = Integer.parseInt(stPart.nextToken());
			indices.put(sym, new Integer(value));
			symbols.add(sym);
		}
	}
	
	/**
	 * Adds a new symbol into the Alphabet object, and returns the integer 
	 * assigned to this symbol. If this symbol is already stored in the Alphabet
	 * then no new integer is assigned to it and the old integer assigned to it
	 * is returned.
	 * @param sym A symbol to be added
	 * @return The index assigned to the newly added symbol
	 */
	public int addSymbol(String sym) {
		if(sym == null){
			return -1;
		}
		if (!indices.containsKey(sym)) {
			indices.put(sym, new Integer(indices.size()));
			symbols.add(sym);
		}
		return indices.get(sym).intValue();
	}
	
	/**
	 * Add pair<symbol,id> from database
	 * 
	 * @param sym Symbol
	 * @param id Id
	 */
	public void addSymbol(String sym, int id) {
		indices.put(sym, new Integer(id));
		symbols.add(sym);
	}

	/**
	 * Add a array of symbols into current Alphabet.
	 * @param A array of Strings
	 */
	public void addSymbols(String[] symbols){
		assert(symbols.length > 0 );
		for(int i = 0; i < symbols.length; i++){
			addSymbol(symbols[i]);
		}
	}
  
	/**
	 * Returns the index associated with the symbol.
	 * @param sym A symbol of which the index is to be returned
	 * @return The index associated with the given symbol or -1 if the symbol is
	 * not stored in the Alphabet
	 */
	public int getIndex(String sym) {
		if (indices.containsKey(sym)) {
			return indices.get(sym).intValue();
		}
		return addSymbol(sym);
	}

	/**
	 * Returns the symbol at the given index position.
	 * @param index The index position at which the symbol is to be returned
	 * @return The symbol at the given index position or null if the index is 
	 * out of range (index < 0 || index >= size())
	 */
	public String getSymbol(int index) {
		if (index >= 0 && index < symbols.size()) {
			return symbols.get(index);
		}
		return null;
	}

	/**
	 * Returns the size of the Alphabet.
	 * @return The size of this Alphabet object, i.e. the number of symbols 
	 * stored in the Alphabet.
	 */
	public int size() {
		return indices.size();
	}

	/**
	 * Output the elements into screen.
	 */	
	public void display() {
		Iterator<String> ite = indices.keySet().iterator();
		while( ite.hasNext() ){
			String key = ite.next();
			System.out.print(key + ":" + indices.get(key) + " ");
		}
		System.out.println("\n" + "[" + symbols.size()+ "]");
		for(int i = 0; i < symbols.size(); i++){
			System.out.print(symbols.get(i) + " ");
		}
		System.out.println();
	}
	
	/**
	 * Save the Alphabet into file.
	 */	
	public void saveVocab(String file) throws IOException{
		BufferedWriter out = new BufferedWriter( new FileWriter( new File(file)));
		Iterator<String> ite = indices.keySet().iterator();
		while(ite.hasNext()){
			String wrd = ite.next(); 
			int id = indices.get(wrd);
			out.write( wrd + " " + id + "\n");
		}
		out.flush();
		out.close();
	}
	
	/**
	 * HashMap to String
	 * 
	 * @return String
	 */
	public String hashMapToString() {
		String ret = new String();
		for(int i = 0; i < symbols.size(); i++) {
			String word = symbols.get(i);
			assert(getIndex(word) == i);
			ret += word + ":" + i + " ";
		}
		return ret;
	}
	
	/**
	 * Save alphabet into database
	 * 
	 * @param con Connection
	 * @param dictId 
	 * @throws SQLException
	 */
	public void saveIntoDatabase(Connection con, int dictId) throws SQLException {
		Statement stmt = con.createStatement();
		for(int i = 0; i < symbols.size(); i++) {
			String word = symbols.get(i);
			assert(getIndex(word) == i);
			String sql = "replace into dictionarys values(\"" + dictId + "\",\"" + word + "\",\"" + i + "\")";
			stmt.executeUpdate(sql);
		}
	}
	
	/**
	 * Save attribute set into database
	 * 
	 * @param con Connection
	 * @throws SQLException
	 */
	public void saveIntoDatabase(Connection con) throws SQLException {
		
		Statement stmt = con.createStatement();
		String sql = "delete from attribute_set";
		stmt.executeUpdate(sql);
		for(int i = 0; i < symbols.size(); i++) {
			String word = symbols.get(i);
			assert(getIndex(word) == i);
			sql = "insert into attribute_set values(\"" + word + "\",\"" + i + "\")";
			stmt.executeUpdate(sql);
		}
	}
	
	/**
	 * Load attribute set from database
	 * 
	 * @param con Connection
	 * @throws SQLException
	 */
    public void loadFromDatabase(Connection con) throws SQLException {
    	Statement stmt = con.createStatement();
    	ResultSet result = stmt.executeQuery("select attribute_name, dict_id from attribute_set");
        while (result.next()){
            String attribute_name = result.getString("attribute_name");
            int dict_id = result.getInt("dict_id");
            addSymbol(attribute_name, dict_id);
        }
        result.close();
    }
	
	/**
	 * Get all symbols from alphabet
	 * 
	 * @return ArrayList<String> symbols
	 */
	public List<String> getSymbols() {
		return symbols;
	}
	
}
