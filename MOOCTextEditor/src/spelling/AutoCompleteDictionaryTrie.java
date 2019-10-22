package spelling;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;


    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}


	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
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
	@Override
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

	/**
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions)
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
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
 				return retrunSet;
 			}

 		  }

 		Queue<TrieNode> q= new LinkedList<TrieNode>();
        q.add(start);
        int i =0;
        while(q.peek() != null && i< numCompletions )
        {

        	TrieNode firstNode = q.remove();

        	if (firstNode.endsWord()){
        		retrunSet.add(firstNode.getText());
        		i++;
        	}
        	Set<Character> characters = new HashSet<Character>();
        	characters = firstNode.getValidNextCharacters();
        	for (Character c : characters) {
				q.add(firstNode.getChild(c));
			}



        }

         return retrunSet;
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



}