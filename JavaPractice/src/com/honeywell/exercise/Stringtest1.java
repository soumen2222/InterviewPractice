package com.honeywell.exercise;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stringtest1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String values = null;
        values ="%one%%two%%%three%%%%";
	     String[] split = values.split("[one,two,three]");

	     for (String val : split) {
			System.out.println(val);
		}

	     String text = "one (1), two (2), three (3)";
	     ArrayList<String> tokens = new ArrayList<String>();
			Pattern tokSplitter = Pattern.compile("[^, ]+");
			Matcher m = tokSplitter.matcher(text);

			while (m.find()) {
				tokens.add(m.group());
			}

			for (String tok : tokens) {
				System.out.println(tok);
			}

	}

}
