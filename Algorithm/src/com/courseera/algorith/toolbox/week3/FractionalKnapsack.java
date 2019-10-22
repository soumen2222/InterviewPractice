package com.courseera.algorith.toolbox.week3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class KnapsackValues
{
	double values;
	double weights;
	double perUnit;
	
}

class SortbyperUnit implements Comparator<KnapsackValues>
{
   
    public int compare(KnapsackValues b, KnapsackValues a)
    {
        return a.perUnit < b.perUnit ? -1 : a.perUnit == b.perUnit ? 0 : 1;

    }
}

public class FractionalKnapsack {
    private static double getOptimalValue(double capacity, double[] values, double[] weights) {
        double total = 0;
        //write your code here
        List<KnapsackValues> knapsackValues = new ArrayList<>();
        for (int i =0 ; i <values.length ;i++)
        {
        	KnapsackValues v = new KnapsackValues();
        	v.values = values[i];
        	v.weights= weights[i];
        	v.perUnit =  (double)(values[i]/weights[i]);
        	knapsackValues.add(v);
        }
        
        Collections.sort(knapsackValues,new SortbyperUnit() );
        
        for (KnapsackValues kv : knapsackValues) {
			total = total + (kv.perUnit * Math.min(capacity, kv.weights));
			capacity = capacity - Math.min(capacity, kv.weights);
			if(capacity==0)
			{
				break;
			}
        	
		}

        return total;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        double capacity = scanner.nextDouble();
        double[] values = new double[n];
        double[] weights = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextDouble();
            weights[i] = scanner.nextDouble();
        }
        System.out.println(getOptimalValue(capacity, values, weights));
    }
} 
