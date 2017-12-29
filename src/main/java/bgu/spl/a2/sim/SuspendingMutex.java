package bgu.spl.a2.sim;
import bgu.spl.a2.Promise;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * 
 * Note: this class can be implemented without any synchronization. 
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 *
 */
public class SuspendingMutex {

	private Lock lock = new ReentrantLock();
	private Computer computer;
	private List<Promise<Computer>> promises = new ArrayList<>();

	
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediatly
	 * 
	 * @param computerType
	 * 					computer's type
	 * 
	 * @return a promise for the requested computer
	 */
	public Promise<Computer> down(String computerType){
		Promise<Computer> promise = new Promise<>();
		if (lock.tryLock()){
			promise.resolve(computer);
		}
		else{
			promises.add(promise);
		}
		return promise;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 *
	 * @param computer
	 */
	public void up(Computer computer){
		if(promises.size() > 0){
			promises.remove(0).resolve(this.computer);
		}
		else{
			lock.unlock();
		}
	}
}
