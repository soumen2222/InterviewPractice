package com.honeywell.Trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */

class TrieNode {
	private HashMap<Character, TrieNode> children; 
	private String text;  // Maybe omit for space
	private boolean isWord;
	private int childs;
	
	/** Create a new TrieNode */
	public TrieNode()
	{
		children = new HashMap<Character, TrieNode>();
		text = "";
		isWord = false;
		childs =0;
	}
	
	/** Create a new TrieNode given a text String to store in it */
	public TrieNode(String text)
	{
		this();
		this.text = text;
	}
	
	/** Return the TrieNode that is the child when you follow the 
	 * link from the given Character 
	 * @param c The next character in the key
	 * @return The TrieNode that character links to, or null if that link
	 *   is not in the trie.
	 */
	public TrieNode getChild(Character c)
	{
		return children.get(c);
	}
	
	/** Inserts this character at this node.
	 * Returns the newly created node, if c wasn't already
	 * in the trie.  If it was, it does not modify the trie
	 * and returns null.
	 * @param c The character that will link to the new node
	 * @return The newly created TrieNode, or null if the node is already 
	 *     in the trie.
	 */
	public TrieNode insert(Character c)
	{
		if (children.containsKey(c)) {
			
			return null;
		}
		
		TrieNode next = new TrieNode(text + c.toString());
		children.put(c, next);
		
		return next;
	}
	
	/** Return the text string at this node */
    public String getText()
	{
		return text;
	}
	
    /** Set whether or not this node ends a word in the trie. */
	public void setEndsWord(boolean b)
	{
		isWord = b;
	}
	
	/** Return whether or not this node ends a word in the trie. */
	public boolean endsWord()
	{
		return isWord;
	}
	
	/** Return the set of characters that have links from this node */
	public Set<Character> getValidNextCharacters()
	{
		return children.keySet();
	}

	public int getChilds() {
		return childs;
	}

	public void setChilds(int childs) {
		this.childs = childs;
	}
	
	

}

public class CompleteDictionaryTrie  {

    private  TrieNode root;
    private  int size;
    private static CompleteDictionaryTrie c = new CompleteDictionaryTrie();


    public CompleteDictionaryTrie()
	{
		root = new TrieNode();
	}


	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public  boolean addWord(String word)
	{
	    //TODO: Implement this method.
		
		if (! isWord(word))
		{
		String lowCaseWord = word.toLowerCase();


		char[] charArray = lowCaseWord.toCharArray();

		TrieNode start = root;
		for ( Character c : charArray) {

			TrieNode next = start.insert(c);

			if (next==null){
				//get the child node and then modify the next pointer
				    next= start.getChild(c);
			}
			start = next;
			start.setChilds(start.getChilds() + 1);
		  }
		if( start.endsWord()){
			return false;
		}
		else
		{
			start.setEndsWord(true);
			size++;
			return true;
		}

	}
		else
			return false;
	}

	/**
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}


	/** Returns whether the string is a word in the trie */
	
	public boolean isWord(String s)
	{
	    // TODO: Implement this method


		String lowCaseWord = s.toLowerCase();


		char[] charArray = lowCaseWord.toCharArray();

		TrieNode start = root;
		for ( Character c : charArray) {

			TrieNode next = start.getChild(c);
			if(next!=null){
				start = next;
			}
			else{
				return false;
			}

		  }
		if( start.endsWord()){
			return true;
		}
		else
		{
			return false;
		}
	}

	
	 public int predictCount(String prefix)
     {
    
    	 List<String> retrunSet = new ArrayList<String>();
    	 String lowCaseStem = prefix.toLowerCase();
    	 char[] charArray = lowCaseStem.toCharArray();


 		TrieNode start = root;
 		for ( Character c : charArray) {

 			TrieNode next = start.getChild(c);
 			if(next!=null){
 				start = next;
 			}
 			else{
 				return retrunSet.size();
 			}

 		  }

 		return (start.getChilds());
 	
 		
     }
	
     public int predictCompletions(String prefix)
     {
    	 
    	 // Return the list of completions
    	 List<String> retrunSet = new ArrayList<String>();
    	 String lowCaseStem = prefix.toLowerCase();
    	 char[] charArray = lowCaseStem.toCharArray();


 		TrieNode start = root;
 		for ( Character c : charArray) {

 			TrieNode next = start.getChild(c);
 			if(next!=null){
 				start = next;
 			}
 			else{
 				return retrunSet.size();
 			}

 		  }

 		System.out.println(start.getChilds());
 		Queue<TrieNode> q= new LinkedList<TrieNode>();
        q.add(start);
       
        while(q.peek() != null )
        {

        	TrieNode firstNode = q.remove();

        	if (firstNode.endsWord()){
        		retrunSet.add(firstNode.getText());
        		
        	}
        	Set<Character> characters = new HashSet<Character>();
        	characters = firstNode.getValidNextCharacters();
        	for (Character c : characters) {
				q.add(firstNode.getChild(c));
			}

        }

         return retrunSet.size();
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}

 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null)
 			return;

 		System.out.println(curr.getText());

 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}


 	
 	
 	 public static void main(String[] args) {
         
 		Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        
        for(int a0 = 0; a0 < n; a0++){
            String op = in.next();
            String contact = in.next();
            
            performOp(op,contact);         
            
        }
              
         
         
 	 }
 	 
 	private static void performOp(String op, String contact) {
		
		 

       switch (op) {
       case "add":
            c.addWord(contact);
            
           break;
       case "find": 
    	   System.out.println(c.predictCount(contact));
    	   
           break;
       default:
      	 break;
		
	}

}
}


	