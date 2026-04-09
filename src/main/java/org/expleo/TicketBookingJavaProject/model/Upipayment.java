/*
 * FILE: Upipayment.java
 * PURPOSE: Stores UPI payment information.
 * 
 * OOPS CONCEPTS USED:
 * - Inheritance: Extends Payment class (parent class)
 * 
 * HOW IT'S USED:
 * - Created when user pays with UPI
 * - Validates UPI ID format
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * Extends Payment class to add UPI-specific details.
 * Stores UPI ID (like "user@sbi")
 */
public class Upipayment extends Payment {
    
    // UPI ID (format: user@bankname)
    private String upiId;

    /*
     * Constructor - Creates a UPI payment
     */
    public Upipayment(int paymentId, double amount, String status, String upiId) {
        super(paymentId, amount, "UPI", status);
        this.upiId = upiId;
    }

    // Get UPI ID
    public String getUpiId() { return upiId; }
}
