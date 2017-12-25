package bgu.spl.a2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;




public class VersionMonitorTest  {

    private VersionMonitor vm;


    @Before
    public void setUp(){
        vm = new VersionMonitor();
    }

    @After
    public void tearDown(){
        vm = null;
    }

    @Test
    public void testGetVersionExpectedZero(){

        assertEquals(0, vm.getVersion());
    }

    @Test
    public void testIncExpectedOne(){

        assertEquals(0, vm.getVersion());
        vm.inc();
        assertEquals(1, vm.getVersion());
    }

    @Test
    public void testAwait_CurrentVersion_ThreadStatusIsWait(){
        Thread t = new awaitThread();
        t.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals( Thread.State.WAITING, t.getState());
        t.interrupt();
    }

    @Test
    public void testAwait_CurrentVersion_Increment_ThreadNotWaiting(){
        Thread t = new awaitThread();
        t.start();
        vm.inc();
        try {
            t.join(1000);
        } catch (InterruptedException e) {

            fail("unexpected interrupt");
        }
        assertTrue(t.getState() != Thread.State.WAITING);

    }

    @Test
    public void testAwait_UnderCurrentVersion_ThreadNotWaiting(){
        Thread t = new awaitThread();
        vm.inc();
        t.start();
        try {
            t.join(1000);
        } catch (InterruptedException e) {

            fail("unexpected interrupt");
        }
        assertTrue(t.getState() != Thread.State.WAITING);

    }

    @Test
    public void testAwait_OverCurrentVersion_ThreadNotWaiting(){
        Thread t = new awaitThread();
        ((awaitThread) t).version = 1;

        t.start();
        try {
            t.join(1000);
        } catch (InterruptedException e) {

            fail("unexpected interrupt");
        }
        assertTrue(t.getState() != Thread.State.WAITING);

    }


    @Test
    public void testAwait_CurrentVersion_Interrupting_ThreadInterrupted(){
        Thread t = new awaitThread();
        t.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(t.getState(), Thread.State.WAITING);
        t.interrupt();

        try {
            t.join(1000);
        } catch (InterruptedException e) {

            fail("unexpected interrupt");
        }
        assertTrue(((awaitThread) t).isInterrupt);
    }


    public class awaitThread extends Thread{
        public int version = 0;
        public boolean isInterrupt = false;

        public void run() {
            try {
                vm.await(version);
            } catch (InterruptedException e) {
                isInterrupt = true;
            }
        }

    }


}
