package edu.ruc.data;

public class Attribute {
	SparseVector sparseVector;
	Dictionary dict;
	int dictId;
	String attributeName;
	
    /**
     * Constructor for a Attribute.
     * 
     * @param dict The dictionary of all symbols
     * @param attributeSet The dictionary of attribute name
     * @param attributeName The attribute name
     */
	public Attribute(Dictionary dict, Alphabet attributeSet, String attributeName) {
		this.dict = dict;
		this.attributeName = attributeName;
		sparseVector = new SparseVector();
		dictId = attributeSet.getIndex(attributeName);
	}
	
	/**
	 * Get AttributeName
	 * 
	 * @return AttributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

	/**
	 * Get SparseVector
	 * 
	 * @return SparseVector
	 */
	public SparseVector getSparseVector() {
		return sparseVector;
	}	
	
	/**
	 * Add a feature(Pair) into SparseVector 
	 * 
	 * @param symbol The symbol of key
	 * @param value The value
	 */
	public void addFeature(String symbol, double value) {
		int key = dict.getAlphabetAt(dictId).getIndex(symbol);
		sparseVector.pushBack(Pair.create(key, value));
	}
	
	/**
	 * Get a feature value from SparseVector
	 * 
	 * @param symbol The symbol of key
	 */
	public double getFeature(String symbol) {
		int key = dict.getAlphabetAt(dictId).getIndex(symbol);
		return sparseVector.findValue(key);
	}
	
	/**
	 * Modify a feature value in SparseVector
	 * 
	 * @param symbol The symbol of key
	 * @param value The value
	 */
	public void modifyFeature(String symbol, double value) {
		int key = dict.getAlphabetAt(dictId).getIndex(symbol);
		sparseVector.modifyPair(Pair.create(key, value));
	}
}
