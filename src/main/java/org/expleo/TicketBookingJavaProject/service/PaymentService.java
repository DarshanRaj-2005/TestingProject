/*
 * FILE: PaymentService.java
 * PURPOSE: Handles payment processing and validation.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: Private fields
 * - Abstraction: Simple payment interface
 * 
 * WHAT THIS FILE DOES:
 * - Validates card details
 * - Validates UPI ID
 * - Validates payment status
 * 
 * PAYMENT METHODS SUPPORTED:
 * - Card (16 digits + 3 digit CVV)
 * - UPI (user@bank format)
 * - Cash (only for Officers/Admins)
 */
package org.expleo.TicketBookingJavaProject.service;

import org.expleo.TicketBookingJavaProject.model.Payment;
import org.expleo.TicketBookingJavaProject.exception.PaymentErrorException;
import java.util.Random;

/*
 * Service class for payment operations.
 * Handles payment validation and processing.
 */
public class PaymentService {

    /*
     * processPayment - Processes a payment
     * 
     * Parameters:
     * - amount: How much to pay
     * - method: Payment method
     * 
     * Returns: Payment object with transaction details
     */
    public Payment processPayment(double amount, String method) {
        int paymentId = new Random().nextInt(1000);
        Payment payment = new Payment(paymentId, amount, method, "SUCCESS");
        System.out.println("Payment processed successfully using " + method + "!");
        return payment;
    }

    /*
     * validateCardPayment - Checks card details
     * 
     * Validates:
     * - Card number: exactly 16 digits
     * - CVV: exactly 3 digits
     * 
     * Throws: PaymentErrorException if invalid
     */
    public void validateCardPayment(String cardNumber, String cvv) {
        // Validate card number (must be 16 digits)
        if (cardNumber == null || cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            throw new PaymentErrorException("Invalid Card Number! Must be exactly 16 digits.");
        }
        
        // Validate CVV (must be 3 digits)
        if (cvv == null || cvv.length() != 3 || !cvv.matches("\\d+")) {
            throw new PaymentErrorException("Invalid CVV! Must be exactly 3 digits.");
        }
        
        System.out.println("Card details validated successfully.");
    }

    /*
     * validateUpiPayment - Checks UPI ID format
     * 
     * Validates:
     * - Contains @ symbol
     * - At least 5 characters
     * 
     * Throws: PaymentErrorException if invalid
     */
    public void validateUpiPayment(String upiId) {
        if (upiId == null || !upiId.contains("@") || upiId.length() < 5) {
            throw new PaymentErrorException("Invalid UPI ID! Format should be: user@bank");
        }
        
        System.out.println("UPI ID validated successfully.");
    }

    /*
     * validatePayment - Checks if payment was successful
     * 
     * Returns: true if payment succeeded, false otherwise
     */
    public boolean validatePayment(Payment payment) {
        return payment != null && "SUCCESS".equalsIgnoreCase(payment.getStatus());
    }
}
