/*
 * FILE: BookingNotFoundException.java
 * PURPOSE: Exception when booking is not found.
 * 
 * OOPS CONCEPTS USED:
 * - Inheritance: Extends CustomException
 */
package org.expleo.TicketBookingJavaProject.exception;

/*
 * Exception thrown when a booking cannot be found.
 */
public class BookingNotFoundException extends CustomException {
    
    /*
     * Constructor - Creates exception with message
     */
    public BookingNotFoundException(String message) {
        super(message);
    }
}
