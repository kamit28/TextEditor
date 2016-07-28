package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 * 
 * @author Amit
 *
 * @param <E>
 *            The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param element
	 *            The element to add
	 */
	public boolean add(E element) {
		checkElement(element);
		LLNode<E> node = new LLNode<E>(element);
		node.next = tail;
		if (size == 0) {
			node.prev = head;
			head.next = node;
			tail.prev = node;
		} else {
			node.prev = tail.prev;
			tail.prev.next = node;
			tail.prev = node;
		}
		size++;
		return true;
	}

	/**
	 * Get the element at position index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E get(int index) {
		checkSearchRange(index);
		LLNode<E> ptr = head;
		for (int i = 0; i <= index; i++) {
			ptr = ptr.next;
		}
		return ptr.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * 
	 * @param The
	 *            index where the element should be added
	 * @param element
	 *            The element to add
	 */
	public void add(int index, E element) {
		checkAddRange(index);
		checkElement(element);
		LLNode<E> node = new LLNode<E>(element);

		if (index == size) {
			node.next = tail;
			node.prev = tail.prev;
			tail.prev.next = node;
			tail.prev = node;
		} else {
			LLNode<E> ptr = head;
			for (int i = 0; i < index; i++) {
				ptr = ptr.next;
			}
			node.prev = ptr;
			node.next = ptr.next;
			ptr.next.prev = node;
			ptr.next = node;
		}
		size++;
	}

	/** Return the size of the list */
	public int size() {
		return size;
	}

	/**
	 * Remove a node at the specified index and return its data element.
	 * 
	 * @param index
	 *            The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException
	 *             If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		checkSearchRange(index);
		LLNode<E> ptr = head;
		for (int i = 0; i < index; i++) {
			ptr = ptr.next;
		}
		E val = ptr.next.data;
		ptr.next = ptr.next.next;
		ptr.next.prev = ptr;
		size--;
		return val;
	}

	/**
	 * Set an index position in the list to a new element
	 * 
	 * @param index
	 *            The index of the element to change
	 * @param element
	 *            The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E set(int index, E element) {
		checkSearchRange(index);
		checkElement(element);
		LLNode<E> ptr = head;
		for (int i = 0; i <= index; i++) {
			ptr = ptr.next;
		}
		E val = ptr.data;
		ptr.data = element;
		return val;
	}

	private void checkAddRange(int index) {
		if (!isInAddRange(index))
			throw new IndexOutOfBoundsException("Invalid add index: " + index
					+ ". List Size is: " + size);
	}

	private void checkSearchRange(int index) {
		if (!isInSearchRange(index))
			throw new IndexOutOfBoundsException("Invalid index: " + index
					+ ". List Size is: " + size);
	}

	private boolean isInSearchRange(int index) {
		return index >= 0 && index < size;
	}

	private boolean isInAddRange(int index) {
		return index >= 0 && index <= size;
	}

	private void checkElement(E element) {
		if (null == element) {
			throw new NullPointerException("Element was null");
		}
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("LLNode [prev=%s, next=%s, data=%s]", prev, next,
				data.toString());
	}
}
