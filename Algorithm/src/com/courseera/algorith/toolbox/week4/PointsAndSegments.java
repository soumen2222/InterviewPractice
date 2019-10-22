package com.courseera.algorith.toolbox.week4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


class SortbyStartEnd implements Comparator<Points>
{
    // Used for sorting in ascending order 
   
    public int compare(Points a, Points b)
    {    	
    	if(a.start < b.start)
    	{
    		return -1;
    	}else
    		if(a.start == b.start)
    		{
    			return (a.end.compareTo(b.end) > 0) ? 1 : (a.end.compareTo(b.end) == 0) ? 0 : -1;    				
    		}else
    		{
    			return 1;
    		}  
    }
}


class Points {
    int start;
    String end;

    Points(int start, String end) {
        this.start = start;
        this.end = end;
    }
}
public class PointsAndSegments {

    private static Long[] fastCountSegments(int[] starts, int[] ends, int[] points) {
       
        
        List<Points> pairs = new ArrayList<>();  //(2* starts.length) + points.length
        
        for(int i =0 ; i <starts.length ; i++)
{
        	Points p = new Points(starts[i],"l");
        	pairs.add(p);
	
}
        
        for(int i =0 ; i <ends.length ; i++)
        {
                	Points p = new Points(ends[i],"r");
                	pairs.add(p);
        	
        }
        for(int i =0 ; i <points.length ; i++)
        {
                	Points p = new Points(points[i],"p");
                	pairs.add(p);
        	
        }
        Collections.sort(pairs,new SortbyStartEnd());  
        
        long segment = 0;
        Map<Integer, Long> sgmentpointmap = new HashMap<>();
        for(int j =0; j <pairs.size() ;j++)
        {
        	if(pairs.get(j).end.equals("l")) {//start of a new segment
        		segment++;
        	}else {if(pairs.get(j).end.equals("r")){//end of a new segment
        			segment--; }
        		   else {        			//It is a point
        			sgmentpointmap.put(pairs.get(j).start,segment);}
                   }
        }
        
        Long[] cnt = new Long[points.length];
        for (int i =0; i < points.length ; i++)
        {
        	cnt[i] = sgmentpointmap.get(points[i]);
        }
		return cnt;
    }

    private static int[] naiveCountSegments(int[] starts, int[] ends, int[] points) {
        int[] cnt = new int[points.length];
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < starts.length; j++) {
                if (starts[j] <= points[i] && points[i] <= ends[j]) {
                    cnt[i]++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        int[] starts = new int[n];
        int[] ends = new int[n];
        int[] points = new int[m];
        for (int i = 0; i < n; i++) {
            starts[i] = scanner.nextInt();
            ends[i] = scanner.nextInt();
        }
        for (int i = 0; i < m; i++) {
            points[i] = scanner.nextInt();
        }
        //use fastCountSegments
        Long[] cnt = fastCountSegments(starts, ends, points);
        for (Long x : cnt) {
            System.out.print(x + " ");
        }
    }
}

