package bgu.spl.a2.sim;

import bgu.spl.a2.Promise;

import java.util.HashMap;

/**
 * represents a warehouse that holds a finite amount of computers
 *  and their suspended mutexes.
 * 
 */
public class Warehouse {

    private HashMap<String, Computer> computers;
    private HashMap<String, SuspendingMutex> mutexes;

    public Warehouse(HashMap<String, Computer> computers){
        this.computers = computers;

        computers.forEach( (computerType, comp)-> {
            SuspendingMutex mutex = new SuspendingMutex();
            mutex.setComputer(comp);
            mutexes.put(computerType, mutex);
        } );
    }

    public Promise<Computer> getComputer(String type){
        return mutexes.get(type).down(type);
    }
	
}
