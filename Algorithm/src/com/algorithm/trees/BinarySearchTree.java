package com.algorithm.trees;

import java.util.NoSuchElementException;
import java.util.Scanner;
public class BinarySearchTree {

	private BinarySearchTreeNode root;
	

	public BinarySearchTreeNode getRoot() {
		return root;
	}


	private static class BinarySearchTreeNode {

		private int data;
		private BinarySearchTreeNode leftNode;
		private BinarySearchTreeNode rightNode;
		private int size;

		public BinarySearchTreeNode(int data, BinarySearchTreeNode leftNode, BinarySearchTreeNode rightNode,int size) {
			this.data = data;
			this.leftNode = leftNode;
			this.rightNode = rightNode;
			this.size=size;
		}

	}

	public BinarySearchTreeNode find(int data) {
		BinarySearchTreeNode currentNode = root;
		while (currentNode != null) {
			if (currentNode.data == data)
				return currentNode;
			else {
				if (currentNode.data < data) { // It will be in right Node
					currentNode = currentNode.rightNode;
				} else {// It will be in Left Node
					currentNode = currentNode.leftNode;
				}
			}
		}
		return null;
	}
	
	public void insert(int data) {
		root = insertrec(root, data);
		
	}

	private BinarySearchTreeNode insertrec(BinarySearchTreeNode root, int data) {

		if (root == null) {
			root = new BinarySearchTreeNode(data, null, null,1);
			return root;
		} 
		if (root.data < data) { // It will be in right Node
				root.rightNode = insertrec(root.rightNode, data);
		} else if (root.data > data){// It will be in Left Node
				root.leftNode = insertrec(root.leftNode, data);
			}		
		root.size = 1 + size(root.leftNode) + size(root.rightNode);
		return root;

	}
	
	public void deleteKey(int key) 
    { 
        root = deleteRec(root, key); 
    } 
	
	private BinarySearchTreeNode deleteRec(BinarySearchTreeNode currentNode, int data) {

		if (currentNode == null) {
			System.out.println("Element Not Found");
		}else if (currentNode.data < data) { // It will be in right Node
				currentNode.rightNode = deleteRec(currentNode.rightNode, data);
		}else if (currentNode.data > data){// It will be in Left Node
				currentNode.leftNode = deleteRec(currentNode.leftNode, data);
		}else{// Found the Element
			if(currentNode.rightNode!=null && currentNode.leftNode!=null) {
				currentNode.data = findMax(currentNode.leftNode);				
				currentNode.leftNode = deleteRec(currentNode.leftNode,currentNode.data);
			}else {
				
				if(currentNode.leftNode==null) {
					return currentNode.rightNode;
				}
				if(currentNode.rightNode==null) {
					return currentNode.leftNode;
				}
			
			}			
		}
		
		currentNode.size = 1 + size(currentNode.leftNode) + size(currentNode.rightNode);
		return currentNode;

	}
	
	
	private int findMax(BinarySearchTreeNode root) {
	
		 int maxV = root.data;
		   while(root.rightNode!=null) {
			   maxV= root.rightNode.data;
			   root= root.rightNode;			   
		   }
		   return maxV;
	}

		public int findMin(BinarySearchTreeNode root) {
			 int minV = root.data;
			   while(root.leftNode!=null) {
				   minV= root.leftNode.data;
				   root= root.leftNode;			   
			   }
			   return minV;
		}

	// return number of key-value pairs in BST rooted at x
    private int size(BinarySearchTreeNode x) {
        if (x == null) return 0;
        else return x.size;
    }
    
    public void inorderTraversel(BinarySearchTreeNode currentNode){
    	if(currentNode!=null) {
    	inorderTraversel(currentNode.leftNode);
    	System.out.print(currentNode.data+ " ->");
    	inorderTraversel(currentNode.rightNode);
    	}
    }
    
    
    public int rank(int data) {
    	return rank(root,data);
    }
    
    private int rank(BinarySearchTreeNode currentNode, int data) {
    	if(currentNode==null) {
    		return 0;
    	}
    	if (currentNode.data < data) { // It will be in right Node
			return 1 + size(currentNode.leftNode) + rank(currentNode.rightNode,data);
	    }else if (currentNode.data > data){// It will be in Left Node
			return rank(currentNode.leftNode,data);
	    }else{
	    	return size(currentNode.leftNode);
	    } 
	}
    
    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int floor(int data) {
    	BinarySearchTreeNode x = floor(root, data);
        if (x == null) return 0;
        else return x.data;
    } 

    private BinarySearchTreeNode floor(BinarySearchTreeNode x, int data) {
        if (x == null) return null;
       
        if (x.data== data) return x;
        if (x.data >  data) return floor(x.leftNode, data);
        BinarySearchTreeNode t = floor(x.rightNode, data); 
        if (t != null) return t;
        else return x; 
    } 
    
    
    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param  key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int ceiling(int data) {
    	BinarySearchTreeNode x = ceiling(root, data);
        if (x == null) return 0;
        else return x.data;
    }

    private BinarySearchTreeNode ceiling(BinarySearchTreeNode x, int data) {
        if (x == null) return null;
       
        if (x.data== data) return x;
        if (x.data >  data) { 
        	BinarySearchTreeNode t = ceiling(x.leftNode, data); 
            if (t != null) return t;
            else return x; 
        } 
        return ceiling(x.rightNode, data); 
    } 
    
    
    public int getLCA(int point1 , int point2) {
    	
    	BinarySearchTreeNode node= getLCARec(root , point1, point2);
    	if(node!=null) {
    		return node.data;
    	}else {
    		return -1;
    	}
    }

	private BinarySearchTreeNode getLCARec(BinarySearchTreeNode node, int point1, int point2) {
		
		if(node==null) {
			return null;
		}
		if(node.data==point1 || node.data==point2) {
			return node;
		}
		if(Math.max(point1, point2) < node.data ) {
			return getLCARec(node.leftNode,point1,point2);
		}else if(Math.min(point1, point2) >node.data) {
			return getLCARec(node.rightNode,point1,point2);
		}else {
			return node;
		}
		
		
	}
	
	public boolean isBST(BinarySearchTreeNode root) {
		
		if(root==null) {
			return true;
		}
		
		if(root.leftNode!=null && findMax(root.leftNode) > root.data) {
			return false;
		}
		if(root.rightNode!=null && findMin(root.rightNode) < root.data) {
			return false;
		}
		
		if(!isBST(root.leftNode) || !isBST(root.rightNode)) {
			return false;
		}
		return true;
	}

	public int[] readData() {
		Scanner scanner = new Scanner(System.in);
		int count = Integer.parseInt(scanner.next());
		scanner.nextLine();
		int[] values = new int[count];
		
		for (int i = 0; i < count; i++) {
			values[i] = (Integer.parseInt(scanner.next()));
		}
		return values;
	}
    

	public static void main(String[] args) {
		BinarySearchTree c1 = new BinarySearchTree();
		int[] values = c1.readData();
		for( int i =0; i <values.length; i++) {
			c1.insert(values[i]);
		}	
		c1.inorderTraversel(c1.getRoot());		
		c1.floor(39);
		System.out.println(c1.getLCA(40,100));
	}

}
