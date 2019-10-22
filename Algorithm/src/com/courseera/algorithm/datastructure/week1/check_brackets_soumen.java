package com.courseera.algorithm.datastructure.week1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Output {
	Boolean match;
    int position;
	Output(Boolean match, int position) {
        this.match = match;
        this.position = position;
    }
}

class Bracket1 {
    Bracket1(char type, int position) {
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

class check_brackets_soumen {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();              
        Output matched = isBalanced(text);
        if(matched.match)
        {
        	 System.out.println("Success");
        }
        else
        {
        	 System.out.println(matched.position);
        }
          
    }

	private static Output isBalanced(String text) {

        Stack<Bracket1> opening_brackets_stack = new Stack<Bracket1>();
		for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);

            if (next == '(' || next == '[' || next == '{') {
            	
            	Bracket1 item = new Bracket1(next, position+1);
				opening_brackets_stack.push(item ); 
				continue;
            }

            if (next == ')' || next == ']' || next == '}') {
                if(opening_brackets_stack.isEmpty())
                {  
                 return  new Output(false, position+1) ;         	
                
                }else{
                	Bracket1 top = opening_brackets_stack.pop();
            	   	if(!top.match(next))
            	       {
            	   		return  new Output(false, position+1) ; 
            	       }
            	   	 }
            }
        }
		return  new Output(opening_brackets_stack.isEmpty(), !opening_brackets_stack.isEmpty() ? opening_brackets_stack.pop().position : 1 ) ; 
	}
}
