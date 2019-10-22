package com.soumen.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
//from w  ww  . java 2s  .co m
public class Regex3 {
  public static void main(String[] args) {
    // A group of 3 digits followed by 7 digits.
    String regex = "\\b(\\d{3})(\\d{3})(\\d{4})\\b";

    // Compile the regular expression
    Pattern p = Pattern.compile(regex);

    String source = "12345678, 12345, and 9876543210";
    String newStr = source.replaceAll(regex, "SOU");
    System.out.println("New String:  " + newStr);

    // Get the Matcher object
    Matcher m = p.matcher(source);

    // Start matching and display the found area codes
    while (m.find()) {
      String phone = m.group();
      String areaCode = m.group(3);
      System.out.println("Phone: " + phone + ", Area  Code:  " + areaCode);
    }
  }
}