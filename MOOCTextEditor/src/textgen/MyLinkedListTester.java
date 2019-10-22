/**
 *
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

	}


	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}

		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}

		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}

	}


	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());


		 try {
			 list1.remove(-1);
 			fail("Check out of bounds");
 		}
 		catch (IndexOutOfBoundsException e) {

 		}

		 try {
			 list1.remove(3);
 			fail("Check out of bounds");
 		}
 		catch (IndexOutOfBoundsException e) {

 		}
		// TODO: Add more tests here
	}

	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
        // TODO: implement this test

		boolean result = shortList.add("D");
		assertEquals("AddEnd: check the result is ", true, result);
		assertEquals("AddEnd: check the element at the end is ", "D", shortList.get(2));
		assertEquals("AddEnd: check size is correct ", 3, list1.size());


		try {
			shortList.add(null);
			fail("null pointer exception");
		}
		catch (NullPointerException e) {

		}
	}


	/** Test the size of the list */
	@Test
	public void testSize()
	{
		// TODO: implement this test


		MyLinkedList<String> smallList = new MyLinkedList<String>();
		smallList.add("A");
		assertEquals("Size: The size after first addition is ", 1, smallList.size());
		smallList.add("B");
		assertEquals("Size: The size after first addition is ", 2, smallList.size());
	}



	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
        // TODO: implement this test

		MyLinkedList<String> list2 = new MyLinkedList<String>();
         list2.add("A");
         list2.add("B");
         list2.add("C");
         list2.add(1,"D");
         assertEquals("testAddAtIndex: check the element at the index 1 is ", "D", list2.get(1));
         assertEquals("testAddAtIndex: check the element at the index 2 is ", "B", list2.get(2));
         assertEquals("testAddAtIndex: The size after first addition is ", 4, list2.size());

         try {
        	 list2.add(-1,"E");
 			fail("Check out of bounds");
 		}
 		catch (IndexOutOfBoundsException e) {

 		}


         try {
        	 list2.add(6,"G");
 			fail("Check out of bounds");
 		}
 		catch (IndexOutOfBoundsException e) {

 		}
	}

	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
	    // TODO: implement this test

		Integer replaced = list1.set(1,75);
		assertEquals("testSet: check the element at the index 1 is ", (Integer)75, list1.get(1));
		assertEquals("testSet: check the replaced element  ", (Integer)21, replaced);

		try {
			list1.set(-1,75);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}


		try {
			list1.set(1,null);
			fail("null pointer exception");
		}
		catch (NullPointerException e) {

		}


		try {
			list1.set(4,55);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {

		}
	}


	// TODO: Optionally add more test methods.

}
