/*
 * FILE: AppTest.java
 * PURPOSE: Unit test for the application.
 */
package org.expleo.TicketBookingJavaProject;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/*
 * Unit test for the application.
 */
public class AppTest 
    extends TestCase
{
    /*
     * Constructor
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /*
     * Creates test suite
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /*
     * Simple test to verify application works
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
