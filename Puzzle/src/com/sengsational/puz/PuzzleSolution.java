package com.sengsational.puz;

import java.util.ArrayList;
import java.util.Collections;

public class PuzzleSolution {
	
	TripleSolution psf;
	Quint hq;
	Quint luq;
	Quint rdq;
	
	Quad ht;
	Quad hb;
	Quad lut;
	Quad lub;
	Quad rdt;
	Quad rdb;

	public PuzzleSolution(TripleSolution perimeterSolutionFound) {
		this.psf = perimeterSolutionFound;
	}

	public boolean solve() {
		ArrayList<Integer> unusedTiles = psf.getUnusedTiles();
		//System.out.println("trying to place " + unusedTiles);

		//System.out.println("We need a model to hold all 19 tiles so we can check the sums.");

		// Horizontal mid5 - starts with fifth TripleSolution, third tile
		//                 - ends with second TripleSolution, third tile
		// LeftUp mid5     - starts with fourth TripleSolution, third tile
		//                 - ends with first TripleSolution, third tile
		// RightDown mid5  - starts with sixth TripleSolution, third tile 
		//                 - ends with third TripleSolution, third tile
		
		// All these lines of 4 tiles use the second tile in the perimeter triple
		
		// Horizontal top4 - starts with sixth TripleSolution 6
		//                 - ends with second TripleSolution  2
		// Horizontal bot4 - starts with fifth TripleSolution 5
		//                 - ends with third TripleSolution   3

		// LeftUp top4     - starts with fifth TripleSolution  5
		//                 - ends with first Triple Solution   1 
		// LeftUp bot4     - starts with fourth TripleSolution 4
		//                 - ends with second TripleSolution   2
		
		// RightDown top4  - starts with the first TripleSolution 1
		//                 - ends with the third TripleSolution   3
		// RightDown bot4  - starts with the sixth TripleSolution 6
		//                 - ends with the forth TripleSolution   4
		
		hq = new Quint(psf.getDigit(5, 3), -1, -1, -1, psf.getDigit(2, 3));
		luq = new Quint(psf.getDigit(4, 3), -1, -1, -1, psf.getDigit(1, 3));
		rdq = new Quint(psf.getDigit(6, 3), -1, -1, -1, psf.getDigit(3, 3));
		
		ht = new Quad(psf.getDigit(6, 2), -1, -1, psf.getDigit(2, 2));
		hb = new Quad(psf.getDigit(5, 2), -1, -1, psf.getDigit(3, 2));
		lut = new Quad(psf.getDigit(5, 2), -1, -1, psf.getDigit(1, 2));
		lub = new Quad(psf.getDigit(4, 2), -1, -1, psf.getDigit(2, 2));
		rdt = new Quad(psf.getDigit(1, 2), -1, -1, psf.getDigit(3, 2));
		rdb = new Quad(psf.getDigit(6, 2), -1, -1, psf.getDigit(4, 2));
		/*
		System.out.println("Horizontal " + ht);
		System.out.println("Horizontal " + hq);
		System.out.println("Horizontal " + hb);
		
		System.out.println("Left Up " + lut);
		System.out.println("Left Up " + luq);
		System.out.println("Left Up " + lub);
		
		System.out.println("Right Down " + rdt);
		System.out.println("Right Down " + rdq);
		System.out.println("Right Down " + rdb);
		*/
		
		int noResultCount = 0;
		do {
			Collections.shuffle(unusedTiles);
			// First row, two additions needed
			ht.place(unusedTiles.get(0), 2);
			lut.place(unusedTiles.get(0),3);
			rdq.place(unusedTiles.get(0),2);
			
			ht.place(unusedTiles.get(1), 3);
			luq.place(unusedTiles.get(1), 4);
			rdt.place(unusedTiles.get(1), 2);
			
			// Second row, three additions needed
			hq.place(unusedTiles.get(2), 2);
			lut.place(unusedTiles.get(2), 2);
			rdb.place(unusedTiles.get(2), 2);
			
			hq.place(unusedTiles.get(3), 3);
			luq.place(unusedTiles.get(3), 3);
			rdq.place(unusedTiles.get(3), 3);
			
			hq.place(unusedTiles.get(4), 4); //6
			lub.place(unusedTiles.get(4), 3);
			rdt.place(unusedTiles.get(4), 3);
			
			// Third row, two additions needed
			hb.place(unusedTiles.get(5), 2); //4
			luq.place(unusedTiles.get(5), 2);
			rdb.place(unusedTiles.get(5), 3);
			
			hb.place(unusedTiles.get(6), 3); //8
			lub.place(unusedTiles.get(6), 2);
			rdq.place(unusedTiles.get(6), 4);
			/*
			System.out.println("second hor " + ht);
			System.out.println("third hor " + hq);
			System.out.println("fourth hor " + hb);
			
			System.out.println("left up top " + lut);
			System.out.println("left up quint " + luq);
			System.out.println("left up bot " + lub);
			
			System.out.println("right down top " + rdt);
			System.out.println("right down quint " + rdq);
			System.out.println("right down bot " + rdb);
			*/
/*
 * 
second hor 19, 7*, 1*, 11
third hor 16, 2*, 5*, 6*, 9
fourth hor 12, 4*, 8, 14
left up top 12, 2*, 7*, 17
left up quint 10, 4*, 5*, 1*, 18
left up bot 13, 8, 6*, 11
right down top 17, 1*, 6*, 14
right down quint 3, 7*, 5*, 8, 15
right down bot 19, 2*, 4*, 13			
*/
			noResultCount++;
			if (!hq.is38()) continue;
			if (!luq.is38()) continue;
			if (!rdq.is38()) continue;
			if (!ht.is38()) continue;
			if (!hb.is38()) continue;
			if (!lut.is38()) continue;
			if (!lub.is38()) continue;
			if (!rdt.is38()) continue;
			if (!rdb.is38()) continue;
			System.out.println("\nFOUND THE SOLUTION!!\n");
			/*
			System.out.println("second hor " + ht);
			System.out.println("third hor " + hq);
			System.out.println("fourth hor " + hb);
			
			System.out.println("left up top " + lut);
			System.out.println("left up quint " + luq);
			System.out.println("left up bot " + lub);
			
			System.out.println("right down top " + rdt);
			System.out.println("right down quint " + rdq);
			System.out.println("right down bot " + rdb);
			*/
			return true;
		} while (noResultCount < 10000);
		return false;
	}

