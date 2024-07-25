package com.sengsational.puz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class PuzzHelper {
	public static final Integer[] DIGITS = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
	public static final ArrayList<Integer> DIGIT_ARRAY = new ArrayList<Integer>(Arrays.asList(DIGITS));

	public static ArrayList<Triple> getTripleList() {
		TreeSet<Triple> allPossible = getAllPossible();
		//for (Triple triple : allPossible) {
		//	System.out.println("allposs: " + triple);
		//}
		return new ArrayList<Triple>(allPossible);
	}
	
	public static TreeSet<Triple> getAllPossible() {
		TreeSet<Triple> aSet = new TreeSet<>();
		int length = 3;
		ArrayList<Integer> digitList = new ArrayList<>(DIGIT_ARRAY);
		int noResultCount = 0;
		do {
			Collections.shuffle(digitList);
			ArrayList<Triple> foundSums = findSums(length, 38, digitList);
			for (Triple foundSum : foundSums) {
				if (aSet.contains(foundSum)) continue;
				aSet.add(foundSum);
				noResultCount = 0;
			}
			noResultCount++;
		} while (noResultCount < 10000);

		System.out.println("we found " + aSet.size() + " items of length "  + length + " adding up to 38.");
		return aSet;

	}

	private static ArrayList<Triple> findSums(int length3, int targetTotal38, ArrayList<Integer> digitList) {
		ArrayList<Triple> returnList = new ArrayList<>();
		for (int i = 0; i < digitList.size() - 3; i++) {
			int sumOfThree = 0;
			sumOfThree+=digitList.get(i) + digitList.get(i+1) + digitList.get(i+2);
			if (sumOfThree == 38) {
				returnList.add(new Triple(digitList.get(i)+","+digitList.get(i+1)+","+digitList.get(i+2)));
			}
		}
		return returnList;
	}

	
	public static void main(String[] args) {
		ArrayList<Triple> tripleList = getTripleList();
		for (Triple triple : tripleList) {
			System.out.println("list item [" + triple + "]");
		}
	}

}
