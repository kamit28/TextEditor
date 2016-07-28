/**
 * 
 */
package textgen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Amit
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH = 10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

	}

	/**
	 * Test if the get method is working correctly.
	 */
	@Test
	public void testGet() {
		// testing empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		// testing short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		try {
			shortList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		// testing longer list contents
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			assertEquals("Check " + i + " element", (Integer) i,
					longerList.get(i));
		}

		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

	}

	/**
	 * Test removing an element from the list.
	 */
	@Test
	public void testRemove() {
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer) 21,
				list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());

		try {
			longerList.remove(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			longerList.remove(longerList.size());
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		try {
			int x = longerList.remove(longerList.size() - 1);
			assertEquals("Remove: check x is correct ", 9, x);
			assertEquals("Remove: check last element is correct ",
					Integer.valueOf(8), longerList.get(longerList.size() - 1));
		} catch (IndexOutOfBoundsException e) {
		}

	}

	/**
	 * Test adding an element into the end of the list, specifically public
	 * boolean add(E element)
	 * */
	@Test
	public void testAddEnd() {

		try {
			shortList.set(0, null);
			fail("Check element was null");
		} catch (NullPointerException e) {

		}

		try {
			longerList.set(0, null);
			fail("Check element was null");
		} catch (NullPointerException e) {

		}
		assertEquals("Check first", "A", shortList.get(0));

		assertEquals("Check second", "B", shortList.get(1));

		assertEquals("Check first", Integer.valueOf(0), longerList.get(0));
		assertEquals("Check last", Integer.valueOf(9), longerList.get(9));

		longerList.add(300);
		assertEquals("Check last", Integer.valueOf(300),
				longerList.get(longerList.size() - 1));

		assertEquals("Check first", Integer.valueOf(65), list1.get(0));

		assertEquals("Check last", Integer.valueOf(42), list1.get(2));

		list1.add(201);
		assertEquals("Check last", Integer.valueOf(201), list1.get(3));
	}

	/** Test the size of the list */
	@Test
	public void testSize() {
		assertEquals("Check shortList", 2, shortList.size());
		assertEquals("Check emptyList", 0, emptyList.size());
		assertEquals("Check longerList", 10, longerList.size());
		assertEquals("Check list1", 3, list1.size());

		try {
			longerList.remove(0);
			assertEquals("Check longerList", 9, longerList.size());
			longerList.add(80);
			assertEquals("Check longerList", 10, longerList.size());
			longerList.add(0, 90);
			assertEquals("Check longerList", 11, longerList.size());
			longerList.add(longerList.size(), 20);
			assertEquals("Check longerList", 12, longerList.size());
		} catch (IndexOutOfBoundsException e) {
		}

	}

	/**
	 * Test adding an element into the list at a specified index, specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex() {
		try {
			emptyList.set(-1, 10);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			longerList.set(-1, 10);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			longerList.set(12, 10);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			shortList.set(0, null);
			fail("Check element was null");
		} catch (NullPointerException e) {

		}

		try {
			longerList.set(10, 10);
			assertEquals("Check longerList element at new index 10",
					Integer.valueOf(10), longerList.get(10));

			longerList.set(5, 500);
			assertEquals("Check longerList value at index 5",
					Integer.valueOf(500), longerList.get(5));
			assertEquals("Check longerList value at index 6 - it must be 5",
					Integer.valueOf(5), longerList.get(5));
			assertEquals("Check longerList value at index 4 - it must be 4",
					Integer.valueOf(4), longerList.get(4));

			longerList.set(0, 1000);
			assertEquals("Check longerList new element at index 0",
					Integer.valueOf(1000), longerList.get(0));
			assertEquals("Check longerList value at index 1",
					Integer.valueOf(0), longerList.get(1));

			emptyList.set(0, 6);
			assertEquals("Check emptyList", Integer.valueOf(6),
					longerList.get(0));

		} catch (IndexOutOfBoundsException e) {

		}

	}

	/** Test setting an element in the list */
	@Test
	public void testSet() {
		try {
			emptyList.set(1, 10);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			emptyList.set(-1, 10);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			shortList.set(0, null);
			fail("Check element was null");
		} catch (NullPointerException e) {

		}

		try {
			shortList.set(1, "C");
			assertEquals("Check shortList", "C", shortList.get(1));

			shortList.set(0, "M");
			assertEquals("Check shortList", "M", shortList.get(0));

			longerList.set(0, 190);
			assertEquals("Check longerList", Integer.valueOf(190),
					longerList.get(0));

			longerList.set(9, 0);
			assertEquals("Check longerList", Integer.valueOf(0),
					longerList.get(9));

			longerList.set(5, 10);
			assertEquals("Check longerList", Integer.valueOf(10),
					longerList.get(5));

		} catch (IndexOutOfBoundsException e) {

		}

	}
}
