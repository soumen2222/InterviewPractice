package com.soumen.regex;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/* ww  w .ja  v a  2 s.c  o m*/
public class Regex5 {
  public static void main(String[] args) {
    //String regex = "[a-z](\\d+)[a-z]";
    String regex = "^[0-9]";
    StringBuffer sb = new StringBuffer();
    String replacementText = "";
    String matchedText = "";
    String text = "52345abcde6789tref";
   
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(text);
    if (m.find())
    { 
    	ArrayList<String> matchedText1 = new ArrayList<String>();
    	String regex1 = "([a-z])(\\d+)"; 
    	Pattern p1 = Pattern.compile(regex1);
        Matcher m1 = p1.matcher(text);
    	    
      while (m1.find()) {
       
       matchedText1.add(m1.group());
       System.out.println(m1.group());
          }
      
     
		
	}
    }

   
  }
