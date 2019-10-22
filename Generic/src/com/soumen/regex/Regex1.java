package com.soumen.regex;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 String regex   = "[a-z]@.";
		    Pattern p  = Pattern.compile(regex);
		    String str = "abc@yahoo.com,123@cnn.com,abc@google.com";
		    Matcher  m   = p.matcher(str);
		    
		    
		    while(m.find())
		    {
		    	System.out.println("Matched  Text:" + m.group() + ", Start:" + m.start()
			            + ", " + "End:" + m.end());
		    	System.out.println("The found string is :" + m.group());
		    }

	}

}
