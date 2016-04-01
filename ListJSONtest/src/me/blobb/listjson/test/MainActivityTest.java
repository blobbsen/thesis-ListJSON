package me.blobb.listjson.test;

import android.test.ActivityTestCase;
//import ListJSON.*;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class me.blobb.listjson.MainActivityTest \
 * com.cortado.listjsontest.tests/android.test.InstrumentationTestRunner
 */
public class MainActivityTest extends ActivityTestCase {

    public MainActivityTest() {
    }
    
    public void testSomething(){
    	FileInfo finfo = new FileInfo(4954967l,49375935l,"foobar", false);
    	assertEquals(false, finfo.isFolder());
     	assertEquals("foobar", finfo.getName());	
    }
    
    public void testSomethingElse(){
    	FileInfo finfo = new FileInfo(4954967l,49375935l,"foobar", false);
    	assertEquals(false, finfo.isFolder());
     	assertEquals("foo", finfo.getName());
    }
}
