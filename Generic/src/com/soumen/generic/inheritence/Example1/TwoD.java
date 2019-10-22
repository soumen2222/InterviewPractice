package com.soumen.generic.inheritence.Example1;

import java.util.ArrayList;


 class TwoD {

	 int x,y;
	 TwoD(int a, int b)
	 {
		 x=a;
		 y=b;
	 }

}
 
 class ThreeD extends TwoD {

	 int z;
	 ThreeD(int a, int b, int c)
	 {
		super(a,b);
		z=c;
	 }

}

 
 class FourD extends ThreeD {

	 int t;
	 FourD(int a, int b, int c, int d)
	 {
		super(a,b,c);
		t=d;
	 }

}
 
 class Coords<T extends TwoD>{
  // T[] coords;
	ArrayList<T>  var = new ArrayList<T>();
	 
	 Coords(ArrayList<T> o){
		 var =o;		 
	 }
 }

 
 
 