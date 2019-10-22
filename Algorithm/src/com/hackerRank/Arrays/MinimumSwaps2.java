package com.hackerRank.Arrays;

public class MinimumSwaps2 {

	public static void main(String[] args) {

		int[] input = { 1, 3, 5, 2, 4, 6, 8 };
		System.out.println(optimisedMinimumSwaps(input));

	}

	public static int minimumSwaps(int[] arr) {

		int swapCount = 0;
		for (int i = 0; i < arr.length; i++) {
			int minVal = Integer.MAX_VALUE;
			int minIndex = i;
			for (int j = i; j < arr.length; j++) {
				if (arr[j] < minVal) {
					minVal = arr[j];
					minIndex = j;
				}
			}

			if (minIndex != i) {
				swap(arr, i, minIndex);
				swapCount++;
			}
		}
		return swapCount;
	}

	public static int optimisedMinimumSwaps(int[] arr) {

		int swapCount = 0;
		for (int i = 0; i < arr.length; i++) {
			while (arr[i] != i + 1 && arr[i] <= arr.length) {
				swap(arr, i, arr[i] - 1);
				swapCount++;
			}
		}
		return swapCount;
	}

	private static void swap(int[] items, int a, int b) {
		int temp = items[a];
		items[a] = items[b];
		items[b] = temp;
	}
}
