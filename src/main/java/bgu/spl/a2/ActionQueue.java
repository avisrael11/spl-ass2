package bgu.spl.a2;


import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * The class represents a queue of status lock of Action per Actor,
 * it uses @{@link ReentrantLock} to maintain a thread-safe environment.
 *
 */
public class ActionQueue extends ConcurrentLinkedQueue<Action> {

    private Lock lock;
    private String actorId;


    /**
     * creates a {@link ActionQueue} per actor.
     *
     * @param actorId
     *            The string represent the Id of the actor that owns this queue.
     */
    public ActionQueue(String actorId) {
        super();
        this.lock = new ReentrantLock();
        this.actorId=actorId;
    }


    /**
     * A getter supply the locking object in the qeue of actor owner.
     *
     * @return ths locking object.
     */
    public Lock getLock(){
        return this.lock;
    }


    /**
     * A getter supply the Id of actor owner.
     *
     *@return ths Id of actor
     *
     */
    public String getActorId(){
        return this.actorId;
    }

}
