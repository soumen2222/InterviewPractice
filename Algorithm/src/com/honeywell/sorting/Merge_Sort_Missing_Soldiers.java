package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class IntPair {

	int L;
	int R;

	IntPair(int x, int y) {
		this.L = x;
		this.R = y;
	}

}

public class Merge_Sort_Missing_Soldiers {

	private static void merge(IntPair[] inputarr, int start, int mid, int end) {
		// stores the starting position of both parts in temporary variables.

		// System.out.println("start:" + start + "mid:" + mid + "end:" + end);
		int p = start, q = mid + 1;
		IntPair[] Arr = new IntPair[(end - start + 1)];
		int k = 0;

		for (long i = start; i <= end; i++) {
			if (p > mid) // checks if first part comes to an end or not .
				Arr[k++] = inputarr[q++];

			else if (q > end) // checks if second part comes to an end or not
				Arr[k++] = inputarr[p++];

			else if (inputarr[p].L <= inputarr[q].L) {// checks which part has
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

	private static void merge_sort(IntPair[] inputarr, int start, int end) {
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
		IntPair[] inputarr = new IntPair[N];
		

		for (int i = 0; i < N; i++) {

			String str = br.readLine();
			String temp1[] = str.split(" ");
            IntPair input = new IntPair(Integer.parseInt(temp1[0]), Integer.parseInt(temp1[0]) + Integer.parseInt(temp1[2]));
            inputarr[i] =input;

		}

		merge_sort(inputarr, 0, N - 1);
		int count = 0;
		int sum =0;
		int oldRight =0;
		int oldLeft =0;
		for (int i = 0; i < N; i++) {			
			
			int curRight = inputarr[i].R;
			int curLeft = inputarr[i].L;
						    
			  
			  //find current left
			  if (curLeft <= oldRight)
				{
					//extend the number
				  curLeft=oldLeft;					
				}
			 
			//find current right
				if (curLeft <= oldRight  && oldRight  > curRight)
					{
											
						curRight = oldRight;						
					}
			sum = curRight- curLeft +1;
			
			if (oldRight!=curRight && oldLeft!=curLeft )
			{
				
				count = count +sum;
			}
			else
			{ 
				if (oldRight!=curRight && oldLeft==curLeft )
				{
					count =sum;
				}
				
			}
			oldRight =curRight;
			oldLeft = curLeft;
			
		}

		
		System.out.println(count);
	}

}
