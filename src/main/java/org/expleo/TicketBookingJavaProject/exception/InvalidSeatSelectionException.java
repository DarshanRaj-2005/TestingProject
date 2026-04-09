/*
 * FILE: InvalidSeatSelectionException.java
 * PURPOSE: Exception for invalid seat selection.
 * 
 * OOPS CONCEPTS USED:
 * - Inheritance: Extends CustomException
 */
package org.expleo.TicketBookingJavaProject.exception;

/*
 * Exception thrown when seat selection is invalid.
 */
public class InvalidSeatSelectionException extends CustomException {
    
    /*
     * Constructor - Creates exception with message
     */
    public InvalidSeatSelectionException(String message) {
        super(message);
    }
}
