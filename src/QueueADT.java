	
public interface QueueADT<T> {
		/**
		 * Adds one element to the rear of the queue
		 * 
		 * @param element
		 *            to be added onto the queue
		 */
		public void enqueue(T element);

		/**
		 * Removes the element from the front of the  queue
		 * 
		 * @return element removed from the front of the queue
		 */
		public T dequeue() ;

		/**
		 * Removes and returns the element from the front of the queue 
		 * 
		 * @return T element removed from the front of the queue
		 */
		
		public T peek ();
		/**
		 * Returns the front of the queue without removing it
		 * @return the front of the queue
		 */
		public int size ();
		/**
		 * Returns the number of items in the queue 
		 * @return int number of items
		 */
		
		public boolean isEmpty();

		/**
		 * Returns the if the contains any elements queue.
		 * 
		 * @return boolean true- if the queue contains no elements
		 */
	
}
