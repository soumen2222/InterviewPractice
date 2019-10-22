package com.honeywell.hackerearth;

/* IMPORTANT: Multiple classes and nested static classes are supported */

/*
 * uncomment this if you want to read input.
//imports for BufferedReader
import java.io.BufferedReader;
import java.io.InputStreamReader;

//import for Scanner and other utility classes
import java.util.*;
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class pro {
    public static void main(String args[] ) throws Exception {
       

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line1 = br.readLine();
        double pmb = Double.parseDouble(line1);
        String line2 = br.readLine();
        double pab = Double.parseDouble(line2);
        String line3 = br.readLine();
        double p1 = Double.parseDouble(line3);
        double val = p1*(pmb*(1-pab) + pab *(1-pmb));
        DecimalFormat df = new DecimalFormat("####0.000000");
        System.out.println("Value: " + df.format(val));        
    }
}
