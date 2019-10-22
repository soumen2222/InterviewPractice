package com.courseera.algorith.toolbox.week5;


import java.util.*;

public class PrimitiveCalculator {
	
	private static final int NO_OP =0;
	private static final int SUB_1 =1;
	private static final int DIV_2 =2;
	private static final int DIV_3 =3;
	
    private static List<Integer> optimal_sequence(int n) {
        List<Integer> sequence = new ArrayList<Integer>();
        while (n >= 1) {
            sequence.add(n);
            if (n % 3 == 0) {
                n /= 3;
            } else if (n % 2 == 0) {
                n /= 2;
            } else {
                n -= 1;
            }
        }
        Collections.reverse(sequence);
        return sequence;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> sequence = optimal_sequence1(n);
        System.out.println(sequence.size() - 1);
        for (Integer x : sequence) {
            System.out.print(x + " ");
        }
    }
    
    private static int min(int a,int b)
    {
    	if(a>b)
    		return b;
    	else
    		return a;
    }
  
    private static List<Integer> optimal_sequence1(int n)
    {
    	List<Integer> sequence1 = new ArrayList<>();
    	int[] out = new int[n+1];
    	int[] res = new int[n+1];
        out[1] = 0;
        res[1] = NO_OP;
         for(int i=2; i <= n; i++) {
                    out[i] = out[i-1] + 1;
                    res[i] = SUB_1;
                    
                    if (i%2 == 0) {
                        if ((out[i/2] + 1) < out[i]) {
                            res[i] = DIV_2;
                        }
                        out[i] = min(1 + out[i/2], out[i]);
                    }
                    if (i%3 == 0) {
                        if ((out[i/3] + 1) < out[i]) {
                            res[i] = DIV_3;
                        }
                        out[i] = min(1 + out[i/3], out[i]);
                    }
            }
            
            // Print the sequence, we already know the number of steps to print
            sequence1.add(n); 
            for (int i = out[n]; i > 0; i--) {
            	     
                if (res[n] == SUB_1) {
                    n = n - 1;                    
                } else if (res[n] == DIV_2) {
                    n = n / 2;
                   } else {
                    n = n / 3;                   
                }
                sequence1.add(n);
            }
           
            
           Collections.reverse(sequence1);
           return sequence1;
    }
    
    
    
}

