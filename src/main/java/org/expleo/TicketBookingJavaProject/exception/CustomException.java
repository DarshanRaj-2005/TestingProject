/*
 * FILE: CustomException.java
 * PURPOSE: Base exception class for the application.
 * 
 * OOPS CONCEPTS USED:
 * - Inheritance: Other exceptions extend this
 * - Exception handling: Used for error reporting
 */
package org.expleo.TicketBookingJavaProject.exception;

/*
 * Base exception class for the application.
 * Other exceptions extend this class.
 */
public class CustomException extends RuntimeException {
    
    /*
     * Constructor - Creates exception with message
     */
    public CustomException(String message) {
        super(message);
    }
}
