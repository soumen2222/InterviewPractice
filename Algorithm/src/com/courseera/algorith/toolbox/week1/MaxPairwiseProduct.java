package com.courseera.algorith.toolbox.week1;

import java.util.*;
import java.io.*;

public class MaxPairwiseProduct {
	static long getMaxPairwiseProduct(long[] numbers) {		
		int index =0;
		long MaxNum =0;
		long MaxNum2 =0;
		int n = numbers.length;
		
		for (int i = 0; i < n; ++i)
		{
			if(MaxNum <numbers[i] )
			{
				index = i;
				MaxNum = numbers[i];
			}	
			
		}
		
		for (int i = 0; i < n; ++i)
		{
			if(index!=i )
			{
				MaxNum2 = Math.max(MaxNum2,	numbers[i]);
			}
			
		}
		
		return MaxNum*MaxNum2;
	}

	public static void main(String[] args) {
		FastScanner scanner = new FastScanner(System.in);
		int n = scanner.nextInt();
		long[] numbers = new long[n];
		for (int i = 0; i < n; i++) {
			numbers[i] = scanner.nextInt();
		}
		System.out.println(getMaxPairwiseProduct(numbers));
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