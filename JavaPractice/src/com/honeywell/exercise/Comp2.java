package com.honeywell.exercise;

class Comp2 {
	{ index = 1; }
	int index;
public static void main(String[] args) {
float f1 = 2.3f;
float[][] f2 = {{42.0f}, {1.7f, 2.3f}, {2.6f, 2.7f}};
float[] f3 = {2.7f};
 Long x = 42L;
 if (f1==f2[1][1]){
	 System.out.println("true");
 }
System.out.println(f2[1][1]);

new Comp2().go();

Days d1 = Days.TH;
Days d2 = Days.M;
 for(Days d: Days.values()) {
 if(d.equals(Days.F)) break;
d2 = d;
}
 System.out.println((d1 == d2)?"same old" : "newly new");
}
 void go() 
 {
int [][] dd = {{9,8,7}, {6,5,4}, {3,2,1,0}};
System.out.println(dd[index++][index++]);
 }
 enum Days {M, T, W, TH, F, SA, SU};
 
 
}
