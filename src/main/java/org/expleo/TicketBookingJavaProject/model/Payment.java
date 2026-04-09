/*
 * FILE: Payment.java
 * PURPOSE: Stores payment information for a booking.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private, accessed through methods
 * - Inheritance: Cardpayment, Upipayment, Cashpayment extend this class
 * 
 * HOW IT'S USED:
 * - Created when a payment is processed
 * - Contains payment method and status
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * Represents a payment made for a booking.
 * Stores payment ID, amount, method, and status.
 */
public class Payment {

    // Unique ID for this payment
    private int paymentId;
    
    // How much was paid
    private double amount;
    
    // Payment method: "CARD", "UPI", or "CASH"
    private String method;
    
    // Payment status: "SUCCESS" or "FAILED"
    private String status;

    /*
     * Constructor - Creates a new Payment object
     */
    public Payment(int paymentId, double amount, String method, String status) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    // Getter methods
    public int getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public String getStatus() { return status; }

    // Setter methods
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setMethod(String method) { this.method = method; }
    public void setStatus(String status) { this.status = status; }
}
