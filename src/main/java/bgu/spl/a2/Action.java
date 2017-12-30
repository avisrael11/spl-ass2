package bgu.spl.a2;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

/**
 * an abstract class that represents an action that may be executed using the
 * {@link ActorThreadPool}
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 *
 * @param <R> the action result type
 */
public abstract class Action<R> implements Serializable {

    String name;

    protected Promise<R> promise;

    protected ActorThreadPool actorThreadPool;
    protected String Id;
    protected PrivateState privateState;
    private callback continuationCallBack;

    private boolean started = false;

	/**
     * start handling the action - note that this method is protected, a thread
     * cannot call it directly.
     */
    protected abstract void start();

    public abstract String getId();
    /**
    *
    * start/continue handling the action
    *
    * this method should be called in order to start this action
    * or continue its execution in the case where it has been already started.
    *
    * IMPORTANT: this method is package protected, i.e., only classes inside
    * the same package can access it - you should *not* change it to
    * public/private/protected
    *
    */
   /*package*/final void handle(ActorThreadPool pool, String actorId, PrivateState actorState) {
       if(!started) {
           Id               = actorId;
           actorThreadPool  = pool;
           privateState     = actorState;
           started          = true;

           start();
       }
       else if(continuationCallBack != null){
           continuationCallBack.call();
       }
   }
    
    
    /**
     * add a callback to be executed once *all* the given actions results are
     * resolved
     * 
     * Implementors note: make sure that the callback is running only once when
     * all the given actions completed.
     *
     * @param actions
     * @param callback the callback to execute once all the results are resolved
     */
    protected final void then(Collection<? extends Action<?>> actions, callback callback) {
        continuationCallBack = callback;
        CountDownLatch promisesResolved = new CountDownLatch(actions.size());

        for(Action action : actions) {

            action.getResult().subscribe(() -> {
                promisesResolved.countDown();

            });
        }
        try {
            promisesResolved.await();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

        sendMessage(this, Id, privateState);
    }

    /**
     * resolve the internal result - should be called by the action derivative
     * once it is done.
     *
     * @param result - the action calculated result
     */
    protected final void complete(R result) {
       promise.resolve(result);
    }
    
    /**
     * @return action's promise (result)
     */
    public final Promise<R> getResult() {
    	return promise;
    }
    
    /**
     * send an action to another actor
     * 
     * @param action
     * 				the action
     * @param actorId
     * 				actor's id
     * @param actorState
	 * 				actor's private state (actor's information)
	 *    
     * @return promise that will hold the result of the sent action
     */
	public Promise<?> sendMessage(Action<?> action, String actorId, PrivateState actorState){
        actorThreadPool.submit(action, actorId, actorState);
        return action.getResult();
	}


    /**
     * set action's name
     * @param actionName
     */
    public void setActionName(String actionName){
        name = actionName;
    }

    /**
     * @return action's name
     */
    public String getActionName(){
        return name;
    }
}
