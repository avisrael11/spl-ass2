package bgu.spl.a2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import junit.framework.Assert;
//import junit.framework.TestCase;
import org.junit.Assert;

import static org.junit.Assert.*;
/*
*
*
 */
public class PromiseTest {


    private Promise<Integer> p;



    @Before
    public void setUp() throws Exception {
        p = new Promise<Integer>();
    }

    @After
    public void tearDown() throws Exception {
        p = null;
    }


    @Test
    public void testGet_resolved_getResult()  {
        Integer i1 = 1;

        try {
            p.resolve(i1);
            assertEquals(i1, p.get());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test public void testGet_unResolved_throwException() {
        try {
            p.get();
            fail("Exception expected!");
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
        catch (Exception e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    public void testIsResolved_notResolved_false()  {
        assertEquals(false, p.isResolved());

    }

    @Test
    public void testIsResolved_resolved_true() throws Exception {
        Integer in = 1;

        try {
            p.resolve(in);
            assertEquals(true, p.isResolved());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

    }


    @Test
    public void testResolve() throws Exception {


        try{
            p.resolve (5);
            try {
                p.resolve(6);
                Assert.fail();

            }catch(IllegalStateException ex) {
                int x = p.get();
                assertEquals(x, 5);
            }
            catch(Exception ex){
                fail("Unexpected exception: " + ex.getMessage());

                }
        }
        catch(Exception ex){
            fail("Unexpected exception: " + ex.getMessage());
        }

    }


    @Test
    public void testResolve_activateCallbacks(){
        try{
            callBackImp ci = new callBackImp();
            p.subscribe(ci);

            assertFalse(ci.isCalled());
            p.resolve(6);
            assertTrue(ci.isCalled());
        }
        catch (Exception e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }


    @Test
    public void testResolve_activate3Callbacks(){
        try{
            callBackImp ci1 = new callBackImp();
            callBackImp ci2 = new callBackImp();
            callBackImp ci3 = new callBackImp();
            p.subscribe(ci1);
            p.subscribe(ci2);
            p.subscribe(ci3);

            assertFalse(ci1.isCalled());
            assertFalse(ci2.isCalled());
            assertFalse(ci3.isCalled());

            p.resolve(6);

            assertTrue(ci1.isCalled());
            assertTrue(ci2.isCalled());
            assertTrue(ci3.isCalled());
        }
        catch (Exception e){
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSubscribe_resolved_callBackCalled()  {
        try{
            p.resolve(6);

            callBackImp ci = new callBackImp();
            p.subscribe(ci);

            assertTrue(ci.isCalled());

        }
        catch (Exception e){
            fail("Unexpected exception: " + e.getMessage());
        }

    }


    public class callBackImp implements callback{

        private boolean called = false;
        public void call() {
            called = true;
        }

        public boolean isCalled(){
            return called;
        }

    }
}