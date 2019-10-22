package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Row {

	String Name;
	int Age;
	String Address;
	String Hometown;

	Row(String a, int b, String c, String d) {
		this.Name = a;
		this.Age = b;
		this.Address = c;
		this.Hometown = d;
	}

}

class Pair {

	int x;
	int y;

	Pair(int a, int b) {
		this.x = a;
		this.y = b;

	}

}

public class Selection_Sort_Writeachecker {

	private static int count = 0;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		String temp[] = line.split(" ");
		int N = Integer.parseInt(temp[0]);
		Row[] row = new Row[N + 1];
		List<Pair> pair = new ArrayList<Pair>();
	

		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			String temp1[] = str.split(" ");
			Row r = new Row(temp1[0], Integer.parseInt(temp1[1]), temp1[2], temp1[3]);
			row[i + 1] = r;
		}

		for (int i = 1; i < N + 1; i++) {

			for (int j = i + 1; j < N + 1; j++) {
				if ((row[i].Address.equals(row[j].Address)) || (row[i].Name.equals(row[j].Name))
						|| (row[i].Hometown.equals(row[j].Hometown)) || (row[i].Age == row[j].Age)) {
					count++;
					Pair p = new Pair(i, j);
					pair.add(p);
				}
			}
		}
		
		System.out.println(count);
		for (int i = 0; i < pair.size(); i++)
		{
			System.out.println(pair.get(i).x + " " + pair.get(i).y);
		}

	}

}
