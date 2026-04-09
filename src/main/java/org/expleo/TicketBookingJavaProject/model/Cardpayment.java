/*
 * FILE: Cardpayment.java
 * PURPOSE: Stores credit/debit card payment information.
 * 
 * OOPS CONCEPTS USED:
 * - Inheritance: Extends Payment class (parent class)
 * - Polymorphism: Can be used anywhere Payment is expected
 * 
 * HOW IT'S USED:
 * - Created when user pays with a card
 * - Validates card details
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * Extends Payment class to add card-specific details.
 * Stores card number and CVV for card payments.
 */
public class Cardpayment extends Payment {
    
    // Credit/debit card number (16 digits)
    private String cardNumber;
    
    // CVV code (3 digits)
    private String cvv;

    /*
     * Constructor - Creates a card payment
     */
    public Cardpayment(int paymentId, double amount, String status, String cardNumber, String cvv) {
        super(paymentId, amount, "CARD", status);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    // Get card details
    public String getCardNumber() { return cardNumber; }
    public String getCvv() { return cvv; }
}
