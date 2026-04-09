/*
 * FILE: PaymentErrorException.java
 * PURPOSE: Exception for payment errors.
 * 
 * OOPS CONCEPTS USED:
 * - Inheritance: Extends CustomException
 */
package org.expleo.TicketBookingJavaProject.exception;

/*
 * Exception for payment-related errors.
 * Thrown when card/UPI validation fails.
 */
public class PaymentErrorException extends CustomException {
    
    /*
     * Constructor - Creates exception with message
     */
    public PaymentErrorException(String message) {
        super(message);
    }
}
