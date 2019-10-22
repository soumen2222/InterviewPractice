package com.honeywell.heaps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BST<T extends Comparable<T>> {
    /** A reference to the Node's left subtree. **/
    BST<T> left;
    /** A reference to the Node's right subtree. **/
    BST<T> right;
    /** The Node's contents. **/
    T data;

    /** Construct a Node object.
        @param data The Node's contents.
    **/
    public BST(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    /** Insert new values into the tree.
        @param value The data value for the node to insert.
    **/
    public void insert(T value) {
        // value <= data
        if(value.compareTo(data) <= 0) {
            if(left == null) {
                // Insert at empty subtree
                left = new BST<T>(value);
            }
            else {
                // Continue searching for empty subtree
                left.insert(value);
            }
        }
        else { // value > data
            if(right == null) {
                // Insert at empty subtree
                right = new BST<T>(value);
            }
            else {
                // Continue searching for empty subtree
                right.insert(value);
            }
        }
    }

    /** Determines if the tree contains a specific value.
        @param value The value to search the tree for.
        @return True if tree contains value; otherwise, false. 
    **/
    public boolean contains(T value) {
        if(value.compareTo(data) == 0) {
            // Value found
            return true;
        }
        else if(value.compareTo(data) < 0) {
            return (left == null) 
                ? false // Not found; there are no more nodes in left subtree to check
                : left.contains(value); // Continue searching left subtree
        }
        else { // data.compareTo(value) > 0
            return (right == null) 
                ? false // Not found; there are no more nodes in right subrtree to check
                : right.contains(value); // Continue searching right subtree
        }
    }

    
    
    /** Print InOrder Iterative of tree **/
    public void printInOrderIteravtive(BST<T> root) {
    	
    	if(root == null)
    	    return ;
    	 
    	 Stack<BST<T>> s = new Stack<BST<T>>();
    	 BST<T> currentNode=root;
    	   	        
    	  while(!s.empty() || currentNode!=null){
    	 
    	   if(currentNode!=null)
    	   {
    	    s.push(currentNode);
    	    currentNode=currentNode.left;
    	   }
    	   else
    	   {
    		 BST<T> n=s.pop();
    		 System.out.print(" " + n.data);    	   
    	     currentNode=n.right;
    	    
    	   }
    	 }
        
    }

    // Iterative solution of Pre-order
    public void PreorderIter( BST<T> root) {
        if( root == null ) return;
     
        Stack<BST<T>> s = new Stack<BST<T>>();
        
        s.push(root);
   	    
        while(!s.isEmpty())
        {
        	BST<T> currentNode = s.pop();
        	System.out.print(currentNode.data+" ");
        	
        	if(currentNode.right!=null)
        	{
        		s.push(currentNode.right);
        	}
        	
        	if(currentNode.left!=null)
        	{
        		s.push(currentNode.left);
        	}
        }
     
    }
    
    
    
    // Iterative solution of post order
    public void postorderIter( BST<T> root) {
        if( root == null ) return;
     
        Stack<BST<T>> s = new Stack<BST<T>>();
   	    BST<T> currentNode=root;
     
        while( true ) {
     
            if( currentNode != null ) {
                if( currentNode.right != null ) 
                 s.push( currentNode.right );
                s.push( currentNode );
                currentNode = currentNode.left;
                continue;
          }
     
            if( s.isEmpty( ) ) 
             return;
            currentNode = s.pop( );
     
            if( currentNode.right != null && ! s.isEmpty( ) && currentNode.right == s.peek( ) ) {
                s.pop( );
                s.push( currentNode );
                currentNode = currentNode.right;
            } else {
                System.out.print( currentNode.data + " " );
                currentNode = null;
            }
        }
    }
    
    /** Print LevelOrder Iterative of tree **/
    public void levelOrderIterative(BST<T> root) {
    	
    	Queue<BST<T>> q = new LinkedList<BST<T>>();
    	if(root != null)
    	{    	   	     
    	 q.add(root);
    	  while(!q.isEmpty() ){
    	 
    		  BST<T> n = q.poll();
    		  System.out.print(" " + n.data);
    		  
    		  if(n.left!=null)
    		  {
    			  q.add(n.left);
    		  }
    		  
    		  if(n.right!=null)
    		  {
    			  q.add(n.right);
    		  }
    	   
    	 }
    	} 
    }

    
    /** Print Size of tree **/
    public int Size(BST<T> root) {
    	int count =0;
    	Queue<BST<T>> q = new LinkedList<BST<T>>();
    	if(root != null)
    	{    	   	     
    	 q.add(root);
    	  while(!q.isEmpty() ){
    	 
    		  BST<T> n = q.poll();
    		  count++;
    		  
    		  if(n.left!=null)
    		  {
    			  q.add(n.left);
    		  }
    		  
    		  if(n.right!=null)
    		  {
    			  q.add(n.right);
    		  }
    	   
    	 }
    	  return count;
    	} 
    	else
    		return 0;
    }
    
    
    /** Print Reverse LevelOrder Iterative of tree **/
    public void ReverselevelOrderIterative(BST<T> root) {
    	
    	Queue<BST<T>> q = new LinkedList<BST<T>>();
    	Stack<BST<T>> s = new Stack<BST<T>>();
    	if(root != null)
    	{    	   	     
    	 q.add(root);
    	  while(!q.isEmpty() ){
    	 
    		  BST<T> n = q.poll();
    		  s.push(n);
    		  
    		  
    		  if(n.left!=null)
    		  {
    			  q.add(n.left);
    		  }
    		  
    		  if(n.right!=null)
    		  {
    			  q.add(n.right);
    		  }
    	   
    		  
    	 }
    	} 
    	
    	while(!s.isEmpty() )
    	{
    		System.out.print(" " + s.pop().data);    		
    	}
    	
    }
    
    /** Print Depth of tree - Iterative**/
    public int depthIterative(BST<T> root) {
    	
    	Queue<BST<T>> q = new LinkedList<BST<T>>();
    	int count =1;
    	if(root == null) return 0;
    	   	     
    	 q.offer(root);
    	 q.offer(null);
    	  while(!q.isEmpty() ){
    	 
    		  BST<T> n = q.poll();
    		  if(n!=null){
    		
    		  if(n.left!=null)
    		  {
    			  q.offer(n.left);
    		  }
    		  
    		  if(n.right!=null)
    		  {
    			  q.offer(n.right);
    		  }
    	   
    		 
    	 } else
    	 {
    		 if(!q.isEmpty())
    		 {
    			 count++;
    			 q.offer(null);
    		 }
    	 }
    		 
    	 }
    	  return count;
   } 
    	
    
   

    
    /** Print InOrder traversal of tree **/
    public void printInOrder(BST<T> root) {
    	
    	if(root!=null)
    	{
    		printInOrder(root.left);
    		System.out.print(root.data + " ");
    		printInOrder(root.right);    		 
    		
    	}
    }
    
    
    /** Print PostOrder traversal of tree **/
    public void printPostOrder(BST<T> root) {
    	
    	if(root!=null)
    	{
    		printPostOrder(root.left);
    		printPostOrder(root.right);
    		System.out.print(root.data + " ");     		
    	}
                       
    }
    /** Print PreOrder traversal of tree **/
    public void printPreOrder(BST<T> root) {
    	
    	if(root!=null)
    	{
    		System.out.print(root.data + " "); 
    		printPreOrder(root.left);
    		printPreOrder(root.right);
    		
    		
    	}
              
    }
    
    public int maxDepthRecursive(BST<T> root)
    {
    	if (root==null)
    		return 0;
    	
    	int leftDepth = maxDepthRecursive(root.left);
    	int rightDepth = maxDepthRecursive(root.right);
    	
    	return (leftDepth>rightDepth) ? leftDepth+1 : rightDepth+1;
    	
    }
    
   
    
    
    
    
    public static void main(String[] args) throws IOException {
    	
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		String temp[] = line.split(" ");
		int N = Integer.parseInt(temp[0]);

		String str = br.readLine();
		String temp1[] = str.split(" ");
		int[] input = new int[(N + 1)];

		for (int i = 0; i < N; i++) {
			input[i] = Integer.parseInt(temp1[i]);
		}
    	
    	BST<Integer> root = new BST<Integer>(input[0]);
    	for (int i =1; i<N;i++)
    	{    		   		
            root.insert(input[i]); 
    	}
    	
         
    	System.out.print("In order Recursion:   ");
        root.printInOrder(root);
        System.out.println();
        System.out.print("In order Iterative:   ");
        root.printInOrderIteravtive(root);
        System.out.println();
        System.out.print("Pre order Recursion:   ");
        root.printPreOrder(root);
        System.out.println();        
        System.out.print("Pre order Iterative:   ");
        root.PreorderIter(root);
        System.out.println();
        System.out.print("Post order Recursion:   ");
        root.printPostOrder(root);
        System.out.println();        
        
        System.out.print("Post order Iterative:   ");
        root.postorderIter(root);
        System.out.println();
        
        System.out.print("Level order Iterative:   ");
        root.levelOrderIterative(root);
        System.out.println();
        
        int count = root.Size(root);
        System.out.println("Size:   " + count);
        
        System.out.print("Reverse Level order Iterative:   ");
        root.ReverselevelOrderIterative(root);
        System.out.println();
        
        int depth = root.maxDepthRecursive(root);
        System.out.println("Recursive Max Depth " + depth);
        
        int depth1 = root.depthIterative(root);
        System.out.println("Iterative Max Depth " + depth1);
       
        
    }
}