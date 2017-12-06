package bgu.spl.a2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PromiseTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGet() throws Exception {
    }

    @Test
    public void testIsResolved() throws Exception {
    }

    @Test
    public void testResolve() throws Exception {
        try{
            Promise<Integer> p = new Promise<>();
            p. resolve (5);
            try{
                p. resolve (6);
                assert.fail ();
            catch(IllegalStateException ex){ int x = p.get();
                    assertEquals(x,5);
                }
            catch(Exception ex){
                    assert.fail();

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