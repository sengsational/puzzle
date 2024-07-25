package com.sengsational.puz;

public class Quint {

	private int first;
	private int second;
	private int third;
	private int fourth;
	private int fifth;

	public Quint(int first, int second, int third, int fourth, int fifth) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
	}

	public void place(Integer integer, int pos) {
		switch (pos) {
		case 2: {
			this.second = integer;
			break;
		}
		case 3: {
			this.third = integer;
			break;
		}
		case 4: {
			this.fourth = integer;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + pos);
		}
	}

	
	public String toString() {
		return "" + first + ", " + second + ", " + third + ", " + fourth + ", " + fifth;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public boolean is38() {
		return 38 == first + second + third + fourth + fifth;
	}

}
