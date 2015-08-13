package edu.ruc.data;

import java.util.ArrayList;

public class Attribute {
	SparseVector sparseVector;
	DenseVector denseVector;
	Dictionary dict;
	int dictId;
	String attributeName;
    VectorType vectorType;
	
    /**
     * Constructor for a Attribute.
     * 
     * @param vectorType SPARSE or DENSE
     * @param dict The dictionary of all symbols
     * @param attributeSet The dictionary of attribute name
     * @param attributeName The attribute name
     */
	public Attribute(VectorType vectorType, Dictionary dict, Alphabet attributeSet, String attributeName) {
		switch(vectorType) {
		case SPARSE:
			this.dict = dict;
			this.attributeName = attributeName;
			sparseVector = new SparseVector();
			dictId = attributeSet.getIndex(attributeName);
			this.vectorType = VectorType.SPARSE;
			break;
		case DENSE:
			this.attributeName = attributeName;
			denseVector = new DenseVector();
			this.vectorType = VectorType.DENSE;
			break;
		}
	}

	/**
	 * Get VectorType
	 * 
	 * @return VectorType
	 */
	public VectorType getVectorType() {
		return vectorType;
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
	 * Get DenseVector
	 * 
	 * @return DenseVector
	 */
	public DenseVector getDenseVector() {
		return denseVector;
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
	 * Add a feature(double) into DenseVector
	 * 
	 * @param value The value
	 */
	public void addFeature(double value) {
		denseVector.pushBack(value);
	}
	
	/**
	 * Add all features(double) into DenseVector
	 * 
	 * @param arrayList The DoubleList
	 */
	public void addFeature(ArrayList<Double> arrayList) {
		denseVector.addAll(arrayList);
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
	 * Get a feature value from DenseVector
	 * 
	 * @param index the index of the value wanted to get
	 * @return feature value
	 */
	public double getFeature(int index) {
		return denseVector.getValue(index);
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
	
	/**
	 * Modify a feature value in DenseVector
	 * 
	 * @param index the index wanted to modify
	 * @param value the value wanted to modify
	 */
	public void modigyFeature(int index, double value) {
		denseVector.modify(index, value);
	}
	
	/**
	 * Output the elements into screen.
	 */
	public void display() {
		System.out.println("Attribute Name: " + attributeName);
		System.out.println("Dictionary Id: " + dictId);
		switch(vectorType) {
		case SPARSE:
			sparseVector.display();
			break;
		case DENSE:
			denseVector.display();
			break;
		}
	}
	
}