	public boolean uses(int[] centerTiles) {
		return psf.uses(centerTiles);
	}

	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(psf.getItemFormatted(1, true)).append("\n");
		buf.append(ht.getItemFormatted()).append("\n");
		buf.append(hq.getItemFormatted()).append("\n");
		buf.append(hb.getItemFormatted()).append("\n");
		buf.append(psf.getItemFormatted(4, false)).append("\n");
		return buf.toString();
	}
	
	public static void main(String[] args) {
		// Cheat: This is the unique outer perimeter that solves the puzzle
		TripleSolution uniqueSolutionCheat = new TripleSolution(new Triple("3,17,18"));
		uniqueSolutionCheat.add(new Triple("18,11,9"));
		uniqueSolutionCheat.add(new Triple("9,14,15"));
		uniqueSolutionCheat.add(new Triple("15,13,10"));
		uniqueSolutionCheat.add(new Triple("10,12,16"));
		uniqueSolutionCheat.add(new Triple("16,19,3"));
		
		
		// NOW WE NEED TO PLACE THE LAST 7 unused tiles in the center to get the solution.
		PuzzleSolution puzzleSolution = new PuzzleSolution(uniqueSolutionCheat);
		if (puzzleSolution.solve()) {
			System.out.println(puzzleSolution);
		} else {
			System.out.println("unable to find a solution.");
		}
		

	}


	
}
