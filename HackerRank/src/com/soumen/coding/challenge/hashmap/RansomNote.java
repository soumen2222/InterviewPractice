import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class RansomNote {

    // Complete the checkMagazine function below.
    static void checkMagazine(String[] magazine, String[] note) {
        Map<String, Integer> magazinemap = new HashMap<>();
        boolean val = false;
    	
    	for (String mag : magazine) {
			
    		if(magazinemap.containsKey(mag)) {
    			magazinemap.put(mag, magazinemap.get(mag)+1);
    		}else {
    			magazinemap.put(mag,1);
    		}
		}
    	for (String not : note) {
    		if(!magazinemap.containsKey(not)) {
    			System.out.println("No");
    			val=true;
    			break;
    		}     				
    		
    		if(magazinemap.get(not)<=0)
    		{
    			System.out.println("No");
    			val=true;
    			break;
    		}
    		magazinemap.put(not, magazinemap.get(not)-1);
    	}
    	if(!val)
    	System.out.println("Yes");

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String[] mn = scanner.nextLine().split(" ");

        int m = Integer.parseInt(mn[0]);

        int n = Integer.parseInt(mn[1]);

        String[] magazine = new String[m];

        String[] magazineItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < m; i++) {
            String magazineItem = magazineItems[i];
            magazine[i] = magazineItem;
        }

        String[] note = new String[n];

        String[] noteItems = scanner.nextLine().split(" ");
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int i = 0; i < n; i++) {
            String noteItem = noteItems[i];
            note[i] = noteItem;
        }

        checkMagazine(magazine, note);

        scanner.close();
    }
}
