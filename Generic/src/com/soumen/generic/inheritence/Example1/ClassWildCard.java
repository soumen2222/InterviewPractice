package com.soumen.generic.inheritence.Example1;

import java.util.ArrayList;

public class ClassWildCard {

	static void showXY(Coords<?> c) {
		System.out.println("x y corordinates are: ");
		for (int i = 0; i < c.var.size(); i++) {
			System.out.println("X value is:" + c.var.get(i).x + "   Y value is:"
					+ c.var.get(i).y);

		}
	}

	static void showXYZ(Coords<? extends ThreeD> c) {
		System.out.println("x y Z corordinates are: ");
		for (int i = 0; i < c.var.size(); i++) {
			System.out.println("X value is:" + c.var.get(i).x
					+ "   Y value is:" + c.var.get(i).y + "   Z value is:"
					+ c.var.get(i).z);

		}
	}
	
	static void superXYZ(Coords<? super ThreeD> c) {
		System.out.println("x y Z corordinates are: ");
		for (int i = 0; i < c.var.size(); i++) {
			System.out.println("X value is:" + c.var.get(i).x
					+ "   Y value is:" + c.var.get(i).y );

		}
	}
	

	static void showAll(Coords<? extends FourD> c) {
		System.out.println("All corordinates are: ");
		for (int i = 0; i < c.var.size(); i++) {
			System.out.println("X value is:" + c.var.get(i).x
					+ "   Y value is:" + c.var.get(i).y + "   Z value is:"
					+ c.var.get(i).z + "   Z value is:" + c.var.get(i).t);
		}
	}

	public static void main(String[] args) {
		ArrayList<TwoD> twoD = new ArrayList<TwoD>();
		twoD.add(new TwoD(0, 0));
		twoD.add(new TwoD(4, 5));
		twoD.add(new TwoD(6, 7));

		TwoD td2[] = { new TwoD(0, 0), new TwoD(4, 5), new TwoD(3, 2),
				new TwoD(7, 8) };

		Coords<TwoD> tdlocs2 = new Coords<TwoD>(twoD);

		showXY(tdlocs2);
		superXYZ(tdlocs2);
		/*
		 * showXYZ(tdlocs); showAll(tdlocs); -- Not allowed as TwoD is not a
		 * subset of ThreeD
		 */

		ThreeD td3[] = { new ThreeD(1, 2, 3), new ThreeD(2, 0, 4),
				new ThreeD(5, 1, 2), new ThreeD(7, 6, 8) };

		ArrayList<ThreeD> threeD = new ArrayList<ThreeD>();
		threeD.add(new ThreeD(2, 0, 4));
		threeD.add(new ThreeD(5, 1, 2));
		threeD.add(new ThreeD(7, 6, 8));

		Coords<ThreeD> tdlocs3 = new Coords<ThreeD>(threeD);

		showXY(tdlocs3);
		showXYZ(tdlocs3);
		superXYZ(tdlocs3);
		// showAll(tdlocs3); -- Not allowed as ThreeD is not a subset of FourD

		FourD td4[] = { new FourD(1, 2, 3, 4), new FourD(2, 0, 4, 5),
				new FourD(5, 1, 2, 4), new FourD(7, 6, 8, 1) };

		ArrayList<FourD> fourD = new ArrayList<FourD>();
		fourD.add(new FourD(2, 0, 4, 3));
		fourD.add(new FourD(5, 1, 2, 5));
		fourD.add(new FourD(7, 6, 8, 7));

		Coords<FourD> tdlocs4 = new Coords<FourD>(fourD);

		showXY(tdlocs4);
		showXYZ(tdlocs4);
		showAll(tdlocs4);
			
	}

}
