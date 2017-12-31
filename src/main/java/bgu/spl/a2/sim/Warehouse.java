package bgu.spl.a2.sim;

import bgu.spl.a2.Promise;

import java.util.HashMap;

/**
 * Represents a warehouse that holds a finite amount of computers
 *  and their suspended mutexes.
 * 
 */
public class Warehouse {

    private HashMap<String, Computer> computers;
    private HashMap<String, SuspendingMutex> mutexes;

    /**
    * Create {@Warehouse} according to computer list.
     *
     * @param computers
     *          Computer list.
     *
     *
     */
    public Warehouse(HashMap<String, Computer> computers){
        this.computers = computers;
        mutexes         = new HashMap<>();

        computers.forEach( (computerType, comp)-> {
            SuspendingMutex mutex = new SuspendingMutex();
            mutex.setComputer(comp);
            comp.setMutex(mutex);
            mutexes.put(computerType, mutex);
        } );
    }


    public Promise<Computer> getComputer(String type){
        return mutexes.get(type).down(type);
    }
	
}
