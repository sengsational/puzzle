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

	}

}
