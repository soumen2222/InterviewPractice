package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 *
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		size=0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev =head;

	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element )
	{
		// TODO: Implement this method

		  if (element!=null){
		LLNode<E> n1 = new LLNode<E>(element, tail);
		size++;
		return true;}
		 else{
		   	   throw new NullPointerException("null pointer exception");
		      }
	}

	/** Get the element at position index
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index)
	{
		// TODO: Implement this method.
       if (index<size() && index>=0){

		LLNode<E> start = head;
		for (int i =0; i<=index;i++){
			start = start.next;
			}
		return start.data;

	}
       else{
    	   throw new IndexOutOfBoundsException("Check out of bounds");
       }

	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element )
	{
		// TODO: Implement this method

		if (element!=null){
		if (index>=0 && index<=size() ){

			LLNode<E> start = head;
			for (int i =0; i<=size();i++){
				start = start.next;
				if (index==i){
					LLNode<E> n = new LLNode<E>(element, start);
					size++;
					break;
				}
			}
				}

		else{
	    	   throw new IndexOutOfBoundsException("Check out of bounds");
	       }
		}
		 else{
		   	   throw new NullPointerException("null pointer exception");
		      }

	}


	/** Return the size of the list */
	public int size()
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 *
	 */
	public E remove(int index)
	{
		// TODO: Implement this method
		 if (index<size() && index>=0){

				LLNode<E> start = head;
				for (int i =0; i<=index;i++){
					start = start.next;
					}
				E deletedData = start.data;
				start.prev.next=start.next;
				start.next.prev=start.prev;
				start.next=null;
				start.prev=null;
				size--;

                return deletedData;


			}
		       else{
		    	   throw new IndexOutOfBoundsException("Check out of bounds");
		       }

	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element)
	{
		// TODO: Implement this method
      if (element!=null){

		if (index<size() && index>=0){

			LLNode<E> start = head.next;
			E deletedData = null;
			for (int i =0; i<size();i++){

				if (index==i){
					deletedData = start.data;
					start.data = element;
					break;
				}

				start = start.next;

				}
			return deletedData;

		}
	       else{
	    	   throw new IndexOutOfBoundsException("Check out of bounds");
	       }
      }

      else{
   	   throw new NullPointerException("null pointer exception");
      }

	}
}

class LLNode<E>
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e)
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E theData,LLNode<E> nextNode   )
	{
		this(theData);
		this.next =nextNode;
		this.prev = this.next.prev;
		this.prev.next = this;
		this.next.prev = this;
	}



}
