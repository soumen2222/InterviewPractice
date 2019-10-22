package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Teacher {

	List<Student> students ;
	String TName;
	
	Teacher(String x, List<Student> y) {
		this.TName = x;
		this.students = y;
	}

}

class Student {

	String SName;
	String TName;
	int Age;
	
	Student(String x, String y, int z) {
		this.TName = x;
		this.SName = y;
		this.Age = z;
		
	}

}

public class Merge_Sort_MonksSchool {

	private static void mergeTname(Teacher[] inputarr, int start, int mid, int end) {
		int p = start, q = mid + 1;
		Teacher[] Arr = new Teacher[(end - start + 1)];
		int k = 0;

		for (long i = start; i <= end; i++) {
			if (p > mid) // checks if first part comes to an end or not .
				Arr[k++] = inputarr[q++];

			else if (q > end) // checks if second part comes to an end or not
				Arr[k++] = inputarr[p++];

			else if (inputarr[p].TName.compareTo(inputarr[q].TName) < 0) {  //Smaller first
				
				Arr[k++] = inputarr[p++];
			} else {
				Arr[k++] = inputarr[q++];

			}
		}
		for (int a = 0; a < k; a++) {
			inputarr[start++] = Arr[a];
		}
	}
	
	private static void mergeSage(List<Student> students, int start, int mid, int end) {
		int p = start, q = mid + 1;
		List<Student> Arr = new ArrayList<Student>();		
		
		for (int i = start; i <= end; i++) {
			if (p > mid)				// checks if first part comes to an end or not .
				Arr.add(students.get(q++));				

			else if (q > end) // checks if second part comes to an end or not
				Arr.add(students.get(p++));

			else if (students.get(p).Age <= students.get(q).Age) {// smaller age first
				Arr.add(students.get(p++));
			} else {
				Arr.add(students.get(q++));	

			}
		}
		for (int a = 0; a < Arr.size(); a++) {
			students.set(start++, Arr.get(a));			
		}
	}

	private static void merge_sort_Tname(Teacher[] inputarr, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2; // defines the current array in 2 parts
											// .
			merge_sort_Tname(inputarr, start, mid); // sort the 1st part of array .
			merge_sort_Tname(inputarr, mid + 1, end); // sort the 2nd part of array.

			// merge the both parts by comparing elements of both the parts.
			mergeTname(inputarr, start, mid, end);
		}
	}
	
	private static void merge_sort_Sage(List<Student> students, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2; // defines the current array in 2 parts
											// .
			merge_sort_Sage(students, start, mid); // sort the 1st part of array .
			merge_sort_Sage(students, mid + 1, end); // sort the 2nd part of array.

			// merge the both parts by comparing elements of both the parts.
			mergeSage(students, start, mid, end);
		}
	}
	

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		String temp[] = line.split(" ");
		int N = Integer.parseInt(temp[0]);
		int M = Integer.parseInt(temp[1]);
		
		Teacher[] teacherlist = new Teacher[N];
		//get all Teachers
		for (int i = 0; i < N; i++) {

			String str1 = br.readLine();
			String temp1[] = str1.split(" ");
			Teacher t = new Teacher(temp1[0], null);
			teacherlist[i] =t;
		}
		
		//Get all Students
		Student[] studentlist = new Student[M];
		for (int i = 0; i < M; i++) {

			String str1 = br.readLine();
			String temp1[] = str1.split(" ");
			Student s = new Student(temp1[0], temp1[1], Integer.parseInt(temp1[2]) );
			studentlist[i] =s;
		}
		
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < M; j++)
			{
				if(teacherlist[i].TName.equals(studentlist[j].TName))
				{
					if(teacherlist[i].students!=null)
					{
						teacherlist[i].students.add(studentlist[j]);
					}
					else
					{
						List<Student> students = new ArrayList<Student>();
						students.add(studentlist[j]);
						Teacher t = new Teacher(teacherlist[i].TName, students);
						teacherlist[i] =t;
					}
				}
				
			}
		}
		
		merge_sort_Tname(teacherlist, 0, N - 1);
		
		for (int i = 0; i < N; i++)
		{
			merge_sort_Sage(teacherlist[i].students,0,teacherlist[i].students.size()-1);
		}
		
		
		for (int i = 0; i < N; i++)
		{
			System.out.println(teacherlist[i].TName);
			for (int j = 0; j < teacherlist[i].students.size(); j++)
			{
				System.out.println(teacherlist[i].students.get(j).SName + " " + teacherlist[i].students.get(j).Age);
				
			}
			
		}
		
}
}
