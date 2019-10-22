import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {
	
	
	private Queue<Object> q = new LinkedList<>();
	private int size = 10;
	final Lock lock = new ReentrantLock(true);
	
	private final Condition notFull= lock.newCondition();
	private final Condition notEmpty= lock.newCondition();	 
	 
	 public void put(Object x) throws InterruptedException{
		 lock.lock();
		 
		 try {
			 while(q.size()==size) {
				 notFull.await();
			 }
			q.add(x);
			notEmpty.signal();
			 
		 }finally {
			 lock.unlock();
		 }
	 }
	 
	 public Object take() throws InterruptedException{
		 lock.lock();
		 
		 try {
			 while(size==0) {
				 notEmpty.await();
			 }
			 Object x = q.poll();
			 --size;
			 notFull.signal();			        
		   return x;			 
		 }finally {
			 lock.unlock();
		 }
	 }
	 
}
