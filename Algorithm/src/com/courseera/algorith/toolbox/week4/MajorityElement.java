package com.courseera.algorith.toolbox.week4;
import java.util.*;
import java.io.*;

public class MajorityElement {
    private static int getMajorityElement(int[] a) {
    	HashMap<Integer,Integer> counts = new HashMap<>();
    	
    	for(int i =0; i< a.length ; i++)
    	{
    		if(counts.containsKey(a[i]))
    		{
    			counts.put(a[i], counts.get(a[i]) + 1);
    		}
    		else
    			counts.put(a[i], 1);
    		
    	}
         int max = 0;
           for (Map.Entry<Integer, Integer> entry : counts.entrySet())
    			{
    			   if(entry.getValue() >max)
    			   {
    				   max = entry.getValue();
    			   }
    			}
           if( max*2 > a.length )
		    	return 1;
		    else
		    	return 0; 
    }

    private static int findMajorityElement(int[] a) { 
        int count = 0, i, majorityElement = 0;
        for (i = 0; i < a.length; i++) {
            if (count == 0)
                majorityElement = a[i];
            if (a[i] == majorityElement) 
                count++;
            else
                count--;
        }
        count = 0;
        for (i = 0; i < a.length; i++)
            if (a[i] == majorityElement)
                count++;
        if (count*2 > a.length)
            return 1;
        return 0;
    }
    public static void main(String[] args) {
        FastScanner scanner = new FastScanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        System.out.println(getMajorityElement(a));
        
    }
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(InputStream stream) {
            try {
                br = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }
}

