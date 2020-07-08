package com.thomas.oneToManyRelationshipSortedMapEagerComparatorwiseSortedWithBackwardNavigability.comparator;

import java.util.Comparator;

public class AKeyComparator implements Comparator<String> {

	@Override
	public int compare(String keyOfA_1, String keyOfA_2) {
		final int BEFORE = -1;
	    final int AFTER = 1;
		if(keyOfA_2 == null) {
			return BEFORE;
		}
		if(keyOfA_1 == null) {
			return AFTER;
		}
		return keyOfA_1.compareTo(keyOfA_2);
	}
}