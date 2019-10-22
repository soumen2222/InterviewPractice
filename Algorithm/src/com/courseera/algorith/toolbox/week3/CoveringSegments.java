package com.courseera.algorith.toolbox.week3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Sortbyend implements Comparator<Segment>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Segment a, Segment b)
    {
        return a.end < b.end ? -1 : a.end == b.end ? 0 : 1;

    }
}


class Segment {
    int start, end;

    Segment(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class CoveringSegments {

    private static Set<Integer> optimalPoints(Segment[] segments) {
        //write your code here
    	Set<Integer> points = new LinkedHashSet<>();
    	List<Segment> pairs = new ArrayList<>();
       
        for (int i = 0; i < segments.length; i++) {
        	Segment s = new Segment(segments[i].start,segments[i].end);
        	pairs.add(s);          
        }
        
        Collections.sort(pairs,new Sortbyend() );       
       
        for (int i =0; i<pairs.size()-1 ; i++ )
        {
        	points.add(pairs.get(i).end);
        	if(pairs.get(i).end >=pairs.get(i+1).start  )
        	{
        		//coinciding point- set the boundary
        		// get minimum of extreme ends 
        		Segment s = new Segment(pairs.get(i+1).start,pairs.get(i).end);        		
        		pairs.set(i+1, s);
        		
        	}
        	else
        	{
        		points.add(pairs.get(i+1).end);
        	}
        }
       
        
        return points;
    }

    
   
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Segment[] segments = new Segment[n];
        for (int i = 0; i < n; i++) {
            int start, end;
            start = scanner.nextInt();
            end = scanner.nextInt();
            segments[i] = new Segment(start, end);
        }
        Set<Integer> points = optimalPoints(segments);              
        
        System.out.println(points.size());
        for (int point : points) {
            System.out.print(point + " ");
        }
    }
}
 
