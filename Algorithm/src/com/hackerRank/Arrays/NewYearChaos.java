package com.hackerRank.Arrays;

public class NewYearChaos {

	public static void main(String[] args) {

		int[] input = { 2,1,5,3,4 };
		newYearChaos2(input);
	}

	public static void newYearChaos2(int[] arr) {
		int SumswapCount = 0;
		boolean tooChaotic = false;
		for (int i = arr.length - 1; i >= 0; i--) {
			int j = i;
			while (arr[j] != i + 1 && arr[j] <= arr.length) {
				j--;
			}
			if ((i - j) > 2) {
				tooChaotic = true;
				break;
			}
			while ((i - j) > 0) {
				swap(arr, j, j + 1);
				j++;
				SumswapCount++;
			}

		}

		if (tooChaotic)
			System.out.println("Too chaotic");
		else
			System.out.println(SumswapCount);

	}

	private static void swap(int[] items, int a, int b) {
		int temp = items[a];
		items[a] = items[b];
		items[b] = temp;
	}

}
