package com.soumen.hackerearth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
 
public class RemoveFirnd 
{
	public static void main(String args[]) throws Exception
	{		
		try
		{
			int numberofFriends;
			int numberToRemove;
        
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        String name = br.readLine();                // Reading input from STDIN
	        int numOfTestCases = Integer.parseInt(name);
	        /*  Perform list operations  */
	        for(int i=1; i<=numOfTestCases; i++)
	        {
	        	String nextLine = br.readLine(); 
	        	String[] numberList = nextLine.split("\\s+");
	        	numberofFriends = Integer.parseInt(numberList[0]);
	        	numberToRemove = Integer.parseInt(numberList[1]);
	        	
	        	String popularity = br.readLine(); 
	        	String[] pList = popularity.split("\\s+");
	        	List<Integer> myPList = new ArrayList<Integer>();
	        	for(String p : pList){
	        	     myPList.add(Integer.parseInt(p));
	        	}
	        	processRequest(numberofFriends,numberToRemove,myPList);
	        }	              	        
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught = "+e.getMessage());
		}
	}
 
	private static void processRequest(int numberofFriends, int numberToRemove, List<Integer> myPList) 
	{		
		try
		{
			// Accept popularity			
			SLinkedList friends = new SLinkedList();
			for(int i : myPList)
			{
				friends.insertAtEnd(i);
			}
			SLinkedList newList = deleteFriends(friends, numberToRemove);			
			displayFriends(newList);
		}
		catch(Exception e)
		{
			System.out.println("Exception Caught = "+e.getMessage());
		}
	}
	
	public static void displayFriends(SLinkedList friends) 
	{
		SLLNode curr = friends.getStartNode();
		for(int i=1; i<= friends.getSize(); i++)
		{
			if(curr!= null)
			{
				System.out.print(curr.getData()+ " ");
				curr = curr.getNextNode();
			}			
		}		
		System.out.println("");
	}
 
	public static SLinkedList deleteFriends(SLinkedList friends, int numberToremove)
	{
		for(int k =1; k<= numberToremove;k++)
		{
		    boolean DeleteFriend=false;
		    SLLNode curr = friends.getStartNode();
    	    for(int i = 1; i< friends.getSize();i++)
    	    {
    	    	if(curr.getData() < curr.getNextNode().getData())
    	    	{
    	    		try 
    	    		{
						friends.deleteAtPos(i);
					} 
    	    		catch (Exception e) 
    	    		{
						return friends;
					}
    	    		DeleteFriend=true;
    	    		break;
    	    	}
    	    	curr=curr.getNextNode();
    	    }
    	    if(DeleteFriend == false)
    	    {
    	    	friends.deleteAtEnd();
    	    }
		}
		return friends;
	}
 
}
 
class SLLNode {
	protected int value;
	protected SLLNode next;
 
	public SLLNode()
	{
		value =0;
		next = null;
	}
	
	public SLLNode (int i,SLLNode n)
	{
		value =i;
		next = n;
	}
	
	public int getData()
	{
		return value;
	}
	
	public SLLNode getNextNode()
	{
		return next;
	}
	
	public void setData(int i)
	{
		value=i;
	}
 
	public void setNextNode(SLLNode n) 
	{
		next = n;		
	}
	
}
 
 
class SLinkedList
{
	protected SLLNode start;
	protected SLLNode end;
 
	public int size;
	
	 public boolean isEmpty()
	 {
	    return start == null;
	 }
	 
	 public int getSize()
	 {
	    return size;
	 }  
	 public SLLNode getStartNode()
	 {
		 return start;
	 }
	 
	 public SLLNode getEndNode()
	 {
		 return end;
	 }
	
	 public void insertAtStart(int value)
	 {
		 SLLNode nodeObj = new SLLNode(value,null);
		 size ++;
		 
		 if(start==null)
		 {
			 start = nodeObj;
			 end = start;
		 }
		 else
		 {
			 nodeObj.setNextNode(start);
			 start = nodeObj;			 
		 }
	 }
	 
	 public void insertAtEnd(int value)
	 {
		 SLLNode nodeObj = new SLLNode(value,null);
		 size++;
		 if(start == null)
		 {
			 start = nodeObj;
			 end = start;
		 }
		 else
		 {
			 end.setNextNode(nodeObj);
			 end = nodeObj;
		 }
	 }
	 
	 public void insertAtPos(int value, int pos) throws Exception
	 {
		 SLLNode nodeObj = new SLLNode(value,null);
		 
		 
		 if(pos>size+1 || pos <1)
		 {
			 throw new Exception("Invalid Position");
		 }
		 if(pos==1)
		 {
			 insertAtStart(value);
			 return;
		 }
		 else if (pos == size+1)
		 {
			 insertAtEnd( value);
			 return;
		 }
		 SLLNode curr = start;
		 for (int i=2; i<=size; i++)
		 {
			 if(i==pos)
			 {
				 SLLNode tmp = curr.getNextNode();
				 curr.setNextNode(nodeObj);				 
				 nodeObj.setNextNode(tmp);	
				 break;
			 }
			curr = curr.getNextNode();
			 
		 }
		 size ++;
		 return;
	 }	 
	 
	 public void deleteAtStart()
	 {
		 if(size==1)
		 {
			 start = null;
			 end= null;
			 size = 0;
			 return;
		 }
		 start = start.getNextNode();
		 size -- ;
		 return;
	 }
	 
	public void deleteAtEnd() 
	{
		 if(size==1)
		 {
			 start = null;
			 end= null;
			 size = 0;
			 return;
		 }
		SLLNode tmp = start;
		for(int i=1;i< size -1;i++)
		{
			tmp = tmp.getNextNode();
		}
		tmp.setNextNode(null);
		end=tmp;		
		return;
	}
	  
	 public void deleteAtPos(int pos) throws Exception
	 {
		 if(pos <1 || pos > size)
		 {
			 throw new Exception("Invalid Position");
		 }
		 if(pos ==1)
		 {
			 deleteAtStart();
			 return;
		 }
		 if(pos == size)
		 {
			 deleteAtEnd();
			 return;
		 }
		 
		 SLLNode curr = start;		 
		 for(int i=1; i <size; i++)
		 {
			 if(i == (pos-1))
			 {
				 SLLNode tmp = curr.getNextNode();
				 curr.setNextNode(tmp.getNextNode());
			 }
			 curr = curr.getNextNode();
		 }
		 size--;
		 
	 }
	 
	 public void display()
	    {
	        System.out.print("\nSingly Linked List = ");
	        if (size == 0) 
	        {
	            System.out.print("empty\n");
	            return;
	        }    
	        if (start.getNextNode() == null) 
	        {
	            System.out.println(start.getData() );
	            return;
	        }
	        SLLNode ptr = start;
	        System.out.print(start.getData()+ "->");
	        ptr = start.getNextNode();
	        while (ptr.getNextNode() != null)
	        {
	            System.out.print(ptr.getData()+ "->");
	            ptr = ptr.getNextNode();
	        }
	        System.out.print(ptr.getData()+ "\n");
	    }
 
	
}
