package com.soumen.regex;

public class StripFirstIntegers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str = "5678absd1234g345";
		char[] charArray = str.toCharArray();
		String result = "";
        int i=0;
		for (; i < charArray.length; i++) // loop over the complete input
		{
			if (!Character.isDigit(charArray[i]))
			{
				break;
			}
        }
		
		for (int j=i;j< charArray.length; j++)
		{
			result= result + charArray[j];
		}
		System.out.println(result);
		

	}

}
