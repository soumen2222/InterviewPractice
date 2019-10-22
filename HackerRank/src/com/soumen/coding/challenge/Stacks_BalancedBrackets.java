package com.soumen.coding.challenge;

import java.util.Scanner;
import java.util.Stack;

public class Stacks_BalancedBrackets {
    
    public static boolean isBalanced(String expression) {
    	 
    	if ((expression.length() & 1) == 1) return false;
    	else 
    	{
    		char[] brackets = expression.toCharArray();
    		Stack<Character> stack = new Stack<Character>();
    		
    		for (char c : brackets) {
				
    			switch(c)
    			{
    			  case '{': stack.push('}'); break;
    		      case '(': stack.push(')'); break;
    		      case '[': stack.push(']'); break;
    		      default : 
    		    	  if(stack.empty() || c != stack.peek())
    		    	  	  return false;
    		    	  stack.pop();
    		    	  
    			}
			}
    		return stack.empty();
    	}
          
    }
    
   
  
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        for (int a0 = 0; a0 < t; a0++) {
            String expression = in.next();
            System.out.println( (isBalanced(expression)) ? "YES" : "NO" );
        }
    }
}
