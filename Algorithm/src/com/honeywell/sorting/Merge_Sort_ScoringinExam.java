package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class IntPair1 {

	int T;
	int S;
	long sum;

	IntPair1(int x, int y) {
		this.T = x;
		this.S = y;
	}

}

public class Merge_Sort_ScoringinExam {

	private static void merge(IntPair1[] inputarr, int start, int mid, int end) {
		// stores the starting position of both parts in temporary variables.

		// System.out.println("start:" + start + "mid:" + mid + "end:" + end);
		int p = start, q = mid + 1;
		IntPair1[] Arr = new IntPair1[(end - start + 1)];
		int k = 0;

		for (long i = start; i <= end; i++) {
			if (p > mid) // checks if first part comes to an end or not .
				Arr[k++] = inputarr[q++];

			else if (q > end) // checks if second part comes to an end or not
				Arr[k++] = inputarr[p++];

			else if (inputarr[p].S >= inputarr[q].S) {// checks which part has
														// smaller element.
				// System.out.println("Smaller Element:" +A[
				// p ] + " " + A[ q ] );
				Arr[k++] = inputarr[p++];
			} else {
				Arr[k++] = inputarr[q++];

			}
		}
		for (int a = 0; a < k; a++) {
			/*
			 * Now the real array has elements in sorted manner including both
			 * parts.
			 */
			inputarr[start++] = Arr[a];
		}
	}

	private static void merge_sort(IntPair1[] inputarr, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2; // defines the current array in 2 parts
											// .
			merge_sort(inputarr, start, mid); // sort the 1st part of array .
			merge_sort(inputarr, mid + 1, end); // sort the 2nd part of array.

			// merge the both parts by comparing elements of both the parts.
			merge(inputarr, start, mid, end);
		}
	}

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		String temp[] = line.split(" ");
		int N = Integer.parseInt(temp[0]);
		int Q = Integer.parseInt(temp[1]);
		IntPair1[] inputarr = new IntPair1[N];
		int[] Qnos = new int[Q];
		int[] timearr = new int[N];
		int[] scorearr = new int[N];

		
		// Get all times
		String str1 = br.readLine();
		String temp1[] = str1.split(" ");
		for (int i = 0; i < N; i++) {			
			timearr[i] = Integer.parseInt(temp1[i]);

		}

		//Get all scores
		String str2 = br.readLine();
		String temp2[] = str2.split(" ");
		
		for (int i = 0; i < N; i++) {
			
			scorearr[i] = Integer.parseInt(temp2[i]);

		}

		//Populate the pair
		for (int i = 0; i < N; i++) {

			IntPair1 input = new IntPair1(timearr[i], scorearr[i]);
			inputarr[i] = input;

		}
		
		// Get all Q's
		for (int i = 0; i < Q; i++) {

			String str3 = br.readLine();
			String temp3[] = str3.split(" ");
			Qnos[i] = Integer.parseInt(temp3[0]);

		}

		merge_sort(inputarr, 0, N - 1);
		
		long sum =0;
		for (int i = 0; i < N; i++) {

			IntPair1 input = new IntPair1(inputarr[i].T, inputarr[i].S);
			sum=sum + inputarr[i].T;
			input.sum= sum;
			inputarr[i] = input;
		}
		
		for (int i = 0; i < Q; i++)
		{  			
			int num = Qnos[i];
			System.out.println(inputarr[num-1].sum);	
	}
}
}
