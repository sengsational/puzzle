package com.sengsational.puz;

public class Quad {

	private int first;
	private int second;
	private int third;
	private int fourth;

	public Quad(int first, int second, int third, int fourth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
	}
	
	public String toString() {
		return "" + first + ", " + second + ", " + third + ", " + fourth;
	}
	
	public String getItemFormatted() {
		//                                  "  nn, nn, nn, nn  "
		StringBuffer buf = new StringBuffer("    ,   ,   ,     ");
		buf.replace(2, 4, String.format("%1$2s", first));
		buf.replace(6, 8, String.format("%1$2s", second));
		buf.replace(10, 12, String.format("%1$2s", third));
		buf.replace(14, 16, String.format("%1$2s", fourth));
		return buf.toString();
	}
	

	public void place(Integer value, int pos) {
		switch (pos) {
			case 2: {
				this.second = value;
				break;
			}
			case 3: {
				this.third = value;
				break;
			}
			default: throw new IllegalArgumentException("Unexpected value: [" + pos + "]");
		}
	}
	
	public boolean is38() {
		return 38 == first + second + third + fourth;
	}


	public static void main(String[] args) {
		Quad aQuad = new Quad(42, -1, -1, 43);
		aQuad.place(44, 2);
		
		aQuad = new Quad(19, 2, 4, 13);
		System.out.println("plain print [" + aQuad + "]");
		System.out.println("formatted \n[" + aQuad.getItemFormatted() + "]\n[  19,  2,  4, 13  ]");

	}


}
