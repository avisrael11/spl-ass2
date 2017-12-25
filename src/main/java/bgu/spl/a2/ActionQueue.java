package bgu.spl.a2;


import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ActionQueue extends ConcurrentLinkedQueue<Action> {

    private Lock lock;
    private String actorId;


    /**
     * to do - add doc
     *
     *
     * @param actorId
     *            ***
     */
    public ActionQueue(String actorId) {
        super();
        this.lock = new ReentrantLock();
        this.actorId=actorId;
    }


    /**
     * to do - add doc
     *
     *
     *
     *            ***
     */
    public Lock getLock(){
        return this.lock;
    }


    /**
     * to do - add doc
     *
     *
     *
     *            ***
     */
    public String getActorId(){
        return this.actorId;
    }

}
