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
 */
public class PromiseTest {


    private Promise<Integer> p;

    @Before
    public void setUp() throws Exception {
        p = new Promise<Integer>();
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * ?????
     * Test method for {@link spl.util.promise#get()}. Positive test.
     */
    @Test
    public void testGet() throws Exception {
        Integer i1 = 1;
        p.resolve(i1);
        try {
            assertEquals(i1, p.get());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * ????
     * Test method for {@link spl.util.Promise#get()}. Negative test - throw an
     * exception.
     */
    @Test public void testGet_unResolved_throwException() {
        try {
            Promise.get();
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
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }


    @Test
    public void testResolve() throws Exception {


        try{
            //Promise<Integer> p = new Promise<Integer>();
            p.resolve (5);
            try {
                p.resolve(6);
                Assert.fail();

            }catch(IllegalStateException ex) {
                int x = p.get();
                assertEquals(x, 5);
            }
            catch(Exception ex){
                    Assert.fail();

                }
        }
        catch(Exception ex){
                Assert.fail();
        }
    }

    @Test
    public void subscribe() throws Exception {

    }

}