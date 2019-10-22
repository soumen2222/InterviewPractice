package com.crackingcoding.algorithm;

import java.util.Scanner;
/*

Zero Matrix: Write an algorithm such that if an 
element in an MxN matrix is 0, its entire row and column are set to 0.*/
public class ZeroMatrixChapter1 {
	
	public static void setZeros(int[][] matrix) {

		boolean[] row = new boolean[matrix.length];
		boolean[] column = new boolean[matrix[0].length];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					row[i] = true;
					column[j] = true;
				}
			}			
		}
		for (int i = 0; i < row.length; i++) {
			if(row[i]) nullifyRow(matrix,i);
		}
		
		for (int i = 0; i < column.length; i++) {
			if(column[i]) nullifyColumn(matrix,i);
		}
		
		System.out.println("Printing the Zero Matrix");
		printData(matrix);
	}
	private static void nullifyColumn(int[][] matrix, int column) {
		
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][column]=0;
		}
		
	}
	private static void nullifyRow(int[][] matrix, int row) {
		for (int j = 0;j < matrix[0].length; j++) {
			matrix[row][j]=0;
		}
		
	}
	public static void readData() {
		Scanner scanner = new Scanner(System.in);		
		int m = Integer.parseInt(scanner.next());
		int n = Integer.parseInt(scanner.next());
		
		int[][] matrix = new int[n][m];
		
		for(int i =0; i<n ;i++) {
			scanner.nextLine();
			for (int j=0;j<m;j++) {
				matrix[i][j] = Integer.parseInt(scanner.next());
			}
		}
		System.out.println("Printing the Intial Matrix");
		printData(matrix);
		setZeros(matrix);
	}
	

	public static void printData(int[][] matrix) {		
		
		for(int i =0; i<matrix.length ;i++) {			
			for (int j=0;j<matrix[0].length;j++) {
				System.out.print(matrix[i][j] + " ") ;
			}
			System.out.println();
		}
	}
	
	public static void main( String args[]) {
		readData();
	}

}
