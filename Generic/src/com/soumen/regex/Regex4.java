package com.soumen.regex;

import java.util.regex.Pattern;
//w  ww .  j  a v a  2 s . c  om
public class Regex4 {
public static void main(String args[]) {

String regex = "ad*";
String input = "add";

boolean isMatch = Pattern.matches(regex, input);
System.out.println(isMatch);
}
}