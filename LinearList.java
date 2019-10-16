/**
 * Program #2 LinearList
 * This is a doubly linked list with functions to add and remove and alter this list 
 * CS310-01
 * 13-Mar-2019
 * @author Kyle McLain cssc 1497
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearList <E extends Comparable <E>> implements LinearListADT<E>{
	
	private long modificationCounter;
	private int currentSize;
	private Node<E> previous = null;
	
	/**
	 *	This it is a no argument constructor
	 * @param <E>
	 */	
	private class Node<E>{
		E data;
		Node<E> next;
		Node<E> previous;
		public Node(E obj) {
			data = obj;
			next = null;
			previous = null;
		}
	}
	
	private Node<E> head, tail;

	public LinearList() {
		tail = head = null;
		currentSize = 0;
		modificationCounter = 0;
	}

	/** Adds the Object obj to the beginning of list and returns true if the list
	 * is not full.
	 * returns false and aborts the insertion if the list is full.
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean addFirst(E obj) {
	
		Node<E> newNode = new Node<E>(obj);
		
		if(isEmpty()) {
			head = tail = newNode;
		}
		
		else{
			newNode.next = head;
			head.previous = newNode;
			head = newNode;	
		}
		
		modificationCounter++;
		currentSize++;
		return true;
	}

	 /** Adds the Object obj to the end of list and returns true if the list is
	 * not full.
	 * returns false and aborts the insertion if the list is full.
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean addLast(E obj) {

		Node<E> newNode = new Node<E>(obj);
		
		if(isEmpty()) {
			head = tail = newNode;	
		}
		
		else{
			tail.next = newNode;
			newNode.previous = tail;
			tail = newNode;
		}
		
		modificationCounter++;
		currentSize++;
		return true;
	}

	/** Removes and returns the parameter object obj in first position in list
	 * if the list is not empty, null if the list is empty.
	 * @return E
	 */
	@Override
	public E removeFirst() {

		if(head == null) {	//IF ITS EMPTY
			return null;
		}	
		
		E tmp = head.data;
		
		if(currentSize == 1) { //IF IT HAS ONE ITEM
			head = tail = null;
			
		}else { //IF IT HAS MORE THAN ONE ITEM
			head = head.next;
			head.previous = null;
		}
		
		modificationCounter++;
		currentSize--;		
		return tmp;
	}

	/** Removes and returns the parameter object obj in last position in list if
	 * the list is not empty, null if the list is empty.
	 * return E
	 */
	@Override
	public E removeLast() {
		
		if(head == null) {	//IS EMPTY
			return null;
		}
		
		E tmp = tail.data;
		
		if(currentSize == 1) {	//ONE ELEMENT
			head = tail = null;
			
		}else {		//MORE THAN ONE ELEMENT
			
			tail = tail.previous;
			tail.next = null;
			
		}
			
		modificationCounter++;				
		currentSize--;
		
		return tmp;
	}

	 /** Removes and returns the parameter object obj from the list if the list
	 * contains it, null otherwise. The ordering of the list is preserved. 
	 * The list may contain duplicate elements. This method removes and returns
	 * the first matching element found when traversing the list from first
	 * position. Note that you may have to shift elements to fill in the slot
	 * where the deleted element was located.
	 * @param obj
	 * @return E
	 */
	@Override
	public E remove(E obj) {
		
		if(isEmpty()) {
			return null;
		}
		
		if(((Comparable<E>)head.data).compareTo(obj) == 0) {	//CHECKING IF OBJ = HEAD
			return removeFirst();
		}
		
		if(((Comparable<E>)tail.data).compareTo(obj) == 0) {	//CHECKING IF OBJ = TAIL
			return removeLast();
		}
		
		Node <E> current = head;
		Node <E> previous = null;
		
		while(current != null) {
			
			if(((Comparable<E>)current.data).compareTo(obj) == 0) {
				
				currentSize--;
				modificationCounter++;
				previous.next = current.next;
				current.next.previous = current.previous;
				return current.data;
				
			}				
			
			previous = current;
			current = current.next;
			
		}	
		return null;
	}

	 /** Returns the first element in the list, null if the list is empty.
	 * The list is not modified.
	 * @return E
	 */
	@Override
	public E peekFirst() {
		if(isEmpty()) {
			return null;
		}
		return head.data;
	}

	/** Returns the last element in the list, null if the list is empty.
	 * The list is not modified.
	 * @return E
	*/
	@Override
	public E peekLast() {
		if(isEmpty()) {
			return null;
		}
		return tail.data;
	}

	/** Returns true if the parameter object obj is in the list, false otherwise.
	 * The list is not modified.
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean contains(E obj) {
		if(find(obj) == null) {
			return false;
		}
		return true;
	}

	 /** Returns the element matching obj if it is in the list, null otherwise.
	 * In the case of duplicates, this method returns the element closest to
	 * front. The list is not modified.
	 * @param obj
	 * @return E
	 */
	@Override
	public E find(E obj) {
		
		Node<E> current =  head;
		if(isEmpty()) {
			return null;
		}
		
		while(current != null) {
			if(((Comparable <E>) current.data).compareTo(obj) == 0){
				return current.data;			
			}
			current = current.next;
		}
		return null;
	}
	
	 /** The list is returned to an empty state.
	 */
	@Override
	public void clear() {
		currentSize = 0;
		modificationCounter++;
		head = tail = null;
	}

	 /** Returns true if the list is empty, otherwise false
	  * @return boolean
	 */
	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	 /** Returns true if the list is full, otherwise false
	  * @return boolean
	 */
	@Override
	public boolean isFull() {
		return false;	
	}

	 /** Returns the number of Objects currently in the list.
	  * @return int
	 */
	@Override
	public int size() {
		return currentSize;
	}

	 /** Returns an Iterator of the values in the list, presented in the same
	 * order as the underlying order of the list. (front first, rear last)
	 */
	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator <E>{ 
		
		Node<E> current;
		long stateCheck;
		E tmp;
		
		public IteratorHelper() {
			current = head;
			stateCheck = modificationCounter;
		}
		
		public boolean hasNext() {
			if(stateCheck != modificationCounter) {
				throw new ConcurrentModificationException();
			}
			return (current != null);
		}
		
		public E next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			
			tmp = current.data;
			current = current.next;	
			return tmp;
			
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
