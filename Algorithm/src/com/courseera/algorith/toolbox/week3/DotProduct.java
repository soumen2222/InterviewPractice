package com.courseera.algorith.toolbox.week3;





import java.util.*;

class ProfitPerClick
{
	long profitPerClick;
}

class AverageClick
{
	long averageClicks;
}

class SortbyPerClick implements Comparator<ProfitPerClick>
{
    
    public int compare(ProfitPerClick b, ProfitPerClick a)
    {
        return a.profitPerClick < b.profitPerClick ? -1 : a.profitPerClick == b.profitPerClick ? 0 : 1;

    }
}

class SortbyPerAvgClick implements Comparator<AverageClick>
{
    
    public int compare(AverageClick b, AverageClick a)
    {
        return a.averageClicks < b.averageClicks ? -1 : a.averageClicks == b.averageClicks ? 0 : 1;

    }
}


public class DotProduct {
    private static long maxDotProduct(long[] a, long[] b) {
        //write your code here
        long result = 0;
        List<ProfitPerClick> profitValues = new ArrayList<>();
        List<AverageClick> avgClickValues = new ArrayList<>();
        for (int i = 0; i < a.length; i++)
        { 
          ProfitPerClick p = new ProfitPerClick();
          p.profitPerClick=a[i];
          AverageClick ac = new AverageClick();
          ac.averageClicks =b[i];
          profitValues.add(p);
          avgClickValues.add(ac);        
        	
        }
        
        Collections.sort(profitValues,new SortbyPerClick() );
        Collections.sort(avgClickValues,new SortbyPerAvgClick() );
     
        for (int i = 0; i < a.length; i++) {
            result += profitValues.get(i).profitPerClick * avgClickValues.get(i).averageClicks;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextLong();
        }
        long[] b = new long[n];
        for (int i = 0; i < n; i++) {
            b[i] = scanner.nextLong();
        }
        System.out.println(maxDotProduct(a, b));
    }
}

