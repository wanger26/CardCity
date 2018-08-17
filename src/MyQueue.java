import java.util.LinkedList;
/**
 * 
 * @author Jakob Wanger
 *
 * @param <T> abstract data type 
 */
public class MyQueue <T> implements QueueADT<T>{
	
	private LinkedList<T> queue;
	
	public MyQueue() {
		queue= new LinkedList<T>(); 
	}
	
	public void enqueue(T item) {
		queue.add(item); 
	}
	
	public T dequeue() {
		return queue.removeFirst(); 
	}
	
	public T peek() {
		return queue.getFirst(); 
	}
	
	public int size() {
		return queue.size(); 
	}

	public boolean isEmpty() {
		return queue.isEmpty(); 
	}
}
