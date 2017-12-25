package bgu.spl.a2;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Describes a monitor that supports the concept of versioning - its idea is
 * simple, the monitor has a version number which you can receive via the method
 * {@link #getVersion()} once you have a version number, you can call
 * {@link #await(int)} with this version number in order to wait until this
 * version number changes.
 *
 * you can also increment the version number by one using the {@link #inc()}
 * method.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class VersionMonitor {

    private AtomicInteger ver   = new AtomicInteger();
    private Object lock         = new Object();
    private boolean shutDown    = false;

    public int getVersion() {
        return ver.get();
    }

    public void setShutDown(boolean val){
        shutDown = val;
    }

    /**
     * increnent version by one
     *
     * synchronization is to make sure that all the waiting threads are notified (no context switch between the while and wait)
     */
    public void inc() {
        synchronized (lock){
            ver.incrementAndGet();
            lock.notifyAll();
        }
    }

    /**
     * wait until versionMonitor's version is different than
     * @param version
     *
     * @throws InterruptedException
     * synchronization is to make sure that all the waiting threads are notified (no context switch between the while and wait)
     */
    public void await(int version) throws InterruptedException {
        synchronized (lock) {
            while(!shutDown && ver.get() == version){
                lock.wait();
            }
        }

    }
}
