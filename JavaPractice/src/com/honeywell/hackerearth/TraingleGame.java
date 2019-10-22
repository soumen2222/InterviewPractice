package com.honeywell.hackerearth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


class Circle {
	double x;
	double y;
}

public class TraingleGame {

	/**
	 * @param args
	 */
	private static int finalCount =0;
	
	public static void combinations(ArrayList<Circle> arr, int len, int startPosition, ArrayList<Circle> result){
		int tempCount =0;
        if (len == 0){
        	  
        	//first Validate Triangle
        	
        	
        	if(validtriangle(result.get(0).x,result.get(0).y,result.get(1).x,result.get(1).y,result.get(2).x,result.get(2).y))
        		{
        		ArrayList<Double> slopes = new ArrayList<Double>();
        		       for (Circle point : arr) {        		    	   
        		    	   
        		    	   if(isInsideTri(result.get(0).x,result.get(0).y,result.get(1).x,result.get(1).y,result.get(2).x,result.get(2).y,point.x,point.y))
        		    		   {
        		    		   tempCount++;
        		    		   //calculate Slope
        		    		   
        		    		   slopes.add(calcSlope(result.get(0).x,result.get(0).y,point.x,point.y));
        		    		   slopes.add(calcSlope(result.get(1).x,result.get(1).y,point.x,point.y));
        		    		   slopes.add(calcSlope(result.get(2).x,result.get(2).y,point.x,point.y));
        		    		   
        		    		  // System.out.println(result.get(0).x + ","+ result.get(0).y +"-"+ result.get(1).x +","+ result.get(1).y + "-"+ result.get(2).x + ","+ result.get(2).y + ","+ point.x + ","+ point.y + ",tempcount:"+ tempCount);
        		    		   }
					}
        		       if(tempCount>1){
        		       for (int i =0; i<slopes.size(); i++)
        		       {
        		    	   for (int j =i+1; j<slopes.size(); j++){
        		    		   
        		    		   if(slopes.get(i).doubleValue()==slopes.get(j).doubleValue()){
        		    			   tempCount--;
        		    		   }
        		    		   
        		    	   }
        		    	   
        		       }
        		       }
        		       
        		       
        		       if(tempCount%2 >0){
        					//final count is odd
        		    	   finalCount++;
        				}
        		       
              		}
        	        	
            return;
        }       
        for (int i = startPosition; i <= arr.size()-len; i++){
        	if(result.size()>=3){
            result.set((3-len),arr.get(i)) ;}
          	else{
        		 result.add((3-len),arr.get(i)) ;
        	}
            combinations(arr, len-1, i+1, result);
        }
    }     
    

	public static boolean validtriangle(double x1, double y1, double x2, double y2,
			double x3, double y3) {
		
		double slope1 =0, slope2 =0, slope3=0;
		if((Math.abs(x2 - x1))>0){
		slope1 = (y2 - y1) / (x2 - x1);
		 slope2 = (y3 - y2) / (x2 - x1);}
		if((Math.abs(x3 - x1))>0){
		 slope3 = (y3 - y1) / (x3 - x1);}

		if ((slope1 == slope2) && (slope1 == slope3)) {
			return false;
		}
		return true;
	}

	public static double area(double x1, double y1, double x2, double y2, double x3,
			double y3) {
		return Math
				.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
	}

	public static Double calcSlope(double x1, double y1, double x, double y)
	{
		double slope1a =0;
		if((Math.abs(x1 - x)) >0){
		slope1a = (y1 - y) / (x1 - x);}
		return slope1a;
		
	}
	public static boolean isInside(double x1, double y1, double x2, double y2, double x3,
			double y3, double x, double y) {
		// to do
		double slope1 =0, slope2 =0, slope3=0;
		if((Math.abs(x2 - x1))>0){
		slope1 = (y2 - y1) / (x2 - x1);}
		if((Math.abs(x3 - x2))>0){
		slope2 = (y3 - y2) / (x3 - x2);}
		if((Math.abs(x3 - x1))>0){
		slope3 = (y3 - y1) / (x3 - x1);}

		double slope1a =0, slope2a =0, slope3a=0;
		if((Math.abs(x1 - x)) >0){
		slope1a = (y1 - y) / (x1 - x);}
		if((Math.abs(x2 - x)) >0){
		slope2a = (y2 - y) / (x2 - x);}
		if((Math.abs(x3 - x)) >0){
		slope3a = (y3 - y) / (x3 - x);}
		
		if (slope1 == slope1a || slope2 == slope2a || slope3 == slope3a) {
			return false;
		}
		
		Double A = area(x1, y1, x2, y2, x3, y3);

		/* Calculate area of triangle PBC */
		Double A1 = area(x, y, x2, y2, x3, y3);

		/* Calculate area of triangle PAC */
		Double A2 = area(x1, y1, x, y, x3, y3);

		/* Calculate area of triangle PAB */
		Double A3 = area(x1, y1, x2, y2, x, y);

		/*
		 * Check if sum of A1, A2 and A3 is same as A if sum is same as total
		 * area then point is inside triangle
		 */
		return (A == A1 + A2 + A3);
	}

	public static boolean isInsideTri(double x1, double y1, double x2, double y2, double x3, double y3, double x, double y) {
		
		
		//AP × AB, BP × BC, and CP × CA all have the same sign, then P is inside triangle ABC
		
		double meas1 = ((x-x1)*(y2-y1)) - ((y-y1)*(x2-x1));
		double meas2 = ((x-x2)*(y3-y2)) - ((y-y2)*(x3-x2));
		double meas3 = ((x-x3)*(y1-y3)) - ((y-y3)*(x1-x3));
		
		
		if(meas1>0 && meas2>0 && meas3>0){
			return true;
		}
		if(meas1<0 && meas2<0 && meas3<0){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// Read the the number of test cases T

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		int T = Integer.parseInt(str);

		// Read all the Hints
		String temp[];
		ArrayList<ArrayList<Circle>> dataPoints = new ArrayList<ArrayList<Circle>>();
		for (int i = 0; i < T; i++) {

			str = br.readLine();
			int N = Integer.parseInt(str);
			ArrayList<Circle> dataPoint = new ArrayList<Circle>();

			if (N >= 3) {
				for (int j = 0; j < N; j++) {
					str = br.readLine();
					temp = str.split(" ");
					Circle P1 = new Circle();
					P1.x = Double.parseDouble(temp[0]);
					P1.y = Double.parseDouble(temp[1]);

					dataPoint.add(P1);
				}
			}

			dataPoints.add(dataPoint);

		}

		ArrayList<Circle> result = new ArrayList<Circle>();
		for (ArrayList<Circle> data : dataPoints) {
			combinations(data, 3, 0, result);			
			System.out.println(finalCount);
			finalCount=0;
		}

	}

}
