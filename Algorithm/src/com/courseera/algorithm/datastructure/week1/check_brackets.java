package com.courseera.algorithm.datastructure.week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
    Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();
        Boolean matched =true;
       
        Stack<Bracket> opening_brackets_stack = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
            	
            	Bracket item = new Bracket(next, position+1);
				opening_brackets_stack.push(item ); 
				continue;
            }

            if (next == ')' || next == ']' || next == '}') {
                if(opening_brackets_stack.isEmpty())
                {  
                	System.out.println(position+1);
                	matched =false;                	
                	break;
                }else{
                	Bracket top = opening_brackets_stack.pop();
            	   	if(!top.match(next))
            	       {
            	   		System.out.println(position+1);  
            	   		matched =false; 
            		    break;
            	       }
            	   	
                }
            }
        }

        if(matched && !opening_brackets_stack.isEmpty())
        {
        		System.out.println(opening_brackets_stack.pop().position);
        }
        else
        {
        	 if(matched && opening_brackets_stack.isEmpty())
        	 {
        		 System.out.println("Success");
        	 }
        	
        }
  
    }
}
