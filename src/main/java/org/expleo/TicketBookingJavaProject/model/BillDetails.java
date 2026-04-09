/*
 * FILE: BillDetails.java
 * PURPOSE: Calculates and displays the bill for a ticket booking.
 * 
 * OOPS CONCEPTS USED:
 * - Encapsulation: All fields are private, calculated once in constructor
 * - Abstraction: Shows final amount without revealing calculation steps
 * 
 * HOW IT'S USED:
 * - Created when user selects seats
 * - Shows breakdown of costs (ticket + GST + fees)
 * - Displays final amount to pay
 */
package org.expleo.TicketBookingJavaProject.model;

/*
 * WHAT THIS CLASS DOES:
 * Calculates the complete bill for a booking.
 * Shows ticket cost, GST (tax), and service fees.
 * 
 * BILL BREAKDOWN:
 * - Ticket Amount: Base price for all seats
 * - GST: 3.5% tax on ticket amount
 * - Application Fee: Rs.10 per ticket
 * - Total: Ticket Amount + GST + Fees
 */
public class BillDetails {

    // Number of tickets booked
    private int ticketCount;
    
    // Total base price for all tickets (before tax)
    private double ticketAmount;
    
    // GST percentage (3.5%)
    private double gstPercentage;
    
    // GST amount calculated (ticketAmount * gstPercentage / 100)
    private double gstAmount;
    
    // Fee per ticket (Rs.10)
    private double applicationFeePerTicket;
    
    // Total fees (applicationFeePerTicket * ticketCount)
    private double totalApplicationFee;
    
    // Final amount to pay
    private double totalAmount;

    /*
     * Constructor - Creates bill and calculates all amounts
     * 
     * Parameters:
     * - ticketCount: How many tickets
     * - ticketAmount: Base price for all tickets
     * - gstPercentage: GST tax percentage
     * - applicationFeePerTicket: Fee per ticket
     * 
     * Note: All amounts are calculated automatically in constructor
     */
    public BillDetails(int ticketCount, double ticketAmount, double gstPercentage, 
                       double applicationFeePerTicket) {
        this.ticketCount = ticketCount;
        this.ticketAmount = ticketAmount;
        this.gstPercentage = gstPercentage;
        
        // Calculate GST: ticketAmount * 3.5 / 100
        this.gstAmount = (ticketAmount * gstPercentage) / 100;
        
        this.applicationFeePerTicket = applicationFeePerTicket;
        
        // Total fees = fee per ticket * number of tickets
        this.totalApplicationFee = applicationFeePerTicket * ticketCount;
        
        // Final total = tickets + GST + fees
        this.totalAmount = ticketAmount + gstAmount + totalApplicationFee;
    }

    // Getter methods - Read bill details
    public int getTicketCount() { return ticketCount; }
    public double getTicketAmount() { return ticketAmount; }
    public double getGstPercentage() { return gstPercentage; }
    public double getGstAmount() { return gstAmount; }
    public double getApplicationFeePerTicket() { return applicationFeePerTicket; }
    public double getTotalApplicationFee() { return totalApplicationFee; }
    public double getTotalAmount() { return totalAmount; }

    /*
     * printBill - Shows the bill on screen
     * 
     * Displays all the details in a nice format.
     * Called after user selects seats to show what they need to pay.
     */
    public void printBill() {
        System.out.println("\n===========================================");
        System.out.println("           BILL DETAILS                   ");
        System.out.println("===========================================");
        System.out.println("Tickets Booked        : " + ticketCount);
        System.out.println("Ticket Amount         : Rs. " + String.format("%.2f", ticketAmount));
        System.out.println("GST (" + gstPercentage + "%)         : Rs. " + String.format("%.2f", gstAmount));
        System.out.println("Application Fee       : Rs. " + String.format("%.2f", totalApplicationFee));
        System.out.println("-------------------------------------------");
        System.out.println("TOTAL AMOUNT          : Rs. " + String.format("%.2f", totalAmount));
        System.out.println("===========================================");
    }
}
