package com.soumen.regex;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
/*from   w w  w .j  a  v a 2s. c o  m*/
public class Regex2 {
  public static void main(String[] args) {
    String regex = "[abc]@.";
    String source = "abc@example.com, cda@example.com";
    findPattern(regex, source);
  }
  public static void findPattern(String regex, String source) {
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(source);
    ArrayList<String> lists = new ArrayList<String>();
    System.out.println("Regex:" + regex);
    System.out.println("Text:" + source);
    while (m.find()) {
      System.out.println("Matched  Text:" + m.group() + ", Start:" + m.start()
          + ", " + "End:" + m.end());
      lists.add(m.group());
    }
    
    for (String list : lists) {
		System.out.println("Items is list are: " + list);
	}
  }
}