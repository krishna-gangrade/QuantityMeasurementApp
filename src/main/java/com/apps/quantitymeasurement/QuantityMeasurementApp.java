package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {
// Inner class to represent Feet measurement
	public static class Feet {
		private final double value;

		public Feet(double value) {
			this.value = value;
		}
		@Override
		public boolean equals(Object obj) {

			if (this == obj) {
				return true;
			}

			if (obj == null) {
				return false;
			}

			if (getClass() != obj.getClass()) {
				return false;
			}

			Feet other = (Feet) obj;
			return Double.compare(this.value, other.value) == 0;
		}

		public double getValue() {
			return value;
		}
	}

	public static class Inches {
		private final double value;

		public Inches(double value) {
			this.value = value;
		}
		@Override
		public boolean equals(Object obj) {

			if (this == obj) {
				return true;
			}

			if (obj == null) {
				return false;
			}

			if (getClass() != obj.getClass()) {
				return false;
			}

			Inches other = (Inches) obj;
			return Double.compare(this.value, other.value) == 0;
		}

		public double getValue() {
			return value;
		}
		
	}
	public static void demonstrateFeetEquality(Scanner sc) {
		double inputOne = sc.nextDouble();
		double inputTwo = sc.nextDouble();
		Feet f1 = new Feet(inputOne);
		Feet f2 = new Feet(inputTwo);
		System.out.println("Are equal? " + f1.equals(f2));
	}
	public static void demonstrateInchesEquality(Scanner sc) {
		
		double inputOne = sc.nextDouble();
		double inputTwo = sc.nextDouble();
		Inches i1 = new Inches(inputOne);
		Inches i2 = new Inches(inputTwo);
		System.out.println("Are equal? " + i1.equals(i2));
        sc.close();
	}
	
// Main method to demonstrate Feet equality check
	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		demonstrateFeetEquality(sc);
		demonstrateInchesEquality(sc);
		sc.close();
	}
}