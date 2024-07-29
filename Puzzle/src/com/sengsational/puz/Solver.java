package com.sengsational.puz;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {
	public static int REMOVE = 0;
	public static int PRESERVE = 1;
	public static int[] CENTER_TILES = {1,2,4,5,6,7,8}; // This is to see how many perimeters use tiles that must be in the center

	public static void main(String[] args) {
		ArrayList<Triple> tripleList = PuzzHelper.getTripleList();
		//for (Triple triple : tripleList) {
		//	System.out.println("tripleList: " + triple);
		//}
		System.out.println(tripleList.size()  + " triples that add up to 38");

		// Cheat: This is the unique outer perimeter that solves the puzzle
		TripleSolution uniqueSolutionCheat = new TripleSolution(new Triple("3,19,16"));
		uniqueSolutionCheat.add(new Triple("16,12,10"));
		uniqueSolutionCheat.add(new Triple("10,13,15"));
		uniqueSolutionCheat.add(new Triple("15,14,9"));
		uniqueSolutionCheat.add(new Triple("9,11,18"));
		uniqueSolutionCheat.add(new Triple("18,17,3"));

		TripleSolution uniqueSolutionCheat2 = new TripleSolution(new Triple("18,17,3"));//
		uniqueSolutionCheat2.add(new Triple("3,19,16"));
		uniqueSolutionCheat2.add(new Triple("16,12,10"));
		uniqueSolutionCheat2.add(new Triple("10,13,15"));
		uniqueSolutionCheat2.add(new Triple("15,14,9"));
		uniqueSolutionCheat2.add(new Triple("9,11,18"));
		
		//if(uniqueSolutionCheat2.isRotationOrReflection(uniqueSolutionCheat)) {
		//	System.out.println("TESTING isRotationOrReflection This is the unique solution to the perimeter: " + uniqueSolutionCheat + "\n");
		//}

		ArrayList<TripleSolution> solutionList = new ArrayList<>();
		int noResultCount = 0;
		int solutionCount = 0;
		do {
			ArrayList<Triple> workingList = new ArrayList<>(tripleList);
			Collections.shuffle(workingList); //randomize the list each iteration
			Triple firstTriple = workingList.get(0);
			//System.out.println(">" + firstTriple);
			TripleSolution solution = new TripleSolution(firstTriple);
			
			// The first item of the first triple can only be in the last position, so remove it from the first and second positions of remaining candidates.
			workingList = pareDownList(workingList, firstTriple.first, REMOVE, REMOVE, PRESERVE);
			// The second item of the first triple needs to be completely removed from any position in remaining candidates.
			workingList = pareDownList(workingList, firstTriple.second, REMOVE, REMOVE, REMOVE);
			// the last item of the first triple can only be in the first position (for when chain is completed).  Anywhere else, it can be removed from the remaining candidates.
			workingList = pareDownList(workingList, firstTriple.third, PRESERVE, REMOVE, REMOVE);
			
			//System.out.println("workingList size " + workingList.size());
			
			while (solution.incomplete()) {
				Triple chainedTriple = getChainedTriple(solution.getLastItem(), workingList);
				if (chainedTriple == null) {
					//System.out.println("no solution");
					for(int i = 0; i < workingList.size(); i++) {
						//System.out.println("no sol: " + workingList.get(i));
					}
					for(int i = 0; i < tripleList.size(); i++) {
						//System.out.println("orig  : " + tripleList.get(i));
					}
					break;
				} else {
					// if this is the last item in the solution, but it doesn't generate a solution, just remove this item and let it go again
					if (solution.needsWrap() && !solution.solvesWith(chainedTriple)) {
						workingList.remove(chainedTriple);
						continue;
					}
					//System.out.println(">" + chainedTriple + "< Added to solution");
					solution.add(chainedTriple);
					// The first item in the chained triple can be completely removed, it's used by two Triples already
					workingList = pareDownList(workingList, chainedTriple.first, REMOVE, REMOVE, REMOVE );
					// The second item in the chained triple can be completely removed from any position
					workingList = pareDownList(workingList, chainedTriple.second, REMOVE, REMOVE, REMOVE);
					// The last item of the chained triple can only be in the first position.  Anywhere else, it can be removed.
					workingList = pareDownList(workingList, chainedTriple.third, PRESERVE, REMOVE, REMOVE);
					//System.out.println("workingList size " + workingList.size() + " solutionList size " + solutionList.size());
				}
			}
			
			if (solution.incomplete()) {
				noResultCount++;
			} else if (solution.isRotationOrReflection(solutionList)) {
				noResultCount++;
			} else {
				solutionList.add(solution);
				solutionCount++;
				noResultCount = 0;
				//System.out.println("FOUND PERIMETER SOLUTION: " + solution + " total of " + solutionList.size()); 
				//5045 with tries at   10000
				//5079 with tries at   50000
				//5083 with tries at  100000
				//5084 with tries at  500000
				//5083 with tries at 1000000
				//5083 with tries at 2000000
				//5084 with tries at 4000000
				
				//if (solution.isRotationOrReflection(uniqueSolutionCheat)) {
				//	System.out.println("Our brute force approach found the unique solution to the perimeter");
				//	break;
				//}
				if (solutionList.size() % 100 == 0) System.out.println("finding perimeter solutions ... " + solutionList.size());
			}
		} while (noResultCount < 100);
		
		System.out.println("We found " + solutionCount + " perimeter solutions by trying random sorts.\nWe tried " + noResultCount + " times in a row to expand the set,\nbut it didn't grow, so we will proceed to solve the center of the puzzle.");
		//for (int i = 0; i < solutionList.size(); i++) {
		//	System.out.println("Solution " + (i+1) + ": " + solutionList.get(i));
		//}
		
		//TripleSolution perimeterSolutionFound = solutionList.get(solutionList.size() - 1);
		//System.out.println("uniqueSolutionCheat is in the set: " + uniqueSolutionCheat.isRotationOrReflection(perimeterSolutionFound) + " true.");
		boolean solutionFound = false;
		for (int i = 0; i < solutionList.size(); i++) {
			// NOW WE NEED TO PLACE THE LAST 7 unused tiles in the center to get the solution.
			PuzzleSolution puzzleSolution = new PuzzleSolution(solutionList.get(i));
			if (puzzleSolution.solve()) {
				System.out.println(puzzleSolution);
				solutionFound = true;
				break;
			} else if(!puzzleSolution.uses(CENTER_TILES)) {
				System.out.println("That perimeter doesn't use the center tiles, so let's brute force a bit harder.");
				System.out.println("Let's try that again!!");
				System.out.println("Let's try that again!!");
				System.out.println("Let's try that again!!");
				if (puzzleSolution.solve()) {
					System.out.println(puzzleSolution);
					solutionFound = true;
					break;
				} else {
					System.out.println("perimeter " + i + " appears not to be a solvable permieter " + solutionList.get(i) + " " + (puzzleSolution.uses(CENTER_TILES)?"":"<<closer"));
				}
			} else {
				System.out.println("perimeter " + i + " appears not to be a solvable permieter " + solutionList.get(i) + " " + (puzzleSolution.uses(CENTER_TILES)?"":"<<closer"));
			}
		}
		if (solutionFound) {
			System.out.println("Yipppee!");
		} else {
			System.out.println("\nSolution not found.  Consider increasing increasing noResultCount threshold higher than " + noResultCount);
		}
	}

	private static ArrayList<Triple> pareDownList(ArrayList<Triple> workingList, int theValue, int presrem1, int presrem2, int presrem3) {
		ArrayList<Triple> updatedList = new ArrayList<>();
		for (Triple triple : workingList) {
			if (presrem1 == REMOVE && triple.first == theValue) continue; //{ System.out.println("removing " + triple + " based on " + theValue); continue;}
			if (presrem2 == REMOVE && triple.second == theValue) continue; // { System.out.println("removing " + triple + " based on " + theValue); continue;}
			if (presrem3 == REMOVE && triple.third == theValue) continue; // { System.out.println("removing " + triple + " based on " + theValue); continue;}
			updatedList.add(triple);
		}
		return updatedList;
	}

	private static Triple getChainedTriple(Triple lastItem, ArrayList<Triple> workingList) {
		//System.out.println("searching " + workingList.size() + " items for an example that starts with " + lastItem.third);
		for (Triple candidateTriple : workingList) {
			if (candidateTriple.chainsAfter(lastItem)) {
				return candidateTriple;
			}
		}
		return null;
	}

}
