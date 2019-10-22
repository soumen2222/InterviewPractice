package com.crackingcoding.algorithm;

import java.util.Scanner;
/*

Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4 bytes,
 write a method to rotate the image by 90 degrees. Can you do this in place? .*/
public class RotateMatrixChapter1 {


	// 1st Column becomes last row
	public static void rotateMatrix(int[][] matrix) {
		
		int[][] rotatedMatrix = new int[matrix[0].length][matrix.length];
		
		for (int column = 0; column < matrix[0].length; column++) { //Column
			for (int row = 0; row < matrix.length; row++) { // row
				
				rotatedMatrix[matrix[0].length-column-1][row] = matrix[row][column];
			}
		}
		System.out.println("Printing ROTATED Matrix");
		printData(rotatedMatrix);
	}


	public static boolean inPlaceRotate(int[][] matrix) {
	
		if (matrix.length== 0 || matrix.length != matrix[0].length) return false;
		
		int n = matrix.length; 
		
		for (int layer = 0; layer < n / 2; layer++) { 
			int first= layer; 
			int last= n - 1 - layer; 
			
			for(int i = first; i < last; i++) { 
				int offset = i - first; 
				int top = matrix[first][i];
				
				//left -> top 
				matrix[first][i]= matrix[last-offset][first]; 
				
				// bottom -> left
				matrix[last-offset][first]=matrix[last][last-offset];
				
		        //right -> bottom 
				
				matrix[last][last-offset] = matrix[i][last]; 
				
		        //top -> right 			
				matrix[i][last] = top; 
				
			}
		}
		System.out.println("Printing ROTATED Matrix");
		printData(matrix);
		return true; 
	}
	
	
	public static void readData() {
		Scanner scanner = new Scanner(System.in);		
		int row = Integer.parseInt(scanner.next());
		int column = Integer.parseInt(scanner.next());
		
		int[][] matrix = new int[row][column];
		
		for(int i =0; i<row ;i++) {
			scanner.nextLine();
			for (int j=0;j<column;j++) {
				matrix[i][j] = Integer.parseInt(scanner.next());
			}
		}
		System.out.println("Printing the Intial Matrix");
		printData(matrix);
		inPlaceRotate(matrix);
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
