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

// Main method to demonstrate Feet equality check
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		double inputOne = sc.nextDouble();
		double inputTwo = sc.nextDouble();
		Feet f1 = new Feet(inputOne);
		Feet f2 = new Feet(inputTwo);

		System.out.println("Are equal? " + f1.equals(f2));
	}
}
