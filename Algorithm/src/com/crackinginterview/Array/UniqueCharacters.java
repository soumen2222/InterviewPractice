package com.crackinginterview.Array;

public class UniqueCharacters {
	
	public static boolean isUniqueChars( String str)
	{
		if(str.length() >256) return false;
		
		int checker =0;
		
		for(int i= 0; i<str.length() ; i++ )
		{
			int val = str.charAt(i)- 'a';
			
			if (( checker & (1 << val)) > 0)
			{
				return false;
			}
			checker |= (1 << val);
		}
		
		return true;
	}
	
    public static void main(String[] args) {		
		
    	// only for small characters
		System.out.println(isUniqueChars("zoumens"));
    }
		

}
