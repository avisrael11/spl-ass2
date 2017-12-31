package bgu.spl.a2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ActorThreadPoolTest {

    private ActorThreadPool atpTest;

    @Before
    public void setUp() throws Exception {
        atpTest = new ActorThreadPool(3);
    }

    @After
    public void finish() throws Exception {
        atpTest = null;
    }

    @Test
    public void submit() {

    }

    @Test
    public void start() {
        atpTest.start();
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(atpTest.threads.size()==3){
            isWaitingAndAlive();
        }//tester size
        try {
            atpTest.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }//start


    @Test
    //checks that all threads have been terminated
    public void shutdown() throws InterruptedException {
        atpTest.start();
        sleep(1000);
        isWaitingAndAlive();
        atpTest.shutdown();
        sleep(1000);
        for (Thread thread: atpTest.threads){
            assertTrue("thread"+ thread.getId()+ "  alive",!thread.isAlive());
            assertTrue("thread"+ thread.getId()+ "not terminated",thread.getState()== Thread.State.TERMINATED);
        }//for
    }




    private void isWaitingAndAlive() {
        for (Thread thread : atpTest.threads) {
            try {
                assertTrue("thread" + thread.getId() + "not waiting", thread.getState() == Thread.State.WAITING);
            }
            catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }









}
