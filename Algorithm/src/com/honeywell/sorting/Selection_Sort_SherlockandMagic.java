package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Position {

	int X;
	int Y;	
	String A;

	Position(int x, int y, String a) {
		this.X = x;
		this.Y = y;
		this.A =a;
		
	}

}

public class Selection_Sort_SherlockandMagic {

	private static void merge(Position[] inputarr, int start, int mid, int end) {
		// stores the starting position of both parts in temporary variables.

		// System.out.println("start:" + start + "mid:" + mid + "end:" + end);
		int p = start, q = mid + 1;
		Position[] Arr = new Position[(end - start + 1)];
		int k = 0;

		for (long i = start; i <= end; i++) {
			if (p > mid) // checks if first part comes to an end or not .
				Arr[k++] = inputarr[q++];

			else if (q > end) // checks if second part comes to an end or not
				Arr[k++] = inputarr[p++];

			else if (inputarr[p].Y <= inputarr[q].Y) {// checks which part has
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

	private static void merge_sort(Position[] inputarr, int start, int end) {
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
		String line1 = br.readLine();
		String temp[] = line1.split(" ");

		// Get nos of test cases
		int testCases = Integer.parseInt(temp[0]);

		for (int i = 0; i < testCases; i++) {

			String line2 = br.readLine();
			String temp2[] = line2.split(" ");

			// Get nos of lines
			int N = Integer.parseInt(temp2[0]);
			

			// Get all the details of each line
			Position[] Input = new Position[N];
			

			for (int j = 0; j < N; j++) {
				String line3 = br.readLine();
				String temp3[] = line3.split(" ");
				Position p = new Position(Integer.parseInt(temp3[0]),Integer.parseInt(temp3[1]),temp3[2]);				
				Input[j] = p;
			}

			// logic
			calculate(Input,N);

		}

	}

	private static void calculate(Position[] input,int size) {
		
		merge_sort(input,0,size-1);
		boolean found;
		int sum = 0;
		for (int i = 0; i < size; i++)
		{
			found = false;
			for (int j = i+1; j < size; j++)
			{ 
				int count =0;
				
				if ((input[j].X - input[i].X )==0)
				{
					 if((input[i].A.equals("N") && input[j].A.equals("S")))
					 {
						 if((Math.abs(input[j].Y-input[i].Y))%2==0){
						 count++;
						 System.out.println(input[i].X+ " " + input[i].Y + " " + input[j].X +" "+ input[j].Y);
						 }
					 }
				}
				else
				{
					float y = (input[j].Y - input[i].Y );
					float x = (input[j].X - input[i].X );
					float slope = y/x;
					if(slope==0.0 )
					{  if((input[j].X > input[i].X && (input[i].A.equals("E") && input[j].A.equals("W"))) || (input[i].X > input[j].X && (input[i].A.equals("W") && input[j].A.equals("E"))))
					 {   
						if((Math.abs(input[j].X-input[i].X))%2==0)
                        {
						 count++;
						 System.out.println(input[i].X+ " " + input[i].Y + " " + input[j].X +" "+ input[j].Y);
						}
					 }
					}else
					{
						if(slope==-1.0
								&& ((input[j].A.equals("E") && input[i].A.equals("N"))
										|| (input[j].A.equals("S") && input[i].A.equals("W"))))
						{
							count++;
							 System.out.println(input[i].X+ " " + input[i].Y + " " + input[j].X +" "+ input[j].Y);
						}
						
						if(slope==1.0
								&& ((input[j].A.equals("W") && input[i].A.equals("N"))
										|| (input[j].A.equals("S") && input[i].A.equals("E"))))
						{
							count++;
							 System.out.println(input[i].X+ " " + input[i].Y + " " + input[j].X +" "+ input[j].Y);
						}
					}
					
				}
				
				if(count>0 && found)
				{
					sum = sum + count;
				}
				
				if(count>0 && !found )
				{
					found = true;
					count++;
					sum = sum + count;
				}
				
			}
		}
		
		System.out.println(sum);
			
	}


		
}
