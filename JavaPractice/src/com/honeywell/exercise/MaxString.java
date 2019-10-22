package com.honeywell.exercise;

import java.util.ArrayList;
import java.util.List;

public class MaxString {
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//input string
		String in = "abcdaedbgfghaij";
		char[] ch=in.toCharArray();
		List<Character> array = new ArrayList<Character>();
		StringBuffer str = new StringBuffer();
		String finalS = new String();
		
		for(int i=0;i<ch.length;i++)
		{
			for(int j=i;j<ch.length;j++)
			{
				if (!array.contains(ch[j]))
				{
					str.append(ch[j]);
					array.add(ch[j]);
					
				}
				else
				{
					if ((str.length()>finalS.length()) &&(str!=null))
					{
						finalS = str.toString();
						
					}
					str=new StringBuffer();
					array.clear();
					break;
					
				}
				
				
				
			}
		}
		
		System.out.println(finalS);

	}

}
