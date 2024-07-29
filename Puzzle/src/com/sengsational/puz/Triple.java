package com.sengsational.puz;

import java.util.ArrayList;

public class Triple implements Comparable {
	
	

	String intString;
	int first;
	int second;
	int third;

	public Triple(String intString) {
		this.intString = intString;
		String[] intStrings = intString.split(",");
		this.first = Integer.parseInt(intStrings[0].trim());
		this.second = Integer.parseInt(intStrings[1].trim());
		this.third = Integer.parseInt(intStrings[2].trim());
	}
	
	public Triple reverse() {
		int save = this.third;
		this.third = this.first;
		this.first = save;
		return this;
	}
	
	public boolean uses(int[] centerTiles) {
		for (int i : centerTiles) {
			if (i == first || i == second || i == third) return true;
		}
		return false;
	}
	

	
	public String toString() {
		return first + "," + second + "," + third;
	}

	@Override
	public int compareTo(Object o) {
		return intString.compareTo(((Triple)o).intString);
	}

	public boolean hasMiddleMatch(Triple tripleMiddleCheck) {
		return this.first == tripleMiddleCheck.second || this.second == tripleMiddleCheck.second || this.third == tripleMiddleCheck.second;
	}
	
	public boolean hasLeftMatch(Triple tripleMiddleCheck) {
		return this.first == tripleMiddleCheck.first || this.second == tripleMiddleCheck.first || this.third == tripleMiddleCheck.first;
	}
	
	public int getLastTwoSum() {
		return this.second + this.third;
	}
	
	public boolean hasMiddleOrLeftMatch(Triple tripleMiddleLeftCheck) {
		boolean middleMatch = hasMiddleMatch(tripleMiddleLeftCheck);
		boolean leftMatch = hasLeftMatch(tripleMiddleLeftCheck);
		return middleMatch || leftMatch;
	}

	public boolean chainsAfter(Triple lastItem) {
		//System.out.println("this.first " + this.first + " == lastItem.third" + lastItem.third);
		return this.first == lastItem.third;
	}

	public Triple getReflected() {
		return new Triple("" + this.third + "," + this.second + "," + this.first);
	}
	
	public ArrayList<Integer> getTiles() {
		ArrayList<Integer> tiles = new ArrayList<>();
		tiles.add(first);
		tiles.add(second);
		tiles.add(third);
		return tiles;
	}


	public static void main(String[] args) {
		Triple theOneWithTheMiddleImChecking = new Triple("1, 18, 19");
		
		
		Triple loop1 = new Triple("18, 3, 17");
		Triple loop2 = new Triple("7, 18, 13");
		Triple loop3 = new Triple("8, 17, 13");
		
		System.out.println(loop1.hasMiddleMatch(theOneWithTheMiddleImChecking) + " true ");
		System.out.println(loop2.hasMiddleMatch(theOneWithTheMiddleImChecking) + " true ");
		System.out.println(loop3.hasMiddleMatch(theOneWithTheMiddleImChecking) + " false ");
		
		System.out.println(loop1.chainsAfter(new Triple("12, 8, 18")) + " true");		
		System.out.println(loop1.chainsAfter(loop2) + " false");	
		
		System.out.println(loop1.hasMiddleOrLeftMatch(new Triple("3, 5, 7")) + " true ");
		System.out.println(loop1.hasMiddleOrLeftMatch(new Triple("13, 5, 3")) + " false ");
	}

}
