package edu.ruc.ranker;

import edu.ruc.data.*;
import edu.ruc.user.*;

import java.util.*;

public class Weight {
	public List<String> attributeNameList;
	public List<Double> weightList;
	public int length;
	
	public Weight(User user) {
		attributeNameList = new ArrayList<String>();
		weightList = new ArrayList<Double>();
		length = 0;
		List<Attribute> arrayList = user.getAttributeList();
		for(Attribute attribute:arrayList) {
			attributeNameList.add(attribute.getAttributeName());
			weightList.add(1.0);
			length += 1;
		}
	}
}
