package com.honeywell.sorting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Border {
	int L;
	int R;
}

public class Benny_Segments {

	private static List<Border> sort(List<Border> input11) {
		for (int i = 0; i < input11.size() - 1; i++) {

			for (int j = 0; j < input11.size() - i - 1; j++) {

				if ((input11.get(j).L > input11.get(j + 1).L)) {
					Border Var1 = input11.get(j);
					Border Var2 = input11.get(j + 1);
					input11.set(j + 1, Var1);
					input11.set(j, Var2);
				}
			}

		}

		return input11;
	}

	private static void result(List<Border> input1, int FabNo) {
		List<Border> sorted = sort(input1);
		String output = "No";
				
		for (int i = 0; i < sorted.size(); i++) {
			int maxRight = sorted.get(i).L + FabNo;
			int curRight = sorted.get(i).R;
					
			for (int j = 0; j < sorted.size(); j++) {
				if (sorted.get(j).L <= curRight && sorted.get(j).R <= maxRight   && sorted.get(j).L > sorted.get(i).L) {
					curRight = Math.max(curRight, sorted.get(j).R);
				}
			}
			if (curRight == maxRight) {
				output = "Yes";
				break;
			}
		}
	System.out.println(output);
	}
		
		

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		int tests = Integer.parseInt(line);

		for (int x = 0; x < tests; x++) {
			
			String str = br.readLine();
			String temp[] = str.split(" ");
			int Roads = Integer.parseInt(temp[0]);
			int FabNo = Integer.parseInt(temp[1]);

			List<Border> input = new ArrayList<Border>();
			for (int i = 0; i < Roads; i++) {
				String str1 = br.readLine();
				String temp1[] = str1.split(" ");
				Border f = new Border();
				f.L = Integer.parseInt(temp1[0]);
				f.R = Integer.parseInt(temp1[1]);
				input.add(f);
			}
        
			result(input,FabNo);
		}
	}

}
