package com.sengsational.puz;

import java.util.ArrayList;

public class TripleSolution implements Comparable{
	
	private ArrayList<Triple> chainList = new ArrayList<>();

	public TripleSolution(Triple firstTriple) {
		chainList.add(firstTriple);
	}

	public boolean incomplete() {
		return chainList.size() < 6;
	}
	
	private int getSumUsed() {
		int sumUsed = 0;
		for (Triple aTriple : chainList) {
			sumUsed += aTriple.getLastTwoSum();
			//System.out.println("aSum " + aTriple.getLastTwoSum() + " for " + aTriple + " for a total of " + sumUsed);
		}
		return sumUsed;
	}

	public Triple getLastItem() {
		return chainList.get(chainList.size() - 1);
	}
	
	public Triple getItem(int i) {
		return chainList.get(i - 1);
	}
	
	public String getItemFormatted(int i, boolean forward) {
		//                                  "nn, nn, nn, nn, nn"
		StringBuffer buf = new StringBuffer("      ,   ,       ");
		Triple anItem = getItem(i);
		
		if (!forward) {
			buf.replace(12, 14, String.format("%1$2s", anItem.first));
			buf.replace(8, 10, String.format("%1$2s", anItem.second));
			buf.replace(4, 6, String.format("%1$2s", anItem.third));
		} else {
			buf.replace(12, 14, String.format("%1$2s", anItem.third));
			buf.replace(8, 10, String.format("%1$2s", anItem.second));
			buf.replace(4, 6, String.format("%1$2s", anItem.first));
		}
		return buf.toString();
	}

	public void add(Triple chainedTriple) {
		chainList.add(chainedTriple);
	}
	
	public boolean needsWrap() {
		return chainList.size() == 5;
	}

	public boolean solvesWith(Triple chainedTriple) {
		return chainList.get(0).first == chainedTriple.third;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		for (Triple aTriple : chainList) {
			buf.append(aTriple).append(":");
		}
		return buf.toString();
	}

	public boolean isRotationOrReflection(ArrayList<TripleSolution> solutionList) {
		for (TripleSolution listSolution : solutionList) {
			//System.out.println("comparing " + this + " with " + listSolution + " and finding " + this.isRotationOrReflection(listSolution));
			if (this.isRotationOrReflection(listSolution)) return true;
		}
		return false;
	}

	public boolean uses(int[] centerTiles) {
		boolean usesCenterTiles = false;
		for (Triple aTriple : chainList) {
			if (aTriple.uses(centerTiles)) {
				usesCenterTiles = true;
				break;
			}
		}
		return usesCenterTiles;
	}
	
	
	boolean isRotationOrReflection(TripleSolution listSolution) {
		if (isRotation(listSolution)) {
			return true; 
		} else {
			TripleSolution reflectedListSolution = listSolution.getReflected();
			return isRotation(reflectedListSolution);
		}
	}
	
	private boolean isRotation(TripleSolution listSolution) {
		// for it to be a rotation, all items must be present in both
		int foundCount = 0;
		for (Triple triple : this.chainList) {
			for(Triple listTriple : listSolution.chainList) {
				//System.out.println("comparing " + triple + " with " + listTriple + " " + triple.compareTo(listTriple));
				if (triple.compareTo(listTriple) == 0) {
					//System.out.println("at least one true is all we need");
					foundCount++;
					break;
				}
			}
		}
		//System.out.println("foundCount " + foundCount);
		return foundCount == this.chainList.size(); // all found...this is equal or a rotation
		
	}
	
	private TripleSolution getReflected() {
		TripleSolution reflected = new TripleSolution(this.chainList.get(chainList.size() - 1).getReflected());
		for (int i = this.chainList.size() - 2; i > -1; i--) {
			reflected.add(this.chainList.get(i).getReflected());
		}
		return reflected;
	}
	

	public ArrayList<Integer> getUnusedTiles() {
		ArrayList<Integer> digitList = new ArrayList<>(PuzzHelper.DIGIT_ARRAY);
		for (Triple triple : this.chainList) {
			ArrayList<Integer> usedTiles = triple.getTiles();
			for (Integer integer : usedTiles) {
				digitList.remove(integer);
			}
		}
		return digitList;
	}
	public int getDigit(int tripleNumber, int digitNumber) {
		Triple aTriple = this.chainList.get(tripleNumber - 1);
		switch (digitNumber) {
		case 1: {
			return aTriple.first;
		}
		case 2: {
			return aTriple.second;
		}
		case 3: {
			return aTriple.third;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + digitNumber);
		}
	}


	

	
	@Override
	public int compareTo(Object o) {
		System.out.println("this solution    toString() [" + this.toString() + "]");
		System.out.println("passed in object toString() [" + ((TripleSolution)o).toString() + "]");
		return this.toString().compareTo(((TripleSolution)o).toString());
	}


	public static void main(String[] args) {
		TripleSolution solution = new TripleSolution(new Triple("5,18,15"));
		
		System.out.println("forward  \n[" + solution.getItemFormatted(1, true)  + "]\n[     5, 18, 15    ] < correct");
		System.out.println("backward \n[" + solution.getItemFormatted(1, false) + "]\n[    15, 18,  5    ] < correct");
		                                      //33
		solution.add(new Triple("15,13,10")); //23
		solution.add(new Triple("10,9,19"));  //28
		solution.add(new Triple("19,11,8"));  //19
		solution.add(new Triple("8,14,16"));  //30
		solution.add(new Triple("16,17,5"));  //22
		                                     //155
		                                     
		System.out.println("solution.getUnusedTiles() " + solution.getUnusedTiles());
		
		System.out.println(solution.getSumUsed() + " 155");
		
		TripleSolution solutionRotated = new TripleSolution(new Triple("19,11,8"));
		solutionRotated.add(new Triple("8,14,16"));
		solutionRotated.add(new Triple("16,17,5"));
		solutionRotated.add(new Triple("5,18,15"));
		solutionRotated.add(new Triple("15,13,10"));
		solutionRotated.add(new Triple("10,9,19"));

		System.out.println("TripleSolution Main: isRotationOrReflaction " + solution.isRotationOrReflection(solutionRotated) + " true");
		
		TripleSolution solutionReflected = solutionRotated.getReflected();
		System.out.println("TripleSolution Main: solution: " + solution);
		System.out.println("TripleSolution Main: solutionRotated: " + solutionRotated);
		System.out.println("TripleSolution Main: solutionReflected: " + solutionReflected);
		
		System.out.println("TripleSolution Main: isRotationOrReflaction " + solution.isRotationOrReflection(solutionReflected) + " true");
		
		solution = new TripleSolution(new Triple("9, 18, 11"));
		System.out.println("1last item " + solution.getLastItem());
		solution.add(new Triple("11, 10, 17"));
		System.out.println("2last item " + solution.getLastItem());
		solution.add(new Triple("17, 8, 13"));
		System.out.println("3last item " + solution.getLastItem());
		System.out.println("incomplete: " + solution.incomplete() + " true");
		
	}


}
