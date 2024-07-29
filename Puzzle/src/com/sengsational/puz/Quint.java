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

	public String getItemFormatted() {
		//                                  "nn, nn, nn, nn, nn"
		StringBuffer buf = new StringBuffer("  ,   ,   ,   ,   ");
		buf.replace(0, 2, String.format("%1$2s", first));
		buf.replace(4, 6, String.format("%1$2s", second));
		buf.replace(8, 10, String.format("%1$2s", third));
		buf.replace(12, 14, String.format("%1$2s", fourth));
		buf.replace(16, 18, String.format("%1$2s", fifth));

		return buf.toString();
	}
	
	public String toString() {
		return "" + first + ", " + second + ", " + third + ", " + fourth + ", " + fifth;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Quint quint = new Quint(3, 8, 7, 5, 15);
		System.out.println("[" + quint.getItemFormatted() +"]\n[ 3,  8,  7,  5, 15]");

	}

	public boolean is38() {
		return 38 == first + second + third + fourth + fifth;
	}


}
