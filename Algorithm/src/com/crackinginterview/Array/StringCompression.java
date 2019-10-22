package com.crackinginterview.Array;

public class StringCompression {
	
	
	public static String compressString(String str)
	{

		//String Buffer is synchronized where String Builder is not
		
        StringBuilder compressedString = new StringBuilder();
        int num =1;
		for(int i= 0; i<str.length()-1 ; i++ )
		{ 
			if(str.charAt(i+1)==str.charAt(i))
			{
				num++;
			}else
			{
				compressedString.append( str.charAt(i)).append(num);
				num =1;
			}
			
		}
		compressedString.append( str.charAt(str.length()-1)).append(num);
		
		return compressedString.toString();
	}
	
   public static void main(String[] args) {		
		
    	// only for small characters
		System.out.println(compressString("aaaab"));
    }

}
