package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Merge_Sort_exp {
	private static long count = 0;

	private static void merge(long[] a2, long start, long mid, long end) {
		// stores the starting position of both parts in temporary variables.

		// System.out.println("start:" + start + "mid:" + mid + "end:" + end);
		long p = start, q = mid + 1;
		long Arr[] = new long[(int) (end - start + 1)];
		int k = 0;

		for (long i = start; i <= end; i++) {
			if (p > mid) // checks if first part comes to an end or not .
				Arr[k++] = a2[(int) q++];

			else if (q > end) // checks if second part comes to an end or not
				Arr[k++] = a2[(int) p++];

			else if (a2[(int) p] <= a2[(int) q]) {// checks which part has smaller element.
									// System.out.println("Smaller Element:" +A[
									// p ] + " " + A[ q ] );
				Arr[k++] = a2[(int) p++];
			} else {
					Arr[k++] = a2[(int) q++];				
				count = count + 1 + (mid - p);			

			}
		}
		for (long a = 0; a < k; a++) {
			/*
			 * Now the real array has elements in sorted manner including both
			 * parts.
			 */
			a2[(int) start++] = Arr[(int) a];
		}
	}

	private static void merge_sort(long A[], long start, long end) {
		if (start < end) {
			long mid = (start + end) / 2; // defines the current array in 2 parts
											// .
			merge_sort(A, start, mid); // sort the 1st part of array .
			merge_sort(A, mid + 1, end); // sort the 2nd part of array.

			// merge the both parts by comparing elements of both the parts.
			merge(A, start, mid, end);
		}
	}

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		String temp[] = line.split(" ");
		long N = Long.parseLong(temp[0]);

		String str = br.readLine();
		String temp1[] = str.split(" ");
		long[] input = new long[(int) (N + 1)];

		for (int i = 0; i < N; i++) {
			input[i] = Long.parseLong(temp1[i]);
		}

		merge_sort(input, 0, N - 1);

		System.out.print(count);

	}

}
